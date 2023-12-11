package remote;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import environment.Board;
import environment.Cell;
import game.Server;
import game.Snake;
import gui.SnakeGui;
import game.HumanSnake;
/** Remore client, only for part II
 * 
 * @author luismota
 *
 */

public class Client{
	
	private RemoteBoard board;
	private ObjectInputStream in;
	private BufferedReader inEco;
	private PrintWriter out;
	private Socket socket;
	
	public static void main(String[] args) throws ClassNotFoundException {
		new Client().runClient();
	}

	public void runClient() throws ClassNotFoundException{
		try {
			createGUI();
			connectToServer();	
			comunicateWithServer();
		} catch (IOException e) { 
			e.printStackTrace();
			System.out.println("Servidor Ficou Indisponível!\nA terminar processo...");
		} finally {//a fechar...
			try {
				socket.close();
			} catch (IOException e) {//... 
				e.printStackTrace();
			}
		}
	}
	
	void createGUI() {
		board = new RemoteBoard(this);
		SnakeGui game = new SnakeGui(board, 0, 0);
		game.init();
	}

	void connectToServer() throws IOException {
		InetAddress endereco = InetAddress.getByName(null); //localhost/127.0.0.1
		System.out.println("Endereco:" + endereco);
		socket = new Socket(endereco, Server.PORTO);		
		System.out.println("Socket:" + socket);
		in = new ObjectInputStream ( socket.getInputStream ());
		inEco = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream())), true);
		
	}
	
	void comunicateWithServer() throws IOException, ClassNotFoundException {
    	while(true) {	              
    		System.out.println("Server Eco: " + inEco.readLine()); 
    		//LinkedList<Snake> state = (LinkedList<Snake>)in.readObject();       
    		//if (state != null) {
    		//	board.snakes = state;
    		// }
    		board.setChanged();
    	}	
	}

	void StreamInput(String s) throws IOException {
		out.println(s);	 
	}
	
}
