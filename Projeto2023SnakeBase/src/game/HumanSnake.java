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
public /*abstract*/ class HumanSnake extends Snake {   //Não sei se podia fazer isto, mas não conseguia instanciar a classe por ser abstrata
	
	public HumanSnake(int id, Board board) {
		super(id, board);
		doInitialPositioning();
		try {
			sleep(1000);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("initial size:"+cells.size());
		
	}
	
	public void nextMove(Cell nextCell) {
		try {
			if ( getBoard().getCell(nextCell.getPosition()).isOcupied() ) {
				System.out.println("Invalid Move");
			}else {
				move(nextCell);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() { //Receber instruções do server, que vêem do input dado pelo cliente com ID desta cobra.
		while(!getBoard().isFinished) {
			try {
				System.out.println("Sleeping...");
				sleep(getBoard().PLAYER_PLAY_INTERVAL);
			} catch (InterruptedException e) {
				// TODO: handle exception
				System.exit(0);
			}
		}
	}

}
