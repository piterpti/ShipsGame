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
	
	private boolean gameEnd = false;
	
	private boolean hostFound = false;
	
	public Client() {
		try {
			socket = new Socket(ConnectionConfig.HOST, ConnectionConfig.PORT);	
		} catch (IOException e) {
			LOGGER.warning("Problem with creating socket on port " + ConnectionConfig.PORT + ": " + e.toString());
		}
	}
	
	private void startConn() throws IOException{ 		
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamIn = new ObjectInputStream(socket.getInputStream());
   }
	
	public void closeConn() {
		try {
			LOGGER.info("Closing connection - closeConn()");
		if (streamIn != null) {
			streamIn.close();
		} 
		if (streamOut != null) {
			streamOut.close();
		}
		if (socket != null) {
			socket.close();
		}
		} catch (IOException e) {
			LOGGER.warning("Problem when closing streams: " + e.toString());
		}
	}
	
	@Override
	public void run() {
		
		try {
			long counter = 0;
			while (socket == null) {
				try {
					socket = new Socket(ConnectionConfig.HOST, ConnectionConfig.PORT);
					counter++;
				} catch (IOException e) {
					if (counter % 20 == 0) {
						LOGGER.warning("Connection problem");
					}
				}
				Thread.sleep(50);
			}
			
			startConn();
			setHostFound(true);
			
			while (!gameEnd) {
				synchronized (lock) {
					receivedMsg = (Message) streamIn.readObject();
					LOGGER.info("Recived message from host: " + receivedMsg.toString());
				}
				Game.clientRecMsg();
				receivedMsg = null;
			}	
		}
		catch (Exception e) {
			LOGGER.warning("Problem with connection on port " + ConnectionConfig.PORT + ": " + e.toString());
		} finally {
			closeConn();
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
	
	public void setGameEnd(boolean aGameEnd) {
		gameEnd = aGameEnd;
	}
	
	public boolean isHostFouond() {
		synchronized (lock) {
			return hostFound;
		}
	}
	
	public void setHostFound(boolean aHostFound) {
		hostFound = aHostFound;
	}
}
