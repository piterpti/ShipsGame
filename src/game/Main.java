package game;

import javax.swing.SwingUtilities;

import layout.MainMenu;
import layout.NetworkType;

/*
 * Main applcation class
 * @author Piter
 */
public class Main {
	
	private static GameFrame gameFrame;
	private static MainMenu mainMenu;
	private static NetworkType networkType;
	
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
