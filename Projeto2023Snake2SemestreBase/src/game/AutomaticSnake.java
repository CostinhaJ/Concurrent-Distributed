package game;

import java.util.LinkedList;
import java.util.List;

import javax.swing.text.Position;

import environment.LocalBoard;
import gui.SnakeGui;
import environment.Cell;
import environment.Board;
import environment.BoardPosition;

public class AutomaticSnake extends Snake {
	public AutomaticSnake(int id, LocalBoard board) {
		super(id,board);
		doInitialPositioning();
		System.err.println("initial size:"+cells.size());		
	}

	@Override
	public void run() {
		//TODO: automatic movement
	while(!getBoard().isFinished() && !this.wasKilled()) {
		try {
				sleep(getBoard().PLAYER_PLAY_INTERVAL);
				Cell next = new Cell(nextBoardPosition());
				move(next);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			if(this.wasKilled()) {
				break;
			}
			moveToNewPosition();
			}
		}
	}
	
	private BoardPosition nextBoardPosition() {
		BoardPosition aux = null;  // Initialize with null or a valid initial position
		for (BoardPosition neighbor : getBoard().getNeighboringPositions(cells.getLast())) {
			if (aux == null || aux.distanceTo(getBoard().getGoalPosition()) > neighbor.distanceTo(getBoard().getGoalPosition())) {
				if(!(getBoard().getCell(neighbor).isOcupiedBySnake() && getBoard().getCell(neighbor).getOcuppyingSnake().getId()==this.getId())) {
					aux = neighbor;
				}
			}
		}
		 
		return aux;
	}
	
	private void moveToNewPosition() {
		try {
			Cell snakehead = this.cells.getLast();
			List<BoardPosition> neighbours = getBoard().getNeighboringPositions(snakehead);
			neighbours.removeIf(position -> getPath().contains(position) || getBoard().getCell(position).isOcupied());
			if (neighbours.size() == 0) {
				move(getBoard().getCell(getBoard().getNeighboringPositions(snakehead).get(0)));
			}else {
				move(getBoard().getCell(neighbours.get(0)));
			}
		}catch (Exception e) {
			moveToNewPosition();
		}
	}
}