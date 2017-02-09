package game;

import javax.swing.SwingUtilities;

import layout.MainMenu;

/*
 * Main applcation class
 * @author Piter
 */
public class Main {
	
	private static GameFrame gameFrame;
	private static MainMenu mainMenu;
	
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
		
		gameFrame = new GameFrame();
		gameFrame.setVisible(true);
		mainMenu.setVisible(false);
	}
	
	public static void backToMenu() {
		if (gameFrame != null) {
			gameFrame.dispose();
			gameFrame = null;
		}
		
		mainMenu.setVisible(true);
	}
	

}
