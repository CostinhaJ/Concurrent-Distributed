package environment;


import game.GameElement;
import game.Goal;
import game.Killer;
import game.Obstacle;
import game.Snake;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import game.AutomaticSnake;

public class Cell implements Serializable{
	
	private BoardPosition position;
	private Snake ocuppyingSnake = null;
	private GameElement gameElement=null;

	//Implementação de concorrencia com Cyclyc barrier
	/*A medida que os ` ObstacleMovers forem terminando a sua execu¸c˜ao, devem ser introduzidos novos elementos de jogo, designado Killers. Esta cria¸c˜ao
de Killers ser´a coordenada com ajuda de uma barreira, a implementar, da
classe CyclicBarrier. Imediatamente antes de um ObstacleMover terminas a sua execu¸c˜ao, deve invocar o m´etodo await da barreira. Por cada
CyclicBarrier.NUMBER WAITERS*/
	
	private Lock rrlock = new ReentrantLock();
	private Condition snakeFree = rrlock.newCondition();
	private Lock gelock = new ReentrantLock();
	
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
	
	public void set(Snake snake) throws InterruptedException {
	    try {
	        if (!isOcupied()) {
	        	rrlock.lock();
	        	ocuppyingSnake = snake;	  
	            snake.getCells().add(this);
				System.err.println("Snake "+snake.getIdentification()+" starting at:"+this.getPosition().toString());
				rrlock.unlock();
	        } else {
	        	System.out.println("Interrompida" + this.getPosition().toString());	  
	        	snake.doInitialPositioning();
	        }
	    } catch(Exception e) {
	      e.printStackTrace();
	    }
	}

	public void request(Snake snake) throws InterruptedException {
		//TODO coordination and mutual exclusion
		rrlock.lock();
		try {
			while(isOcupied())
				snakeFree.await();
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
			snakeFree.signalAll();
		} finally {
			rrlock.unlock();
		}
		
		/* COSTA
		ocuppyingSnake = null; */
	}

	public boolean isOcupiedBySnake() {
		return ocuppyingSnake!=null;
	}
	
	public boolean isOcupiedByKiller() {
		return gameElement instanceof Killer;
	}

	//EU
	public boolean isOcupiedByGameElement() {
		return gameElement!=null;
	}

	public  void setGameElement(GameElement element) throws InterruptedException {
		// TODO coordination and mutual exclusion
		gelock.lock();
		gameElement = element;
		gelock.unlock();	
	}
	
	public  void removeGameElement(GameElement element) throws InterruptedException {
		
		gelock.lock();		
		gameElement = null;		
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
			snakeFree.signalAll();
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
