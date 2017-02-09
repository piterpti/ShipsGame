package game;

import static constants.Constants.LOGGER;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client extends Thread {

	private Socket socket;
	private DataOutputStream streamOut;
	
	private String line;
	private Object lock = new Object();
	
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
	
	private String waitForClick() {
		try {
			synchronized (lock) {
				lock.wait();
			}
		} catch (InterruptedException e) {
			LOGGER.warning("Problem with thread interrupting" + e.toString());
		}
		return line;
	}
	
	@Override
	public void run() {
		
		try {
			startConn();
			
			line = "";
			
			while (!line.equals("END")) {
				line = waitForClick();
				streamOut.writeUTF(line);
				streamOut.flush();
				
				line = in.readLine();
				LOGGER.info("Recived from server: " + line);
				
			}
		}
		catch (Exception e) {
			LOGGER.warning("Problem with connection on port" + Main.PORT + ": " + e.toString());
		}
	}
	
}
