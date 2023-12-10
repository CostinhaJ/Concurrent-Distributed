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

	private Client cliente;
	private static final int NUM_OBSTACLES = 25;
	private static final int NUM_SIMULTANEOUS_MOVING_OBSTACLES = 3;
	private ExecutorService pool = Executors.newFixedThreadPool(NUM_SIMULTANEOUS_MOVING_OBSTACLES);
	
	public RemoteBoard(Client cliente) {
		this.cliente = cliente;
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
	public void handleKeyPress(int keyCode){
		//TODO
		
		try {			
			switch(keyCode) {
			case KeyEvent.VK_W:
				cliente.StreamInput("w");				
				System.out.println("Teste Up");
				break;
			case KeyEvent.VK_UP:
				cliente.StreamInput("w");				
				System.out.println("Teste Up");
				break;
			case KeyEvent.VK_A:
				cliente.StreamInput("a");
				System.out.println("Teste Left");
				break;
			case KeyEvent.VK_LEFT:
				cliente.StreamInput("a");
				System.out.println("Teste Left");
				break;
			case KeyEvent.VK_S:
				cliente.StreamInput("s");
				System.out.println("Teste Down");
				break;
			case KeyEvent.VK_DOWN:
				cliente.StreamInput("a");
				System.out.println("Teste Left");
				break;
			case KeyEvent.VK_D:
				cliente.StreamInput("d");
				System.out.println("Teste Right");
				break;
			case KeyEvent.VK_RIGHT:
				cliente.StreamInput("a");
				System.out.println("Teste Left");
				break;
		}
			
		} catch (IOException e) {
			// TODO: handle exception
		}
		
	
	}

	@Override
	public void handleKeyRelease() {
		// TODO
	}
	
	@Override
	public void setFinished(){
		isFinished=true;
		for(Snake s : snakes) {
			s.interrupt();
		}
		pool.shutdownNow();
	}
	
}
