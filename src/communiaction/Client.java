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
	private Object connectionLock = new Object();
	
	private Message receivedMsg = null;
	
	private boolean gameEnd = false;
	private boolean connected = false;
	
	private Game game;
	
	
	public Client(Game aGame) {
		try {
			game = aGame;
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
			
			int counter = 0;
			while (socket == null) {
				try {
				Thread.sleep(100);
				socket = new Socket(ConnectionConfig.HOST, ConnectionConfig.PORT);
				counter++;
				
				
				} catch (Exception ignored) {
					if (counter % 10 == 0) {
						LOGGER.info("Host not found");
					}
				}
			}
			
			startConn();
			
			setConnected(true);
			
			while (!gameEnd) {
				synchronized (lock) {
					receivedMsg = (Message) streamIn.readObject();
					LOGGER.info("Recived message from host: " + receivedMsg.toString());
				}
				game.clientRecMsg();
				receivedMsg = null;
			}	
		}
		catch (Exception e) {
			LOGGER.warning("Problem with connection on port " + ConnectionConfig.PORT + ": " + e.toString());
			e.printStackTrace();
		} finally {
			closeConn();
		}
		
		LOGGER.info("Client thread ended work");
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
	
	private void setConnected(boolean aConnected) {
		synchronized (connectionLock) {
			connected = aConnected;
		}
	}
	
	public boolean isConnected() {
		synchronized (connectionLock) {
			return connected;
		}
	}
}
