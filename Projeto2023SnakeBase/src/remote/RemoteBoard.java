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

	public RemoteBoard() {
		
	}
	
	@Override
	public void handleKeyPress(int keyCode) {
		//TODO
	
	}

	@Override
	public void handleKeyRelease() {
		// TODO
	}

	@Override
	public void init() {
	
	}
	
	
}
