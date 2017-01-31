package game;

import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import bot.Bot;
import layout.GamePanel;
import logger.MyLogger;
import model.Board;
import model.FieldType;
import tools.ShipGenerator;

/**
 * Main class
 * Here app is starting
 * @author Piter
 *
 */
public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public static Board myBoard;
	public static Board enemyBoard;
	
	public static Bot bot;
	public static boolean userMove = true;
	
	private static GamePanel myPanel;
	private static GamePanel enemyPanel;
	
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
		
		startGameWithComputer();
	}
	
	private void initComponents() {
		myPanel = new GamePanel(false);
		enemyPanel = new GamePanel(true);				
	}
	
	public static void refreshPanels() {
		myPanel.refresh(myBoard);
		enemyPanel.refresh(enemyBoard);
	}
	
	public static boolean checkWin(Board board) {
		FieldType[][] fields = board.getFields();
		
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (fields[x][y] == FieldType.SHIP) {
					return false;
				}
			}
		}
		
		JOptionPane.showMessageDialog(null, "You win!", "Game over", JOptionPane.OK_OPTION);
		System.exit(0);
		return true;
	}
	
	private void startGameWithComputer() {
		
		myBoard = new Board();
		myBoard.setMyBoard(true);
		ShipGenerator.generateShips(myBoard);
		
		bot = new Bot();
		enemyBoard = Bot.myBoard;
		bot.nextTurn();
		
		refreshPanels();
	}
	
	
	
	
	
}
