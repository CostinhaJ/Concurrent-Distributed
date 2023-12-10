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

import environment.Cell;
import game.Server;
import game.HumanSnake;
/** Remore client, only for part II
 * 
 * @author luismota
 *
 */

public class Client{
	
	private RemoteBoard board;
	private ObjectInputStream in;
	private PrintWriter out;
	private Socket socket;
	
	public static void main(String[] args) {
		new Client().runClient();
	}

	public void runClient() {
		try {
			createRemoteBoard();
			connectToServer();		
		} catch (IOException e) { 
			System.out.println("Servidor Ficou Indisponível!\nA terminar processo...");
		} finally {//a fechar...
			try {
				socket.close();
			} catch (IOException e) {//... 
			}
		}
	}
	
	void createRemoteBoard() {
		board = new RemoteBoard(this);
	}

	void connectToServer() throws IOException {
		InetAddress endereco = InetAddress.getByName(null); //localhost/127.0.0.1
		System.out.println("Endereco:" + endereco);
		socket = new Socket(endereco, Server.PORTO);		
		System.out.println("Socket:" + socket);
		in = new ObjectInputStream ( socket.getInputStream ());
		out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream())), true);
		
	}
	
	void listenToServer() throws IOException {
    	while(true) {	              
    		//Board newBoardState = in.getObject()       
    		// if (state != null && !state.isEmpty()) {
    			//update das posições de snakes e obstacles
    			//trocar os objetos de snakes e obstáculos.
        		//board.setChanged();
    		// }
    	}		
	}

	void StreamInput(String s) throws IOException {    	
		if (s != null && !s.isEmpty()) 		                    
			out.println(s);	 
	}
	
}
