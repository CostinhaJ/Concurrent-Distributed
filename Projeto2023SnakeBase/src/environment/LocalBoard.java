package environment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.ObstacleMover;
import game.Server;
import game.Snake;
import game.AutomaticSnake;

/** Class representing the state of a game running locally
 * 
 * @author luismota
 *
 */
public class LocalBoard extends Board{
	
	private static final int NUM_SNAKES = 0;
	private static final int NUM_OBSTACLES = 25;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;
	private transient ExecutorService pool = Executors.newFixedThreadPool(NUM_SIMULTANEOUS_MOVING_OBSTACLES);

	public LocalBoard() {
		
		for (int i = 0; i < NUM_SNAKES; i++) {
			AutomaticSnake snake = new AutomaticSnake(i, this);
			snakes.add(snake);
		}

		addObstacles( NUM_OBSTACLES);
		Goal goal=addGoal();
		System.err.println("All elements placed");
	}

	public void init(){
		// TODO: launch other threads
		try {
			Thread.sleep(10000);
		} catch (InterruptedException  e) {
			e.printStackTrace();
		}
		for(Snake s:snakes)
			s.start();
		for(Obstacle o: getObstacles()) {
			pool.submit(new ObstacleMover(o, this));
		}
		setChanged();
	}

	

	@Override
	public void handleKeyPress(int keyCode) {
		// do nothing... No keys relevant in local game
	}

	@Override
	public void handleKeyRelease() {
		// do nothing... No keys relevant in local game
	}

	public void setFinished(){
		isFinished=true;
		for(Snake s : snakes) {
			s.interrupt();
		}
		pool.shutdownNow();
	}

}
