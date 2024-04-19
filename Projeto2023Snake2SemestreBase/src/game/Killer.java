package game;

import environment.Board;
import environment.BoardPosition;
import environment.Cell;

public class Killer extends GameElement {

	private Board board;
	private int id;
	private Cell previous;
	
	public Killer(int id, Board board){
		this.board = board;
		this.id = id;
		doInitialPositioning();
	}
	
//	@Override
//	public void run() {
//		//TODO: automatic movement
//	while(!getBoard().isFinished()) {
//		try {
//				sleep(getBoard().PLAYER_PLAY_INTERVAL);
//				Cell next = new Cell(nextBoardPosition());
//				move(next);
//				previous.removeGameElement(this);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			//Verificar se acabou o jogo
//			moveToNewPosition();
//			}
//		}
//	}

	public void doInitialPositioning() {
		int posX = (int) (Math.random() * Board.HEIGHT);;
		int posY = (int) (Math.random() * Board.WIDTH);
		BoardPosition at = new BoardPosition(posX, posY);		
		try {			
			board.getCell(at).setGameElement(this);				
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block	
			e1.printStackTrace();
		}		
	}
	
	
	private Board getBoard() {
		return board;
	}
	
}
