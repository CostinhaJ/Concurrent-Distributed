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
	
	public RemoteBoard(Client cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public void init(){}
	
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
				System.out.println("Teste Down");
				break;
			case KeyEvent.VK_D:
				cliente.StreamInput("d");
				System.out.println("Teste Right");
				break;
			case KeyEvent.VK_RIGHT:
				cliente.StreamInput("a");
				System.out.println("Teste Right");
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
	public void setFinished() {}
	
}
