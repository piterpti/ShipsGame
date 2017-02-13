package communiaction;

import static constants.Constants.LOGGER;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import communiaction.Message.TypeMsg;
import game.Game;
import game.Game.Move;

public class Host extends Thread {
	
	private final int port;
	private ServerSocket hostServer;
	private Socket socket;
	
	private ObjectInputStream streamIn = null;
	private ObjectOutputStream streamOut;
	
	private Move startMove;
	
	private boolean endGame = false;
	
	private Object lock = new Object();
	
	private boolean clientAccepted = false;
	
	private Message receivedMsg = null;
	
	public Host(int aPort, Move aStartMove) {
		port = aPort;
		startMove = aStartMove;
		configureServer();
	}
	
	private void configureServer() {
		
		try {
			hostServer = new ServerSocket(port);
			hostServer.setSoTimeout(1000 * 60);
		} catch (IOException e) {
			LOGGER.warning("Error when creating server:" + e.toString());
		}
	}
	
	@Override
	public void run() {
	
		if (hostServer == null) {
			LOGGER.warning("Host server is null..");
			return;
		}
		
		while (true && !endGame) {
			
			try {
				LOGGER.info("Waiting for client connection");
				socket = hostServer.accept();
				LOGGER.info("Client accepted: " + socket.toString());
				
				setClientAccepted(true);
				
				streamOut  = new ObjectOutputStream(socket.getOutputStream());
				streamIn = new ObjectInputStream((socket.getInputStream()));
				
				streamOut.writeObject(new Message(TypeMsg.WELCOME, startMove));
				
				while (!endGame) {
					synchronized (lock) {
						receivedMsg = (Message) streamIn.readObject();
						LOGGER.info("Message from client: " + receivedMsg.toString());
					}
					
					Game.hostRecMsg();
					receivedMsg = null;
					
				}
				
			} catch (Exception e) {
				LOGGER.warning("Communication problem: " + e.toString());
			}
		}
	}
	
	public void close()  {
		LOGGER.info("Closing connection");
		try {
			if (socket != null) {
				socket.close();
			}
			if (hostServer != null) {
				hostServer.close();
			}
			
			if (streamIn != null) {
				streamIn.close();
			}
			
			if (streamOut != null) {
				streamOut.close();
			}
		} catch (IOException e) {
			LOGGER.warning("Problem with closing resources");
		}
	}
	
	public void sendMessage(Message msg) {
		if (streamOut != null) {
			try {
				streamOut.writeObject(msg);
				streamOut.flush();
			} catch (IOException e) {
				LOGGER.info("Cannot send message - exception:" + e.toString());
			}
		} else {
			LOGGER.info("Cannot send message - printWriterOut is null");
		}
	}
	
	public Message getMessage() {
		synchronized (lock) {
			return receivedMsg;
		}
	}
	
	public boolean isEndGame() {
		return endGame;
	}
	
	public void setEndGame(boolean aEndGame) {
		endGame = aEndGame;
	}
	
	public boolean isClientAccepted() {
		synchronized (lock) {
			return clientAccepted;
		}
	}
	
	public void setClientAccepted(boolean result) {
		synchronized (lock) {
			clientAccepted = result;
		}
	}
}
