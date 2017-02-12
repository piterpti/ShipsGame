package communiaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import constants.Constants;

public class ConnectionConfig {

	public static String HOST = "localhost";
	public static int PORT = 8888;
	
	private static final String FILENAME = "connection.properties";
	
	public static void getConnectionConfig() {
		
		File connectionCfgFile = new File(FILENAME);
		
		if (!connectionCfgFile.exists()) {
			Constants.LOGGER.info("Connection config file not found - using default settings\nPORT: " + PORT + ", HOST: " + HOST);
			return;
		}
		
		Properties prop = new Properties();
		int port = -1;
		String host = null;
		boolean exception = false;
		
		try {
			InputStream inputStream = new FileInputStream(connectionCfgFile);
			
			prop.load(inputStream);
			
			port = Integer.valueOf(prop.getProperty("port"));
			host = prop.getProperty("host");
			
			
		} catch (Exception e) {
			Constants.LOGGER.warning("Problem when loading connection config: " + e.toString());
			exception = true;
		}
		
		if (!exception && port != -1 && host != null) {
			HOST = host;
			PORT = port;
			Constants.LOGGER.info("Connection settings:\nHOST=" + HOST + ", PORT=" + PORT);
		}
		
	}
	
}
