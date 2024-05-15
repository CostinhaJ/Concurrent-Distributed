package remote;

import java.io.Serializable;

public class ActionResult implements Serializable{
//TODO
	boolean wasSucessefull;
	boolean gameEnded;
	
	public ActionResult(boolean wasSucessefull, boolean gameEnded){
		this.wasSucessefull = wasSucessefull;
		this.gameEnded = gameEnded;
	}
	
	@Override
	public String toString() {
		return String.valueOf(gameEnded) + String.valueOf(wasSucessefull);
	}

}
