package game;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import layout.GamePanel;

/**
 * Main class
 * Here app is starting
 * @author Piter
 *
 */
public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private GamePanel gamePanel;
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main();
			}
		});
	}
	
	public Main() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Ships game");
		gamePanel = new GamePanel();
		add(gamePanel);		
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
}
