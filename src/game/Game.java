package game;

import static constants.Constants.LOGGER;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bot.Bot;
import communiaction.Client;
import communiaction.ConnectionConfig;
import communiaction.Host;
import communiaction.Message;
import communiaction.Message.TypeMsg;
import game.Main.GameType;
import layout.BoardPanel;
import model.Board;
import model.FieldType;
import model.Point;
import tools.ShipGenerator;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static Board MY_BOARD;
	public static Board ENEMY_BOARD;
	
	public static Object lock = new Object();
	
	private static BoardPanel myPanel;
	private static BoardPanel enemyPanel;
	
	private static Host host;
	private static Client client;
	
	private static Bot enemy;
	
	public enum Move {
		HOST,
		CLIENT,
		USER,
		BOT
	}
	
	public static Move move = Move.HOST;
	
	private static JLabel movementLabel = new JLabel("Turn: ");
	
	public Game() {
		initComponents();
		initGame();
	}
	
	private void initGame() {
		
		synchronized (lock) {
			ENEMY_BOARD = new Board();
			ENEMY_BOARD.setMyBoard(false);
			
			if (MY_BOARD == null) {
				MY_BOARD = new Board();
				MY_BOARD.setMyBoard(true);
				ShipGenerator.generateShips(MY_BOARD);
			}
		}
		
		switch (Main.gameType) {

		case HOST:
			initHost();
			break;
		case CLIENT:
			initClient();
			break;
		case USER_VS_COMPUTER:
			initBot();
			break;
		}
		refreshPanels();
	}
	
	

	private void lossPlayer() {
		Random random = new Random();
		if (Main.gameType == GameType.HOST) {
			move = random.nextInt(2) == 0 ? Move.CLIENT : Move.HOST;
		} else if (Main.gameType == GameType.USER_VS_COMPUTER) {
			move = random.nextInt(2) == 0 ? Move.USER : Move.BOT;
		}
	}

	private void initComponents() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Ships game");
		this.setResizable(false);
		
		myPanel = new BoardPanel(false, "Your board");
		enemyPanel = new BoardPanel(true, "Enemy board");
		
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
//		this.setVisible(true);
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
			LOGGER.warning("Wrong game type");
			break;
		}
	}
	
	private void initClient() {
		client = new Client();
		client.start();
		
//		setVisible(true);
	}
	
	private void initHost() {
		lossPlayer();
		host = new Host(ConnectionConfig.PORT, move);
		host.start();
		
		setTurnText();
		
//		setVisible(true);
	}
	
	
	public static void refreshPanels() {
		synchronized (lock) {
			myPanel.refresh(MY_BOARD);
			enemyPanel.refresh(ENEMY_BOARD);
		}
	}
	
	public static void hostRecMsg() {
		
		Message recMsg = host.getMessage();
			
		switch (recMsg.getType()) {
		
			case ATTACK:
				HashMap<Point, FieldType> points = MY_BOARD.checkIsShipHit(recMsg.getPoint());
				if (points != null) {
					if (isShipHitted(points)) {
						move = Move.CLIENT;
					} else {
						move = Move.HOST;
					}
					refreshPanels();
					
					if (checkLose(false)) {
						Message endMsg = new Message(TypeMsg.END, move);
						host.sendMessage(endMsg);
						
						endGame(false);
						
					} else {
						Message sendMsg = new Message(recMsg.getId() + 1, null, TypeMsg.POINTS, move);
						sendMsg.setEnemyPoints(points);
						host.sendMessage(sendMsg);
					}
				}
				
				break;
				
			case POINTS:
				ENEMY_BOARD.setFieldsTo(recMsg.getEnemyPoints());
				refreshPanels();
				move = recMsg.getMove();
				break;
				
			case END:
				endGame(true);
				break;
				
			default:
				LOGGER.warning("Incorrect message type");
				break;
		
		}
		
		setTurnText();
		
	}
	
	public static void clientRecMsg() {
		Message recMsg = client.getMessage();
		
		switch (recMsg.getType()) {
			case ATTACK:
				HashMap<Point, FieldType> points = MY_BOARD.checkIsShipHit(recMsg.getPoint());
				if (points != null) {
					if (isShipHitted(points)) {
						move = Move.HOST;
					} else {
						move = Move.CLIENT;
					}
					
					refreshPanels();
					
					if (checkLose(false)) {
						Message endMsg = new Message(TypeMsg.END, move);
						client.sendMessage(endMsg);
						
						endGame(false);
						
					} else {
						Message sendMsg = new Message(recMsg.getId() + 1, null, TypeMsg.POINTS, move);
						sendMsg.setEnemyPoints(points);
						client.sendMessage(sendMsg);
					}
				}
				
				break;
				
			case POINTS:
				ENEMY_BOARD.setFieldsTo(recMsg.getEnemyPoints());
				move = recMsg.getMove();
				refreshPanels();
				break;
				
			case WELCOME:
				move = recMsg.getMove();
				break;
				
			case END:
				endGame(true);
				break;
				
			default:
				LOGGER.warning("Incorrect message type");
				break;
		
		}
		
		setTurnText();
	}
	
	public static boolean isYourMove() {
		if (Main.gameType == GameType.HOST && move == Move.HOST) {
			return true;
		}
		if (Main.gameType == GameType.CLIENT && move == Move.CLIENT) {
			return true;
		}
		
		if (Main.gameType == GameType.USER_VS_COMPUTER && move == Move.USER) {
			return true;
		}
		
		return false;
	}
	
	private static boolean isShipHitted(HashMap<Point, FieldType> points) {
		for (Map.Entry<Point, FieldType> entry : points.entrySet()) {
			if (entry.getValue() == FieldType.DAMAGED ||
					entry.getValue() == FieldType.DESTROYED) {
				return true;
			}
		}
		return false;
	}
	
	private static void setTurnText() {
		if (isYourMove()) {
			movementLabel.setText("YOUR TURN");
		} else {
			movementLabel.setText("ENEMY TURN");
		}
	}
	
	private static boolean checkLose(boolean bot) {
		FieldType[][] fields;
		if (!bot) {
			fields = MY_BOARD.getFields();
		} else {
			fields = ENEMY_BOARD.getFields();
		}
		
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (fields[x][y] == FieldType.SHIP) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private static boolean checkEnemyLose(Board b) {
		FieldType[][] fields = b.getFields();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (fields[x][y] == FieldType.SHIP) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static void endGame(boolean win) {
		
		if (Main.gameType == GameType.CLIENT) {
			client.setGameEnd(true);
			client.closeConn();
		}
		if (Main.gameType == GameType.HOST) {
			host.setEndGame(true);
			host.close();
		}
		
		if (win) {
			JOptionPane.showMessageDialog(null, "You win!");
		} else {
			JOptionPane.showMessageDialog(null, "You lose!");
		}
		
		Main.endGame();
		
	}
	
	private void initBot() {
		
		enemy = new Bot();
		ENEMY_BOARD = enemy.getMyBoard();
		lossPlayer();
		botTurn();
	}
	
	public static void botTurn() {
		BotTurn botTurn = new BotTurn();
		Thread t = new Thread(botTurn);
		t.start();
	}
	
	public static void userMove(Point point) {
		HashMap<Point, FieldType> points = ENEMY_BOARD.checkIsShipHit(point);
		if (points != null) {
			if (isShipHitted(points)) {
				move = Move.USER;
			} else {
				move = Move.BOT;
			}
			
			refreshPanels();
			if (checkLose(false)) {
				endGame(false);
			}
			else if (checkEnemyLose(ENEMY_BOARD)) {
				endGame(true);
			} else if (move == Move.BOT) {
				botTurn();
			}
		}
		
		setTurnText();
	}
	
	static class BotTurn implements Runnable {
		
		private int sleepTime = 1000;
		
		public BotTurn() {
			sleepTime = new Random().nextInt(1500) + 1000;
		}
		
		@Override
		public void run() {
				while (!isYourMove()) {
				try {
					Thread.sleep(5);
					LOGGER.info("Bot thread sleep for " + sleepTime + " ms");
				} catch (InterruptedException e) {
					LOGGER.warning("Problem with bot thread interrupting: " + e.toString());
				}
				Point toAttack = enemy.nextTurn();
				HashMap<Point, FieldType> points = MY_BOARD.checkIsShipHit(toAttack);
				refreshPanels();
				if (checkLose(true)) {
					endGame(true);
				}
				else if (checkEnemyLose(MY_BOARD)) {
					endGame(false);
				}
				if (points == null) {
					break;
				}
				if (isShipHitted(points)) {
					move = Move.BOT;
				} else {
					move = Move.USER;
				}			
			}
	
			setTurnText();
		}
		
	}
	
	public void setMyBoard(Board b) {
		MY_BOARD = b;
		refreshPanels();
	}
}