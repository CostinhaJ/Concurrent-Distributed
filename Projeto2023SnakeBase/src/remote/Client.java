package remote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import game.Server;
/** Remore client, only for part II
 * 
 * @author luismota
 *
 */

public class Client {

	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
	public static void main(String[] args) {
		new Client().runClient();
	}

	public void runClient() {
		try {
			connectToServer();
			sendMessages();
		} catch (IOException e) {// ERRO...
		} finally {//a fechar...
			try {
				socket.close();
			} catch (IOException e) {//... 
			}
		}
	}

	void connectToServer() throws IOException {
		InetAddress endereco = InetAddress.getByName(null);
		System.out.println("Endereco:" + endereco);
		socket = new Socket(endereco, Server.PORTO);
		System.out.println("Socket:" + socket);
		in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(),true);
		//out = new PrintWriter(new BufferedWriter(
			//	new OutputStreamWriter(socket.getOutputStream())), true);
	}

	void sendMessages() throws IOException {
			
		BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        	while(true) {
        		String message = userInput.readLine();
        		out.println(message);
        		try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				out.println("FIM");
			}
        }
		
	}
	
}
