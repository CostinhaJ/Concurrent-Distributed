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
				 aux = neighbor;
			 }
		 }
		   
		return new Cell(aux);
	}

	@Override
	public void run() {
		doInitialPositioning();
		System.err.println("initial size:"+cells.size());
		try {
			while(true) {
			cells.getFirst().request(this);
			Cell next = nextCell();
			System.out.println("got cell:" + next.getPosition().toString());
			move(next);
			sleep(getBoard().PLAYER_PLAY_INTERVAL);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block  //criar uma sentinela e dar release quando sair o objeto da frente
			System.out.println("A cobra encontrou um obstáculo");
			e.printStackTrace();
		}
		//TODO: automatic movement
	}
	

	
}
