package remote;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import environment.LocalBoard;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;

import game.AutomaticSnake;
import game.Goal;
import game.Obstacle;
import game.Snake;
import game.HumanSnake;
import game.ObstacleMover;


/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Server.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board{
	private static final int NUM_OBSTACLES = 25;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;
	private ExecutorService pool = Executors.newFixedThreadPool(NUM_SIMULTANEOUS_MOVING_OBSTACLES);
	
	public RemoteBoard() {
		addObstacles( NUM_OBSTACLES);
		Goal goal=addGoal();
		System.err.println("All elements placed");
	}
	
	@Override
	public void init() {
		for(Obstacle o: getObstacles()) {
			pool.submit(new ObstacleMover(o, this));
		}
		setChanged();
	}
	
	@Override
	public void handleKeyPress(int keyCode) {
		//TODO
		/*switch(keyCode) {
			case KeyEvent.VK_W:
				
				//como ir buscar a classe que chamou handle key, para poder mexer a sua snake
				System.out.println("Teste Up");
				//snake.move(new Cell(snake.getCells().getLast().getPosition().getCellAbove()));
				break;
			case KeyEvent.VK_A:
				System.out.println("Teste Left");
				//snake.move(new Cell(snake.getCells().getLast().getPosition().getCellLeft()));
				break;
			case KeyEvent.VK_S:
				System.out.println("Teste Down");
				//snake.move(new Cell(snake.getCells().getLast().getPosition().getCellBelow()));
				break;
			case KeyEvent.VK_D:
				System.out.println("Teste Right");
				//snake.move(new Cell(snake.getCells().getLast().getPosition().getCellRight()));
				break;
		}*/
	
	}

	@Override
	public void handleKeyRelease() {
		// TODO
	}
	
	@Override
	public void setFinished(){
		isFinished=true;
		pool.shutdownNow();
	}
	
}
