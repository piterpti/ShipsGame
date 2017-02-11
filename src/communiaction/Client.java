package communiaction;

import static constants.Constants.LOGGER;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import constants.Constants;
import game.Main;

public class Client extends Thread {

	private Socket socket;
	private DataOutputStream streamOut;
	
	private String line;
	private Object lock = new Object();
	
	private boolean gameEnd = false;
	
	private BufferedReader in = null;
	
	public Client() {
		try {
			socket = new Socket("localhost", Main.PORT);
		
			
		} catch (IOException e) {
			LOGGER.warning("Problem with creating socket on port" + Main.PORT + ": " + e.toString());
		}
	}
	
	private void startConn() throws IOException{ 		
		streamOut = new DataOutputStream(socket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
   }
	
	public void setLine(String aLine) {
		line = aLine; 
		synchronized (lock) {
			lock.notifyAll();
		}
	}
	
	
	public void sendRequest(String utfLine) {
		try {
			streamOut.writeUTF(utfLine);
			streamOut.flush();
		} catch (IOException e) {
			LOGGER.warning("Error when sending request: " + e.toString());
		}
	}
	
	private void closeConn() {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
				Constants.LOGGER.warning("Problem with closing socket");
			}
		}
	}
	
	@Override
	public void run() {
		
		try {
			startConn();
			
			line = "PREV";
			
			while (!line.equals("END")) {
				line = in.readLine();
				LOGGER.info("Recived from server: " + line);
			}
			synchronized (lock) {
				gameEnd = false;
			}
			closeConn();
		}
		catch (Exception e) {
			LOGGER.warning("Problem with connection on port" + Main.PORT + ": " + e.toString());
		}
	}
	
	public boolean isGameEnd() {
		synchronized (lock) {
			return gameEnd;
		}
	}
	
	public String getLine() {
		return line;
	}
	
	
	
}
