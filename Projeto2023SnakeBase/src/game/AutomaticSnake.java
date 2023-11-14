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

	@Override
	public void run() {
		doInitialPositioning();
		System.err.println("initial size:"+cells.size());
		try {
			while(true) {
			cells.getFirst().request(this);
			Cell next = new Cell(cells.getLast().getPosition().getCellRight());
			System.out.println("got cell:" + next.getPosition().toString());
			move(next);
			sleep(1000);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block  //criar uma sentinela e dar release quando sair o objeto da frente
			System.out.println("Program Stoped");
			e.printStackTrace();
		}
		//TODO: automatic movement
	}
	

	
}
