package communiaction;

import static constants.Constants.LOGGER;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import game.Game;

public class Client extends Thread {

	private Socket socket;
	private ObjectOutputStream streamOut;
	private ObjectInputStream streamIn;
	
	private Object lock = new Object();
	
	private Message receivedMsg = null;
	
	public Client() {
		try {
			socket = new Socket("localhost", ConnectionConfig.PORT);	
		} catch (IOException e) {
			LOGGER.warning("Problem with creating socket on port " + ConnectionConfig.PORT + ": " + e.toString());
		}
	}
	
	private void startConn() throws IOException{ 		
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamIn = new ObjectInputStream(socket.getInputStream());
   }
	
	private void closeConn() {
		try {
		if (streamIn != null) {
			streamIn.close();
		} 
		if (streamOut != null) {
			streamOut.close();
		}
		} catch (IOException e) {
			LOGGER.warning("Problem when closing streams: " + e.toString());
		}
	}
	
	@Override
	public void run() {
		
		try {
			startConn();
			
			boolean gameEnd = false;
			
			while (!gameEnd) {
				synchronized (lock) {
					receivedMsg = (Message) streamIn.readObject();
					LOGGER.info("Recived message from host: " + receivedMsg.toString());
					
				}
				Game.clientRecMsg();
				receivedMsg = null;
			}
			
			closeConn();
		}
		catch (Exception e) {
			LOGGER.warning("Problem with connection on port " + ConnectionConfig.PORT + ": " + e.toString());
		}
	}
	
	public void sendMessage(Message msg) {
		try {
			streamOut.writeObject(msg);
			streamOut.flush();
		} catch (IOException e) {
			LOGGER.warning("Error when sending message: " + e.toString());
		}
	}
	
	public Message getMessage() {
		synchronized (lock) {
			return receivedMsg;
		}
	}
}
