package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import java.awt.event.KeyEvent;

import game.HumanSnake;
import gui.SnakeGui;
import remote.RemoteBoard;
import environment.Cell;

public class Server {
	
	public RemoteBoard board;
	public static final int PORTO = 8080;
	
	public Server(){
		this.board = board;
		this.board = new RemoteBoard();
		SnakeGui game = new SnakeGui(board, 0, 0);
		game.init();
		try {
			startServing();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Server couldn't boot");
			System.exit(PORTO);
		}
	}
	
	public static void main(String[] args) {
		try {
			new Server().startServing();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(PORTO);
		}
	}
	
	 public void startServing() throws IOException {
		
		System.out.println("Server is waiting for clients...");
		ServerSocket ss = new ServerSocket(PORTO);
		try {
			while(true){
				Socket socket = ss.accept();
				new ClientProcess(socket).start();
			}
		} finally {
			ss.close();
		}
	}
	
	public class ClientProcess extends Thread {
		
		private BufferedReader in;
		private PrintWriter out;
		private HumanSnake snake;
		
		//Quando o processo do cliente é criado, as conexões são feitas automáticamente
		public ClientProcess(Socket socket) throws IOException {  
			System.out.println("Client connected!");
			doConnections(socket);
			snake= new HumanSnake(socket.getLocalPort(), board);
			board.addSnake(snake);
		}
		
		void doConnections(Socket socket) throws IOException {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())),
					true);
			
		}
		
		@Override
		public void run() {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			try {
				serve();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//O handeling de inputs (VK_DOWN, LEFT, RIGHT, UP) 
		private void serve() throws IOException {
			
			while (true) {
			    try {
			        String str = in.readLine();			       
			        if (str != null && !str.isEmpty()) {
			            int key = str.charAt(0);
			            //System.out.println("Cliente disse: " + key);
			            out.println("Echo: " + key);
			            key = KeyEvent.getExtendedKeyCodeForChar(key);
			            Cell nextCell = HandleClientCommand(key);
			            if (nextCell != null)
			                snake.nextMove(nextCell);
			        }
			    } catch (IOException e) {			        
			        e.printStackTrace();
			    }

			    try {			    
			        sleep(board.PLAYER_PLAY_INTERVAL);
			    } catch (InterruptedException e) {		    	
			        e.printStackTrace();
			    }			    
			}

		}	
		
		private Cell HandleClientCommand(int keyCode) {
			
		switch(keyCode) {
			case KeyEvent.VK_W:
				//como ir buscar a classe que chamou handle key, para poder mexer a sua snake
				//System.out.println("Teste Up");
					return new Cell(snake.getCells().getLast().getPosition().getCellAbove());
				
			case KeyEvent.VK_A:
				//System.out.println("Teste Left");
				return new Cell(snake.getCells().getLast().getPosition().getCellLeft());				
				
			case KeyEvent.VK_S:
				//System.out.println("Teste Down");
				return new Cell(snake.getCells().getLast().getPosition().getCellBelow());				
				
			case KeyEvent.VK_D:
				//System.out.println("Teste Right");
				return new Cell(snake.getCells().getLast().getPosition().getCellRight());
			
			default:
				return null;
				
			}
		}
	}
}

