package server;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import environment.BoardPosition;
import environment.Cell;
import environment.LocalBoard;
import game.AutomaticSnake;
import game.Goal;
import game.Killer;
import game.Snake;
import gui.SnakeGui;
import remote.ActionResult;


public class Server {
	
	public LocalBoard board;
	SnakeGui game;
	public static final int PORTO = 8080;
	
	public Server() {
		this.board = new LocalBoard();
		game = new SnakeGui(board, 0, 0);

		new Thread(() -> {
			try {
				System.out.println("Server is waiting for clients...");
				ServerSocket ss = new ServerSocket(PORTO);
				try {
					while (true) {
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
		}).start();

		new Thread(() -> {
			game.init();
		}).start();

	}
	
	public static void main(String[] args) {
			new Server();
	}
	
	public class ClientProcess extends Thread {
		
		private BufferedReader in;
		private ObjectOutputStream out;
		private PrintWriter outEco;		
		
		public ClientProcess(Socket socket) throws IOException {  
			System.out.println("Client connected!");
			doConnections(socket);
		}
		
		void doConnections(Socket socket) throws IOException {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new ObjectOutputStream ( socket.getOutputStream ());					
		}
		
		@Override
		public void run() {			
			try {
				serve();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		private void serve() throws IOException {

			new Thread(() -> {
				while (true){
						try{
							String str = in.readLine();			 //problema aqui
							//String str = "10 29";
							if (str != null && !str.isEmpty()) {						
								String[] parts = str.split(" ");
								int x = Integer.parseInt(parts[0]);								
								int y = Integer.parseInt(parts[1]);								
								out.writeObject(new ActionResult(HandleClientCommand(new BoardPosition(x, y)), board.isFinished()));
								out.flush();
								out.reset();
								sleep(200);;
							}							
						} catch (Exception e) {
							e.printStackTrace();					
								}
					board.setChanged();
				}
			}).start();

		}
		private boolean HandleClientCommand(BoardPosition coords) throws InterruptedException{
			Cell bp = board.getCell(coords);
			if(bp.isOcupiedByGameElement() && !bp.getGameElement().isAlive() && !(bp.getGameElement() instanceof Killer)  && !bp.isOcupiedByGoal() ){
				board.getCell(coords).removeGameElement(bp.getGameElement());
				return true;
			}
			if(bp.isOcupiedBySnake() && !bp.getOcuppyingSnake().isAlive()) {
				board.removeSnake(bp.getOcuppyingSnake());
				return true;
			}
			return false;
		}
	}
}
