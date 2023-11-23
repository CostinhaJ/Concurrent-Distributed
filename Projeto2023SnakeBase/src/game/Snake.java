package game;

import java.io.Serializable;
import java.util.LinkedList;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Board;
import environment.BoardPosition;
import environment.Cell;
/** Base class for representing Snakes.
 * Will be extended by HumanSnake and AutomaticSnake.
 * Common methods will be defined here.
 * @author luismota
 *
 */
public abstract class Snake extends Thread implements Serializable{
	protected LinkedList<Cell> cells = new LinkedList<Cell>();
	protected int size = 10;
	private int id;
	private Board board;
	
	public Snake(int id,Board board) {
		this.id = id;
		this.board=board;
	}

	public int getSize() {
		return size;
	}

	public int getIdentification() {
		return id;
	}

	public int getLength() {
		return cells.size();
	}
	
	public LinkedList<Cell> getCells() {
		return cells;
	}
	protected void move(Cell cell) throws InterruptedException {
		// TODO		
		board.getCell(cell.getPosition()).request(this);
		cells.add(cell);
		if(board.getCell(cell.getPosition()).isOcupiedByGoal()) {
			size+=board.getCell(cell.getPosition()).getGoal().getValue();
			board.getCell(cell.getPosition()).getGoal().captureGoal();
		}
		if(cells.size() == size) {
			board.getCell(cells.getFirst().getPosition()).release();
			cells.removeFirst();
		}
		board.setChanged();
		
		/* COSTA
		cells.add(cell);
		board.getCell(cell.getPosition()).request(this);
		if(cells.size() == size) {
			board.getCell(cells.getFirst().getPosition()).release();
//			board.getNeighboringPositions(board.getCell(cells.getFirst().getPosition())).forEach(neighbor -> {
//				neighbor.notify();
//			}); tentativa de dar release as cobras paradas na cauda de outras 
			cells.removeFirst();
		}
		board.setChanged(); */
	}
	
	public LinkedList<BoardPosition> getPath() {
		LinkedList<BoardPosition> coordinates = new LinkedList<BoardPosition>();
		for (Cell cell : cells) {
			coordinates.add(cell.getPosition());
		}

		return coordinates;
	}	
	protected void doInitialPositioning() {
		// Random position on the first column. 
		// At startup, snake occupies a single cell
		int posX = 0;
		int posY = (int) (Math.random() * Board.NUM_ROWS);
		BoardPosition at = new BoardPosition(posX, posY);
		
		try {
			board.getCell(at).request(this);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cells.add(board.getCell(at));
		System.err.println("Snake "+getIdentification()+" starting at:"+getCells().getLast().getPosition().toString());		
	}
	
	public Board getBoard() {
		return board;
	}
	
	public boolean equals(Snake s) {
		if(id==s.getId()) {
			return true;
		}
		return false;
	}
}
