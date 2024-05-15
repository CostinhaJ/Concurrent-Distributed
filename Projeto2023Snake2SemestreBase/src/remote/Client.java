package remote;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import environment.Board;
import environment.BoardPosition;
import environment.LocalBoard;
import gui.SnakeGui;
import server.Server;

public class Client{
	
	private ActionResult state;
	private ObjectInputStream in;
	private PrintWriter out;
	private Socket socket;
	
	public Client() throws IOException {
		try {
			doConnections();
			runClient();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Servidor Ficou Indisponível!\nA terminar processo...");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {// ...
				e.printStackTrace();
			}
		}

	}
	public void doConnections() throws IOException {
		InetAddress endereco = InetAddress.getByName(null); //localhost/127.0.0.1
		System.out.println("Endereco:" + endereco);
		socket = new Socket(endereco, Server.PORTO);		
		System.out.println("Socket:" + socket);
		in = new ObjectInputStream(socket.getInputStream ());		
		out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream())), true);
	}


	private void runClient() {
		state = new ActionResult(true, false);
		try {
			while (!state.gameEnded) {
				int x = (int) (Math.random() * Board.WIDTH);
				int y = (int) (Math.random() * Board.HEIGHT);
				out.println(String.valueOf(x) + " " + String.valueOf(y) + "\n");
				out.flush();
				state = (ActionResult) in.readObject();  // Espera ativa. Não passa daqui				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO		
		new Client().runClient();;

	}	

	public void StreamInput(String s) throws IOException {
		out.println(s);	 		
	}

}
