package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import gui.SnakeGui;
import remote.RemoteBoard;

public class Server {
	public RemoteBoard board;
	
	public class DealWithClient extends Thread {
		public DealWithClient(Socket socket) throws IOException {
			doConnections(socket);
		}
		private BufferedReader in;
		
		@Override
		public void run() {
			try {
				serve();
				board = new RemoteBoard();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		private PrintWriter out;

		void doConnections(Socket socket) throws IOException {
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())),
					true);
		}
		private void serve() throws IOException {   //No serviço, deve o servidor apenas receber keys de input. VK_UP, VK_DOWN...
			while (true) {
				String str = in.readLine();
				System.out.println(str);
				//int key = Integer.parseInt(str);
				//board.handleKeyPress(key);
				System.out.println("Eco:" + str);
				out.println(str);
			}
		}
	}
	public static final int PORTO = 8080;
	public static void main(String[] args) {
		try {
			new Server().startServing();
		} catch (IOException e) {
			// ...
		}
	}

	public void startServing() throws IOException {
		ServerSocket ss = new ServerSocket(PORTO);
		try {
			while(true){
				Socket socket = ss.accept();
				new DealWithClient(socket).start();
			}			
		} finally {
			ss.close();
		}
	}
}
