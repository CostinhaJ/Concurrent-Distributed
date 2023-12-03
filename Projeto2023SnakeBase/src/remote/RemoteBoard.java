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
import game.AutomaticSnake;
import game.ObstacleMover;

/** Remote representation of the game, no local threads involved.
 * Game state will be changed when updated info is received from Server.
 * Only for part II of the project.
 * @author luismota
 *
 */
public class RemoteBoard extends Board{

	private static final int NUM_SNAKES = 3;
	private static final int NUM_OBSTACLES = 25;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;
	private ExecutorService pool = Executors.newFixedThreadPool(NUM_SIMULTANEOUS_MOVING_OBSTACLES);
	
	public RemoteBoard() {
		
		for (int i = 0; i < NUM_SNAKES; i++) {
			AutomaticSnake snake = new AutomaticSnake(i, this);
			snakes.add(snake);
		}

		addObstacles( NUM_OBSTACLES);
		
		Goal goal=addGoal();
		System.err.println("All elements placed");
		
	}
	
	@Override
	public void handleKeyPress(int keyCode) {
		//TODO
		switch(keyCode){
		case KeyEvent.VK_DOWN:
			//return DOWN;
		case KeyEvent.VK_UP:
			//return UP;
		case KeyEvent.VK_LEFT:
			//return LEFT;
		case KeyEvent.VK_RIGHT:
			//return RIGHT;
		}
	}

	@Override
	public void handleKeyRelease() {
		// TODO
	}

	@Override
	public void init() {
	
		for(Snake s:snakes)
			s.start();
		for(Obstacle o: getObstacles()) {
			pool.submit(new ObstacleMover(o, this));
		}
		setChanged();
	}

	public void setFinished() {
		isFinished=true;
		for(Snake s : snakes) {
			s.interrupt();
		}
		pool.shutdownNow();
	}

	

}
