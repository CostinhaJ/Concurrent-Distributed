package game;

import environment.Board;
import environment.BoardPosition;
import environment.LocalBoard;

public class Goal extends GameElement  {
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
		//TODO
		value++;
		if(value==MAX_VALUE) {
			board.setFinished();
		}
	}

	public int captureGoal() {
		//TODO
		try {
			board.getCell(board.getGoalPosition()).removeGoal();
			incrementValue();
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
