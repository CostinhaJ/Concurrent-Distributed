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

	}
	
	private Cell nextCell() {
		BoardPosition aux = null;  // Initialize with null or a valid initial position
		for (BoardPosition neighbor : getBoard().getNeighboringPositions(cells.getLast())) {
			if (aux == null || aux.distanceTo(getBoard().getGoalPosition()) > neighbor.distanceTo(getBoard().getGoalPosition())) {
				if(!(getBoard().getCell(neighbor).isOcupiedBySnake() && getBoard().getCell(neighbor).getOcuppyingSnake().getId()==this.getId())) {
					aux = neighbor;
				}
			}
		}
		 
		return new Cell(aux);
	}

	@Override
	public void run() {
		//TODO: automatic movement
		doInitialPositioning();
		System.err.println("initial size:"+cells.size());
		try {
			//cells.getLast().request(this); codigo original mas parece inutil
			while(true) {
				Cell next = nextCell();
				System.out.println("Next cell:" + next.getPosition().toString());
				move(next);
				sleep(getBoard().PLAYER_PLAY_INTERVAL);
			}
			/* COSTA
			while(true) {
			cells.getFirst().request(this);
			Cell next = nextCell();
			System.out.println("got cell:" + next.getPosition().toString());
			move(next);
			sleep(getBoard().PLAYER_PLAY_INTERVAL);
			} */
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Program Stoped");
			
			/* COSTA
			System.out.println("Program Stoped"); */
			
			e.printStackTrace();
		}
	}
	

	
}
