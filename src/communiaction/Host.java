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
	
	private boolean gameEnd = false;
	
	private Object lock = new Object();
	private Object connectionLock = new Object();
	private Object endLock = new Object();
	
	private Message receivedMsg = null;
	
	private boolean clientConnected = false;
	
	private Game game;
	
	public Host(Game aGame, int aPort, Move aStartMove) {
		port = aPort;
		startMove = aStartMove;
		game = aGame;
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
		
		while (true && !gameEnd) {
			
			try {
				LOGGER.info("Waiting for client connection");
				socket = hostServer.accept();
				LOGGER.info("Client accepted: " + socket.toString());
				
				setClientConnected(true);
				
				streamOut  = new ObjectOutputStream(socket.getOutputStream());
				streamIn = new ObjectInputStream((socket.getInputStream()));
				
				streamOut.writeObject(new Message(TypeMsg.WELCOME, startMove));
				
				while (!gameEnd) {
					synchronized (lock) {
						receivedMsg = (Message) streamIn.readObject();
						LOGGER.info("Message from client: " + receivedMsg.toString());
					}
					
					game.hostRecMsg();
					receivedMsg = null;
					
				}
				
			} catch (Exception e) {
				LOGGER.warning("Communication problem: " + e.toString());
			}
		}
		
		LOGGER.info("Host thread ended work");
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
	
	public boolean isClientConnected() {
		synchronized (connectionLock) {
			return clientConnected;
		}
	}
	
	private void setClientConnected(boolean aClientConnected) {
		synchronized (connectionLock) {
			clientConnected = aClientConnected;
		}
	}
	
	public void setEndGame(boolean aGameEnd) {
		synchronized (endLock) {
			gameEnd = aGameEnd;
		}
	}
	
	public boolean isEndGame() {
		synchronized (endLock) {
			return gameEnd;
		}
	}
}
