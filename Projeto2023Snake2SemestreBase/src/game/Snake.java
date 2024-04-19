package game;

import java.io.Serializable;
import java.util.LinkedList;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Snake extends Thread {

	private boolean killed = false ;
	protected LinkedList<Cell> cells = new LinkedList<Cell>();
	protected int size = 5;
	private int id;
	private Board board;

	public Snake(int id,Board board) {
		this.id = id;
		this.board=board;
	}
	
	public boolean wasKilled () { 
		return killed;
	}
	
	public int getSize() {
		return size;
	}

	public int getIdentification() {
		return id;
	}

	public int getCurrentLength() {
		return cells.size();
	}
	
	public LinkedList<Cell> getCells() {
		return cells;
	}
	
	protected void move(Cell cell) throws InterruptedException {
		//TODO
		board.getCell(cell.getPosition()).request(this);
		cells.add(cell);
		if(board.getCell(cell.getPosition()).isOcupiedByGoal()) {
			size+=board.getCell(cell.getPosition()).getGoal().getValue();
			board.getCell(cell.getPosition()).getGoal().captureGoal();
		}
		if(board.getCell(cell.getPosition()).isOcupiedByKiller()) {
			killed = true ; 
		}
		if(cells.size() == size) {
			board.getCell(cells.getFirst().getPosition()).release();
			cells.removeFirst();
		}
		board.setChanged();
	}
	
	
	public void doInitialPositioning() {
		//TODO		
		int posX = 0;
		int posY = (int) (Math.random() * Board.WIDTH);
		BoardPosition at = new BoardPosition(posX, posY);		
		try {			
			board.getCell(at).set(this);				
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block	
			e1.printStackTrace();
		}		
	}
	
	public Board getBoard() {
		return board;
	}
	
	// Utility method to return cells occupied by snake as a list of BoardPosition
	// Used in GUI. Do not alter
	public synchronized LinkedList<BoardPosition> getPath() {
		LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
		for (Cell cell : cells) {
			coordinates.add(cell.getPosition());
		}

		return coordinates;
	}	
}
