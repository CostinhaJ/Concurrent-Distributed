package gui;

import environment.LocalBoard;

import remote.RemoteBoard;
import game.Server;

public class Main {
	public static void main(String[] args) {
		//LocalBoard board = new LocalBoard();
		//SnakeGui game = new SnakeGui(board,0,0);
		//game.init();
		// Launch server
		// TODO
		
		RemoteBoard board = new RemoteBoard();
		SnakeGui game = new SnakeGui(board, 0, 0);
		game.init();
	}
}
