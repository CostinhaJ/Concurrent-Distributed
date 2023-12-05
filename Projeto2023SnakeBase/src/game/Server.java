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

import remote.RemoteBoard;

public class Server {
	
	public static final int PORTO = 8080;
	
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
		
		//Quando o processo do cliente é criado, as conexões são feitas automáticamente
		public ClientProcess(Socket socket) throws IOException {  
			System.out.println("Client connected!");
			doConnections(socket);
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
				serve();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//O serviço prestado ao cliente deve ser da atualização da interface a cada tick e o handeling de inputs (VK_DOWN, LEFT, RIGHT, UP) 
		private void serve() throws IOException {
				while (true) {
					String str = in.readLine();
					System.out.println("Cliente disse: " + str);
					out.println("Echo: " + str);											
					
				}
			}	
		
	}
	
}
