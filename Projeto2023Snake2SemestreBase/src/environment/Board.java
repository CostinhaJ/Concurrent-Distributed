package environment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import game.GameElement;
import game.Goal;
import game.Killer;
import game.Obstacle;
import game.ObstacleMover;
import game.Snake;

// Class is abstract to allow the creation of other kinds of Board, which is not necessary in this project.
public abstract class Board extends Observable {
	protected Cell[][] cells;
	private BoardPosition goalPosition;
	public static final long PLAYER_PLAY_INTERVAL = 100;
	public static final long REMOTE_REFRESH_INTERVAL = 200;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	protected LinkedList<Snake> snakes = new LinkedList<Snake>();
	protected LinkedList<Obstacle> obstacles= new LinkedList<Obstacle>();
	protected LinkedList<Killer> killers = new LinkedList<Killer>();
	private CyclicBarrier cb;
	protected boolean isFinished;

	public Board() {
		cells = new Cell[WIDTH][HEIGHT];
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				cells[x][y] = new Cell(new BoardPosition(x, y));
			}
		}
		
		cb = new CyclicBarrier(3, () -> {
			this.addGameElement(new Killer(this.numKillers() ,this));	
			});

	}

	public Cell getCell(BoardPosition cellCoord) {
		return cells[cellCoord.x][cellCoord.y];
	}

	protected BoardPosition getRandomPosition() {
		return new BoardPosition((int) (Math.random() *HEIGHT),(int) (Math.random() * WIDTH));
	}

	public BoardPosition getGoalPosition() {
		return goalPosition;
	}

	public void setGoalPosition(BoardPosition goalPosition) {
		this.goalPosition = goalPosition;
	}

	public void addGameElement(GameElement gameElement) {
		boolean placed=false;
		while(!placed) {
			BoardPosition pos=getRandomPosition();
			if(!getCell(pos).isOcupied() && !getCell(pos).isOcupiedByGoal()) {
				try {
					getCell(pos).setGameElement(gameElement);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				if(gameElement instanceof Goal) {
					setGoalPosition(pos);
					System.out.println("Goal placed at:"+pos);
				}
				placed=true;
			}
		}
	}

	public List<BoardPosition> getNeighboringPositions(Cell cell) {
		ArrayList<BoardPosition> possibleCells=new ArrayList<BoardPosition>();
		BoardPosition pos=cell.getPosition();
		if(pos.x>0)
			possibleCells.add(pos.getCellLeft());
		if(pos.x<WIDTH-1)
			possibleCells.add(pos.getCellRight());
		if(pos.y>0)
			possibleCells.add(pos.getCellAbove());
		if(pos.y<HEIGHT-1)
			possibleCells.add(pos.getCellBelow());
		return possibleCells;

	}

	public BoardPosition selectPositionClosestToGoal(List<BoardPosition> possibleDestinations) {
		//TODO
		return null;
	}

	protected Goal addGoal() {
		Goal goal=new Goal(this);
		addGameElement( goal);
		return goal;
	}

	protected void addObstacles(int numberObstacles) {
		//TODO
		// clear obstacle list , necessary when resetting obstacles.
		getObstacles().clear();
		while(numberObstacles>0) {
			Obstacle obs=new Obstacle(this);
			addGameElement(obs);
			getObstacles().add(obs);
			numberObstacles--;
		}
	}

	public LinkedList<Snake> getSnakes() {
		return snakes;
	}
	
	public int numKillers() {
		return killers.size();
	}


	@Override
	public void setChanged() {
		super.setChanged();
		notifyObservers();
	}
	
	public LinkedList<Obstacle> getObstacles() {
		//TODO
		// percorrer cells e acumular obstáculos numa lista
		return obstacles;
	}


	public void addSnake(Snake snake) {
		snakes.add(snake);
	}
	
	public void removeSnake(Snake snake) {
		for( Cell c : snake.getCells()){
			c.release();
			c.removeObstacle();
		}
		snakes.remove(snake);
	}

	public void moveObstacle(Obstacle obstacle) {
		//TODO
		new ObstacleMover(obstacle, this).run();
	}
	
	public void removeGoal() {
		//TODO
		new Cell(goalPosition).removeGoal();
	}
	public boolean isFinished() {
		return isFinished;
	}
	
	public abstract void init(); 
	
	public void requestCyclycBarrier(){
		try {
			cb.await();
		}catch(BrokenBarrierException e) {
			cb.reset();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	
	// Ignorar: para johador humano
	public abstract void handleKeyPress(int keyCode);

	public abstract void handleKeyRelease();

	public abstract void setFinished();

	protected void setCells(Cell[][] cells) {
		this.cells=cells;
	}

	public Cell[][] getCells() {
		return cells;
	}

}