package gui;

import environment.LocalBoard;

import remote.RemoteBoard;
import game.Server;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
	public static void main(String[] args) {
		LocalBoard board = new LocalBoard();
		SnakeGui game = new SnakeGui(board,0,0);
		game.init();
		// Launch server
		// TODO
		//Server server = new Server();
		
	}
}
