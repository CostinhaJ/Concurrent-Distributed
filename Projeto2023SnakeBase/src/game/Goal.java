package game;

import java.io.Serializable;

import environment.Board;
import environment.BoardPosition;
import environment.LocalBoard;

public class Goal extends GameElement implements Serializable {
	private int value=1;
	private Board board;
	public static final int MAX_VALUE=10;
	
	public Goal( Board board2) {
		this.board = board2;
	}
	
	public int getValue() {
		return value;
	}
	public void incrementValue() throws InterruptedException {		
		value++;
	}

	public int captureGoal() {
		//TODO
		try {
			board.getCell(board.getGoalPosition()).removeGoal();
			incrementValue();
			if(value==MAX_VALUE) {
				board.setFinished();
				return 1;
			}
			boolean valid=false;
			while(valid==false) {
				BoardPosition bp = new BoardPosition((int) (Math.random() *board.NUM_COLUMNS),(int) (Math.random() * board.NUM_ROWS));
				if(!board.getCell(bp).isOcupied()) {
					board.getCell(bp).setGameElement(this);
					board.setGoalPosition(bp);
					board.setChanged();
					valid=true;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
