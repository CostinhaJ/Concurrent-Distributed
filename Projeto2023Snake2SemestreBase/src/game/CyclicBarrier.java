package game;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeoutException;

import environment.Board;

public class CyclicBarrier {
	protected final int NUMBER_WAITERS = 3 ;
	private int currentWaiters = 0;
	private int passedWaiters = 0;
	private Board board;
	
	
	public CyclicBarrier(Board board, Runnable barrierAction) {
        currentWaiters=0;
        passedWaiters=0;
        this.board = board;
    }
	
	public synchronized void await () throws InterruptedException {
		currentWaiters ++;
		while ( currentWaiters < NUMBER_WAITERS ) {
			wait ();
		}
			if( passedWaiters == 0) {
				notifyAll();			
				passedWaiters ++;
			}
			if( passedWaiters == NUMBER_WAITERS ){
				passedWaiters = 0;
				currentWaiters = 0;
				}	
			board.addGameElement(new Killer(board.numKillers()+1 ,board));	
		}
	
}
