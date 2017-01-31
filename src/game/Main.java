package game;

import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	private JLabel movementLabel = new JLabel("Ruch");
	
	static {
		try {
			MyLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public enum GameType {
		USER_VS_COMPUTER,
		USER_VS_USER
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
		
		JPanel panel = new JPanel();
		initComponents();
		
		GroupLayout layoutt = new GroupLayout(panel);
		layoutt.setAutoCreateGaps(true);
		layoutt.setAutoCreateContainerGaps(true);
	
		GroupLayout.SequentialGroup leftToRight = layoutt.createSequentialGroup();
		
		GroupLayout.ParallelGroup columnFirst = layoutt.createParallelGroup();
		columnFirst.addComponent(myPanel);
		columnFirst.addComponent(movementLabel, GroupLayout.Alignment.CENTER);
		leftToRight.addGroup(columnFirst);
		leftToRight.addComponent(enemyPanel);
		
		GroupLayout.SequentialGroup topToBottom = layoutt.createSequentialGroup();
		GroupLayout.ParallelGroup rowTop = layoutt.createParallelGroup();
		rowTop.addComponent(myPanel);
		rowTop.addComponent(enemyPanel);
		topToBottom.addGroup(rowTop);
		topToBottom.addComponent(movementLabel);
		
		layoutt.setHorizontalGroup(leftToRight);
		layoutt.setVerticalGroup(topToBottom);
		
		
		panel.setLayout(layoutt);
		
		add(panel);
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
	}
	
	public static void refreshPanels() {
		myPanel.refresh(myBoard);
		enemyPanel.refresh(enemyBoard);
		
	}
	
	
	
	
	
}
