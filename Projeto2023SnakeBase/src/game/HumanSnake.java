package game;

import java.io.Serializable;
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
public /*abstract*/ class HumanSnake extends Snake implements Serializable {   //Não sei se podia fazer isto, mas não conseguia instanciar a classe por ser abstrata
	
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
	
	/*public void nextMove(Cell nextCell) {
		try {
			if ( getBoard().getCell(nextCell.getPosition()).isOcupied() ) {
				System.out.println("Invalid Move");
			}else {
				move(nextCell);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	@Override
	public void run(){}
	

}
