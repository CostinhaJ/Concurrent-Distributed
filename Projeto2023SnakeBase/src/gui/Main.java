package gui;

import environment.LocalBoard;
import remote.Client;
import remote.RemoteBoard;
import game.Server;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		//LocalBoard board = new LocalBoard();
		//SnakeGui game = new SnakeGui(board,0,0);
		//game.init();
		// Launch server
		// TODO
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("1-Boot Server;\n 2-Boot Cliente");
		
		int n = keyboard.nextInt();
		switch(n) {
			case 1:
				Server server = new Server();
			break;
			
			case 2:
				try {
					new Client().runClient();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
			break;
		
		}
		
		
	}
}
