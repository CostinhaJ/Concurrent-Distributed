package game;

import java.util.List;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import remote.RemoteBoard;
 /** Class for a remote snake, controlled by a human 
  * 
  * @author luismota
  *
  */
public abstract class HumanSnake extends Snake {
	
	public RemoteBoard board;
	
	public HumanSnake(int id, RemoteBoard board) {
		super(id,board);
		this.board = board;
	}
	
	@Override
	public void run() {
		
	}

}
