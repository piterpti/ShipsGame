package game;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import layout.GamePanel;
import logger.MyLogger;
import model.Board;
import tools.ShipGenerator;

/**
 * Main class
 * Here app is starting
 * @author Piter
 *
 */
public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public static final String MY_BOARD = "my_board";
	public static final String ENEMY_BOARD = "enemy_board";
	
	public static GamePanel myPanel;
	public static GamePanel enemyPanel;
	
	public static Board myBoard;
	public static Board enemyBoard;
	
	static {
		try {
			MyLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
		this.setResizable(false);
		setLayout(new GridLayout(1, 2));
		initComponents();
		this.pack();
		this.setVisible(true);
	}
	
	private void initComponents() {
		myPanel = new GamePanel(false);
		myBoard = new Board();
		ShipGenerator.generateShips(myBoard);
		
		enemyPanel = new GamePanel(true);
		enemyPanel.setEnemy(true);
		enemyBoard = new Board();
		ShipGenerator.generateShips(enemyBoard);
		
		refreshPanels();
		
		add(myPanel);
		add(enemyPanel);
	}
	
	private static void refreshPanels() {
		myPanel.refresh(myBoard);
		enemyPanel.refresh(enemyBoard);
		
	}
	
}
