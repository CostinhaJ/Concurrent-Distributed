package gui;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

import javax.net.ssl.StandardConstants;

import environment.LocalBoard;
import remote.Client;
import server.Server;

public class Main {
	public static void main(String[] args) {
		//LocalBoard board = new LocalBoard();
		//SnakeGui game = new SnakeGui(board,600,0);
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
					new Client();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			break;
		
		}
		
		
	}
		
}
