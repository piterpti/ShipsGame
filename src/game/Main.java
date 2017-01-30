package game;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import layout.GamePanel;
import model.FieldId;
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
	
	public static GamePanel gamePanel1;
	public static GamePanel gamePanel2;
	
	public static Board myBoard;
	public static Board enemyBoard;
	
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
		setLayout(new GridLayout(1, 3));
		initComponents();
		this.pack();
		this.setVisible(true);
	}
	
	private void initComponents() {
		gamePanel1 = new GamePanel(MY_BOARD);
		add(gamePanel1);
		myBoard = new Board();
		ShipGenerator.GenerateRandomShips(myBoard);
		
		JLabel space = new JLabel("          ");
		add(space);
		
		gamePanel2 = new GamePanel(ENEMY_BOARD);
		add(gamePanel2);
		enemyBoard = new Board();
		ShipGenerator.GenerateRandomShips(enemyBoard);
		
		refreshPanels();
	}
	
	public static void boardEvent(String boardStr, FieldId fieldId) {
		if (boardStr.equals(MY_BOARD)) {
			
		} else {
			enemyBoard.clickEventEnemy(fieldId);
		}
		
		
		refreshPanels();
	}
	
	public static void refreshPanels() {
		gamePanel1.refreshPanel(myBoard);
		gamePanel2.refreshPanel(enemyBoard);
	}
}
