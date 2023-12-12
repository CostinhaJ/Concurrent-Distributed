package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.KeyEvent;


import gui.SnakeGui;
import environment.LocalBoard;
import environment.Cell;

public class Server {
	
	public LocalBoard board;
	SnakeGui game;
	public static final int PORTO = 8080;
	
	public Server(){		
		this.board = new LocalBoard();
		game = new SnakeGui(board, 0, 0);
		
		 ExecutorService executorService = Executors.newFixedThreadPool(2); // 2 threads

	        try {	          
	            executorService.execute(() -> {            	              
	            	try {
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
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        			System.exit(PORTO);
	        		}		              
	            });

	         
	            executorService.execute(() -> {	               
	                game.init();
	            });
	        } finally {
	           executorService.shutdown();
	        }    
	}
	
	public static void main(String[] args) {
			new Server();
	}
	
	 /*public static void startServing() throws IOException {
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
	}*/
	
	public class ClientProcess extends Thread {
		
		private BufferedReader in;
		private ObjectOutputStream out;
		private PrintWriter outEco;
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
			out = new ObjectOutputStream ( socket.getOutputStream ());
			
			outEco = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			
			out.writeObject(board);				
			out.reset();
			
		}
		
		@Override
		public void run() {			
			try {
				serve();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//O handeling de inputs (VK_DOWN, LEFT, RIGHT, UP) 
		private void serve() throws IOException {
			
			 ExecutorService executorService = Executors.newFixedThreadPool(2); // 2 threads

		        try {	          
		            executorService.execute(() -> {            	              
		            	while(true) {
		            		try {
								out.writeObject(board);				
								out.reset();
							} catch (IOException e) {
								e.printStackTrace();
							}
		            			
		            	}            			
		        				              
		            });

		         
		            executorService.execute(() -> {
		            	
		            	while (true) {							
		    			    try {
		    			    	try {
									 String str = in.readLine();	
									 if (str != null && !str.isEmpty()) {
		    			            int key = str.charAt(0);
		    			            //System.out.println("Cliente disse: " + str + key);
		    			            //outEco.println("Echo: " + key);
		    			            key = KeyEvent.getExtendedKeyCodeForChar(key);
		    			            Cell nextCell = HandleClientCommand(key);			           
		    			            if (!nextCell.isOcupied()) {
		    			            	System.out.println("teste?");
		    			                snake.move(nextCell);
		    			               	}			         
		    			            sleep(board.REMOTE_REFRESH_INTERVAL);
		    			        }
		    			    } catch (InterruptedException e) {			       
		    			        e.printStackTrace();
		    			    }
								} catch (IOException e) {
									e.printStackTrace();
								}		    			      
		    			}
		            	
		            });
		        } finally {
		           executorService.shutdown();
		        }  
		}			
		
		private Cell HandleClientCommand(int keyCode) {
			
		switch(keyCode) {
			case KeyEvent.VK_W:
				//como ir buscar a classe que chamou handle key, para poder mexer a sua snake
				//System.out.println("Teste Up");
					return board.getCell(snake.getCells().getLast().getPosition().getCellAbove());
				
			case KeyEvent.VK_A:
				//System.out.println("Teste Left");
				return board.getCell(snake.getCells().getLast().getPosition().getCellLeft());				
				
			case KeyEvent.VK_S:
				//System.out.println("Teste Down");
				return board.getCell(snake.getCells().getLast().getPosition().getCellBelow());				
				
			case KeyEvent.VK_D:
				//System.out.println("Teste Right");
				return board.getCell(snake.getCells().getLast().getPosition().getCellRight());
			
			default:
				return null;
				
			}
		}
	}
}

