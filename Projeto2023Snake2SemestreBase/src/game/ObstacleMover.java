package game;

import environment.Board;
import environment.BoardPosition;

public class ObstacleMover extends Thread {
	private Obstacle obstacle ;
	private Board board ;
	
	public ObstacleMover(Obstacle obstacle, Board board) {
		super();
		this.obstacle = obstacle;
		this.board = board;
	}
	
	public void run() {
		// TODO
		try {
			boolean done;
			while(obstacle.getRemainingMoves()>0) {
				done=false;
				sleep(Obstacle.OBSTACLE_MOVE_INTERVAL);
				for(int i=0;i!=board.HEIGHT;i++) {
					for(int j=0;j!=board.WIDTH;j++) {
						if(board.getCell(new BoardPosition(i,j)).isOcupiedByGameElement() && board.getCell(new BoardPosition(i,j)).getGameElement().equals(obstacle)) {
							board.getCell(new BoardPosition(i,j)).removeObstacle();
							while(done==false) {
								BoardPosition bp = new BoardPosition((int) (Math.random() *board.HEIGHT),(int) (Math.random() * board.WIDTH));
								if(!board.getCell(bp).isOcupied() && !board.getCell(bp).isOcupiedByGoal()) {
									board.getCell(bp).setGameElement(obstacle);
									obstacle.decrementRemainingMoves();
									board.setChanged();
									done=true;
								}
							}
							break;
						}
					}
					if(done==true)						
						break;
				}
			}
			board.requestCyclycBarrier();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}		
	}
}
