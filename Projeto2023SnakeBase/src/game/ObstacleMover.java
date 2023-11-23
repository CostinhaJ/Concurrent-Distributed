package game;

import environment.BoardPosition;
import environment.LocalBoard;

public class ObstacleMover extends Thread {
	private Obstacle obstacle;
	private LocalBoard board;
	
	public ObstacleMover(Obstacle obstacle, LocalBoard board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
	}

	@Override
	public void run() {
		// TODO
		try {
			boolean done;
			while(obstacle.getRemainingMoves()>0) {
				done=false;
				sleep(Obstacle.OBSTACLE_MOVE_INTERVAL);
				for(int i=0;i!=board.NUM_COLUMNS;i++) {
					for(int j=0;j!=board.NUM_ROWS;j++) {
						if(board.getCell(new BoardPosition(i,j)).isOcupiedByGameElement() && board.getCell(new BoardPosition(i,j)).getGameElement().equals(obstacle)) {
							board.getCell(new BoardPosition(i,j)).removeObstacle();
							while(done==false) {
								BoardPosition bp = new BoardPosition((int) (Math.random() *board.NUM_COLUMNS),(int) (Math.random() * board.NUM_ROWS));
								if(!board.getCell(bp).isOcupied() && !board.getCell(bp).isOcupiedByGoal()) {
									board.getCell(bp).setGameElement(obstacle);
									obstacle.decrementRemainingMoves();
									board.setChanged();
									done=true;
								}
							}
							break;
						}
					}
					if(done==true)
						break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}
}
