package communiaction;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static constants.Constants.LOGGER;

public class Host extends Thread {
	
	private final int port;
	private ServerSocket hostServer;
	private Socket socket;
	private PrintWriter printWriterOut;
	
	private DataInputStream streamIn = null;
	
	private boolean endGame = false;
	private Object lock = new Object();
	
	private String line = "ppaa";
	
	public Host(int aPort) {
		port = aPort;
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
		
		while (true) {
			
			try {
				LOGGER.info("Waiting for client");
				socket = hostServer.accept();
				LOGGER.info("Client accepted: " + socket.toString());
				printWriterOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

				
				streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				
				while (!endGame) {
					line = streamIn.readUTF();
					LOGGER.info("Message from client: " + line);
					if (line.equals("END")) {
						endGame = true;
					}
					
					line = "xd";
					LOGGER.info("Sending:" + line);
					printWriterOut.write(line);
					printWriterOut.flush();
					
				}
				
				close();
				
			} catch (Exception e) {
				LOGGER.warning("Communication problem: " + e.toString());
			}
		}
	}
	
	private void close() throws IOException {
		if (socket != null) {
			socket.close();
		}
		
		if (streamIn != null) {
			streamIn.close();
		}
	}
	
	public void setLine(String aLine) {
//		line = aLine;
//		synchronized (lock) {
//			lock.notifyAll();
//		}
		
	}
	
	public boolean isEndGame() {
		return endGame;
	}
}
