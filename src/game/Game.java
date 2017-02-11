package game;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import communiaction.Client;
import communiaction.Host;
import communiaction.Message;
import layout.GamePanel;
import model.Board;
import tools.ShipGenerator;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static Board MY_BOARD;
	public static Board ENEMY_BOARD;
	
	public static Object lock = new Object();
	
	private static GamePanel myPanel;
	private static GamePanel enemyPanel;
	
	private static Host host;
	private static Client client;
	
	public enum Move {
		PLAYER,
		ENEMY
	}
	
	public static Move move = Move.PLAYER;
	
	private JLabel movementLabel = new JLabel("Ruch");
	
	public Game() {
		initComponents();
		lossPlayer();
		initGame();
	}
	
	private void initGame() {
		switch (Main.gameType) {
		case HOST:
			initHost();
			break;
		case CLIENT:
			initClient();
			break;
		default:
			break;
		}
		
		ENEMY_BOARD = new Board();
		ShipGenerator.generateShips(ENEMY_BOARD);
		
		MY_BOARD = new Board();
		ShipGenerator.generateShips(MY_BOARD);

	}

	private void lossPlayer() {
//		Random random = new Random();
//		move = random.nextInt(2) == 0 ? Move.PLAYER : Move.ENEMY;
	}

	private void initComponents() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Ships game");
		this.setResizable(false);
		
		myPanel = new GamePanel(false);
		enemyPanel = new GamePanel(true);
		
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	
		GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();
		
		GroupLayout.ParallelGroup columnFirst = layout.createParallelGroup();
		columnFirst.addComponent(myPanel);
		columnFirst.addComponent(movementLabel, GroupLayout.Alignment.CENTER);
		leftToRight.addGroup(columnFirst);
		leftToRight.addComponent(enemyPanel);
			
		GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();
		GroupLayout.ParallelGroup rowTop = layout.createParallelGroup();
		rowTop.addComponent(myPanel);
		rowTop.addComponent(enemyPanel);
		topToBottom.addGroup(rowTop);
		topToBottom.addComponent(movementLabel);
		
		layout.setHorizontalGroup(leftToRight);
		layout.setVerticalGroup(topToBottom);
		
		panel.setLayout(layout);
		
		add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	
	public static void move(Message msg) {
		switch (Main.gameType) {
		case HOST:
			host.sendMessage(msg);
			break;
		case CLIENT:
			client.sendMessage(msg);
			break;
		default:
			break;
		}
	}
	
	private void initClient() {
		client = new Client();
		client.start();
	}
	
	private void initHost() {
		host = new Host(Main.PORT);
		host.start();
	}
	
	public static void refreshPanels() {
		myPanel.refresh(MY_BOARD);
		enemyPanel.refresh(ENEMY_BOARD);
	}

	
}
