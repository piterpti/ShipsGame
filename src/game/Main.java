package game;

import java.io.IOException;

import javax.swing.SwingUtilities;

import communiaction.ConnectionConfig;
import layout.MainMenu;
import layout.NetworkType;
import layout.SetShips;
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

	private static MainMenu mainMenu;
	private static NetworkType networkType;
	public static Game gameFrame;
	
	public static GameType gameType = GameType.USER_VS_COMPUTER;

	public enum GameType {
		USER_VS_COMPUTER,
		HOST,
		CLIENT
	}
	
	public static void main(String[] args) {
		
		ConnectionConfig.getConnectionConfig();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainMenu = new MainMenu();
				mainMenu.setVisible(true);
			}
		});
	}
	
	public static void startGameComputer() {
		gameType = GameType.USER_VS_COMPUTER;
	}
	
	public static void backToMenu() {
		
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
	
	public static void endGame() {
		if (gameFrame != null) {
			gameFrame.dispose();
			gameFrame = null;
		}
		
		mainMenu.setVisible(true);
	}
	
	public static void setShips() {
		SetShips setShips = new SetShips(gameFrame);
		setShips.setVisible(true);
	}
	
	public static void startGame() {
		gameFrame = new Game();
		mainMenu.setVisible(false);	
	}
}
