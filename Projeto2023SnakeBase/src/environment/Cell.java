package environment;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.midi.SysexMessage;

import game.GameElement;
import game.Goal;
import game.Obstacle;
import game.Snake;
import game.AutomaticSnake;
/** Main class for game representation. 
 * 
 * @author luismota
 *
 */
public class Cell implements Serializable {
	
	private BoardPosition position;
	private Snake ocuppyingSnake = null;
	private GameElement gameElement=null;

	
	private Lock rrlock = new ReentrantLock();
	private Lock gelock = new ReentrantLock();
	private Condition freeCell = rrlock.newCondition();
	
	public GameElement getGameElement() {
		return gameElement;
	}


	public Cell(BoardPosition position) {
		super();
		this.position = position;
	}

	public BoardPosition getPosition() {
		return position;
	}

	public void request(Snake snake) throws InterruptedException {
		//TODO coordination and mutual exclusion
		rrlock.lock();
		try {
			while(isOcupied())
				freeCell.await();
			ocuppyingSnake=snake;
		} finally {
			rrlock.unlock();
		}
	}

	public void release() {
		//TODO
		rrlock.lock();
		try {
			ocuppyingSnake = null;
			freeCell.signalAll();
		} finally {
			rrlock.unlock();
		}
		
		/* COSTA
		ocuppyingSnake = null; */
	}

	public boolean isOcupiedBySnake() {
		return ocuppyingSnake!=null;
	}

	//EU
	public boolean isOcupiedByGameElement() {
		return gameElement!=null;
	}

	public  void setGameElement(GameElement element) {
		// TODO coordination and mutual exclusion
		gelock.lock();
		gameElement=element;
		gelock.unlock();
	}

	public boolean isOcupied() {
		return isOcupiedBySnake() || (gameElement!=null && gameElement instanceof Obstacle);
	}


	public Snake getOcuppyingSnake() {
		return ocuppyingSnake;
	}


	public Goal removeGoal() {
		//TODO
		
		Goal goal = (Goal) gameElement;
		gameElement=null;
		return goal;
	}
	public void removeObstacle() {
		//TODO
		
		rrlock.lock();
		try {
			gameElement = null;
			freeCell.signalAll();
		} finally {
			rrlock.unlock();
		}
		
		/* COSTA
		gameElement = null; */
	}


	public Goal getGoal() {
		return (Goal)gameElement;
	}


	public boolean isOcupiedByGoal() {
		return (gameElement!=null && gameElement instanceof Goal);
	}
	
	public boolean equals(Object o) {
		if(o instanceof Cell) {
			if(position.equals(((Cell) o).getPosition()) && ocuppyingSnake==(((Cell) o).getOcuppyingSnake()) && gameElement==(((Cell) o).getGameElement())) {
				return true;
			}
		}
		
		return false;
	}

}
