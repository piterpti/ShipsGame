package game;

import java.io.IOException;

import javax.swing.SwingUtilities;

import layout.MainMenu;
import layout.NetworkType;
import logger.MyLogger;

/*
 * Main application class
 * @author Piter
 */
public class Main {
	
	static {
		try {
			MyLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static GameFrame gameFrame;
	private static MainMenu mainMenu;
	private static NetworkType networkType;
	
	public static GameType gameType = GameType.USER_VS_COMPUTER;
	
	public static String NICKNAME = "Piter";
	public static int PORT = 8888;

	public enum GameType {
		USER_VS_COMPUTER,
		HOST,
		CLIENT
	}
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainMenu = new MainMenu();
				mainMenu.setVisible(true);
			}
		});
		
	}
	
	public static void startGame() {

		if (gameFrame != null) {
			gameFrame.dispose();
			gameFrame = null;
		}
		
		gameType = GameType.USER_VS_COMPUTER;
		
		mainMenu.setVisible(false);
		gameFrame = new GameFrame();
		gameFrame.setVisible(true);
	}
	
	public static void backToMenu() {
		if (gameFrame != null) {
			gameFrame.dispose();
			gameFrame = null;
		}
		
		if (networkType != null) {
			networkType.dispose();
			networkType = null;
		}
		
		mainMenu.setVisible(true);
	}
	
	public static void startNetworkGame() {
		if (networkType != null) {
			networkType.dispose();
			networkType = null;
		}
		
		mainMenu.setVisible(false);
		networkType = new NetworkType();
		networkType.setVisible(true);
	}
	

}
