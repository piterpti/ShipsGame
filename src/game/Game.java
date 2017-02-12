package game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import communiaction.Client;
import communiaction.Host;
import communiaction.Message;
import communiaction.Message.TypeMsg;
import game.Main.GameType;
import layout.GamePanel;
import model.Board;
import model.FieldType;
import model.Point;
import tools.ShipGenerator;

import static constants.Constants.LOGGER;

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
		HOST,
		CLIENT
	}
	
	public static Move move = Move.HOST;
	
	private static JLabel movementLabel = new JLabel("Turn: ");
	
	public Game() {
		initComponents();
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
		
		synchronized (lock) {
			ENEMY_BOARD = new Board();
			ENEMY_BOARD.setMyBoard(false);
			
			MY_BOARD = new Board();
			MY_BOARD.setMyBoard(true);
			ShipGenerator.generateShips(MY_BOARD);
		}

		refreshPanels();
	}

	private void lossPlayer() {
		Random random = new Random();
		move = random.nextInt(2) == 0 ? Move.CLIENT : Move.HOST;
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
			LOGGER.warning("Wrong game type");
			break;
		}
	}
	
	private void initClient() {
		client = new Client();
		client.start();
	}
	
	private void initHost() {
		lossPlayer();
		host = new Host(Main.PORT, move);
		host.start();
		setTurnText();
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
				if (isShipHitted(points)) {
					move = Move.CLIENT;
				} else {
					move = Move.HOST;
				}
				refreshPanels();
				Message sendMsg = new Message(recMsg.getId() + 1, null, TypeMsg.POINTS, move);
				sendMsg.setEnemyPoints(points);
				host.sendMessage(sendMsg);
				
				break;
				
			case POINTS:
				ENEMY_BOARD.setFieldsTo(recMsg.getEnemyPoints());
				refreshPanels();
				move = recMsg.getMove();
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
				if (isShipHitted(points)) {
					move = Move.HOST;
				} else {
					move = Move.CLIENT;
				}
				refreshPanels();
				Message sendMsg = new Message(recMsg.getId() + 1, null, TypeMsg.POINTS, move);
				sendMsg.setEnemyPoints(points);
				client.sendMessage(sendMsg);
				
				break;
				
			case POINTS:
				ENEMY_BOARD.setFieldsTo(recMsg.getEnemyPoints());
				move = recMsg.getMove();
				refreshPanels();
				break;
				
			case WELCOME:
				move = recMsg.getMove();
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

	
}
