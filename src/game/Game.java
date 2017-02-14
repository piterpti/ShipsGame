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
import layout.WaitFrame;
import model.Board;
import model.FieldType;
import model.Point;
import tools.ShipGenerator;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Board MY_BOARD;
	private Board ENEMY_BOARD;
	
	private Object lock = new Object();
	private Object moveLock = new Object();
	
	
	private BoardPanel myPanel;
	private BoardPanel enemyPanel;
	
	private Host host;
	private Client client;
	
	private WaitFrame waitFrame;
	
	private Bot enemy;
	
	public enum Move {
		HOST,
		CLIENT,
		USER,
		BOT
	}
	
	private Move move = Move.HOST;
	
	private JLabel movementLabel = new JLabel("Turn: ");
	
	public Game() {
		initComponents();
		initGame();
	}
	
	private void initGame() {
		
		ENEMY_BOARD = new Board();
		ENEMY_BOARD.setMyBoard(false);
		
		if (MY_BOARD == null) {
			MY_BOARD = new Board();
			MY_BOARD.setMyBoard(true);
			ShipGenerator.generateShips(MY_BOARD);
		}
		
		switch (Main.gameType) {

		case HOST:
			initHost();
			break;
		case CLIENT:
			initClient();
			break;
		case USER_VS_COMPUTER:
			setVisible(true);
			initBot();
			break;
		}
		refreshPanels();
	}
	
	

	private void lossPlayer() {
		Random random = new Random();
		if (Main.gameType == GameType.HOST) {
			setMove(random.nextInt(2) == 0 ? Move.CLIENT : Move.HOST);
		} else if (Main.gameType == GameType.USER_VS_COMPUTER) {
			setMove(random.nextInt(2) == 0 ? Move.USER : Move.BOT);
		}
	}

	private void initComponents() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Ships game");
		this.setResizable(false);
		
		myPanel = new BoardPanel(this, false, "Your board");
		enemyPanel = new BoardPanel(this, true, "Enemy board");
		
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
	}
	
	
	public void move(Message msg) {
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
		client = new Client(this);
		client.start();
		setVisible(false);
		
		waitFrame = new WaitFrame(this, "Connecting to host");
		waitFrame.setVisible(true);
		
		Runnable start = new Runnable() {
			
			@Override
			public void run() {
				while (!client.isConnected()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						LOGGER.warning("Wait for client thread:" + e.toString());
					}
				}
				LOGGER.fine("Connected to host - closing thread");
				setVisible(true);
				waitFrame.dispose();
				waitFrame = null;
				
			}
		};
		
		Thread thr = new Thread(start);
		thr.start();
	}
	
	private void initHost() {
		lossPlayer();
		host = new Host(this, ConnectionConfig.PORT, getMove());
		host.start();
		setVisible(false);
		
		waitFrame = new WaitFrame(this, "Waiting for player");
		waitFrame.setVisible(true);
		
		Runnable start = new Runnable() {
			
			@Override
			public void run() {
				while (! host.isClientConnected()) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						LOGGER.warning("Wait for client thread:" + e.toString());
					}
				}
				LOGGER.fine("Client connected - closing thread");
				setVisible(true);
				waitFrame.dispose();
				waitFrame = null;
			}
		};
		
		Thread thr = new Thread(start);
		thr.start();
		
		setTurnText();
	}
	
	
	public void refreshPanels() {
		synchronized (lock) {
			myPanel.refresh(MY_BOARD);
			enemyPanel.refresh(ENEMY_BOARD);
		}
	}
	
	public void hostRecMsg() {
		
		Message recMsg = host.getMessage();
			
		switch (recMsg.getType()) {
		
			case ATTACK:
				HashMap<Point, FieldType> points = MY_BOARD.checkIsShipHit(recMsg.getPoint());
				if (points != null) {
					if (isShipHitted(points)) {
						setMove(Move.CLIENT);
					} else {
						setMove(Move.HOST);
					}
					refreshPanels();
					
					if (checkLose(false)) {
						Message endMsg = new Message(TypeMsg.END, getMove());
						host.sendMessage(endMsg);
						
						endGame(false, false);
						
					} else {
						Message sendMsg = new Message(recMsg.getId() + 1, null, TypeMsg.POINTS, getMove());
						sendMsg.setEnemyPoints(points);
						host.sendMessage(sendMsg);
					}
				}
				
				break;
				
			case POINTS:
				ENEMY_BOARD.setFieldsTo(recMsg.getEnemyPoints());
				refreshPanels();
				setMove(recMsg.getMove());
				break;
				
			case END:
				endGame(true, false);
				break;
				
			default:
				LOGGER.warning("Incorrect message type");
				break;
		
		}
		
		setTurnText();
		
	}
	
	public void clientRecMsg() {
		Message recMsg = client.getMessage();
		
		switch (recMsg.getType()) {
			case ATTACK:
				HashMap<Point, FieldType> points = MY_BOARD.checkIsShipHit(recMsg.getPoint());
				if (points != null) {
					if (isShipHitted(points)) {
						setMove(Move.HOST);
					} else {
						setMove(Move.CLIENT);
					}
					
					refreshPanels();
					
					if (checkLose(false)) {
						Message endMsg = new Message(TypeMsg.END, getMove());
						client.sendMessage(endMsg);
						
						endGame(false, false);
						
					} else {
						Message sendMsg = new Message(recMsg.getId() + 1, null, TypeMsg.POINTS, getMove());
						sendMsg.setEnemyPoints(points);
						client.sendMessage(sendMsg);
					}
				}
				
				break;
				
			case POINTS:
				ENEMY_BOARD.setFieldsTo(recMsg.getEnemyPoints());
				setMove(recMsg.getMove());
				refreshPanels();
				break;
				
			case WELCOME:
				setMove(recMsg.getMove());
				break;
				
			case END:
				endGame(true, false);
				break;
				
			default:
				LOGGER.warning("Incorrect message type");
				break;
		
		}
		
		setTurnText();
	}
	
	public boolean isYourMove() {
		if (Main.gameType == GameType.HOST && getMove() == Move.HOST) {
			return true;
		}
		if (Main.gameType == GameType.CLIENT && getMove() == Move.CLIENT) {
			return true;
		}
		
		if (Main.gameType == GameType.USER_VS_COMPUTER && getMove() == Move.USER) {
			return true;
		}
		
		return false;
	}
	
	private boolean isShipHitted(HashMap<Point, FieldType> points) {
		for (Map.Entry<Point, FieldType> entry : points.entrySet()) {
			if (entry.getValue() == FieldType.DAMAGED ||
					entry.getValue() == FieldType.DESTROYED) {
				return true;
			}
		}
		return false;
	}
	
	private void setTurnText() {
		if (isYourMove()) {
			movementLabel.setText("YOUR TURN");
		} else {
			movementLabel.setText("ENEMY TURN");
		}
	}
	
	private boolean checkLose(boolean bot) {
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
	
	private boolean checkEnemyLose(Board b) {
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
	
	public void endGame(boolean win, boolean withoutWin) {
		
		if (Main.gameType == GameType.CLIENT) {
			client.setGameEnd(true);
			client.closeConn();
		}
		if (Main.gameType == GameType.HOST) {
			host.setEndGame(true);
			host.close();
		}
		
		if (withoutWin) {
			Main.endGame();
		}
		
		if (win) {
			JOptionPane.showMessageDialog(null, "You win!");
		} else {
			JOptionPane.showMessageDialog(null, "You lose!");
		}
		
		Main.endGame();
		
	}
	
	private void initBot() {
		
		enemy = new Bot(MY_BOARD);
		ENEMY_BOARD = enemy.getMyBoard();
		lossPlayer();
		botTurn();
	}
	
	public void botTurn() {
		BotTurn botTurn = new BotTurn();
		Thread t = new Thread(botTurn);
		t.start();
	}
	
	public void userMove(Point point) {
		HashMap<Point, FieldType> points = ENEMY_BOARD.checkIsShipHit(point);
		if (points != null) {
			if (isShipHitted(points)) {
				setMove(Move.USER);
			} else {
				setMove(Move.BOT);
			}
			
			refreshPanels();
			if (checkLose(false)) {
				endGame(false, false);
			}
			else if (checkEnemyLose(ENEMY_BOARD)) {
				endGame(true, false);
			} else if (getMove() == Move.BOT) {
				botTurn();
			}
		}
		
		setTurnText();
	}
	
	class BotTurn implements Runnable {
		
		private int sleepTime = 1000;
		
		public BotTurn() {
			sleepTime = new Random().nextInt(300) + 300;
		}
		
		@Override
		public void run() {
				while (!isYourMove()) {
				try {
					Thread.sleep(sleepTime);
					LOGGER.info("Bot thread sleep for " + sleepTime + " ms");
				} catch (InterruptedException e) {
					LOGGER.warning("Problem with bot thread interrupting: " + e.toString());
				}
				Point toAttack = enemy.nextTurn();
				HashMap<Point, FieldType> points = MY_BOARD.checkIsShipHit(toAttack);
				refreshPanels();
				if (checkLose(true)) {
					endGame(true, false);
				}
				else if (checkEnemyLose(MY_BOARD)) {
					endGame(false, false);
				}
				if (points == null) {
					break;
				}
				if (isShipHitted(points)) {
					setMove(Move.BOT);
				} else {
					setMove(Move.USER);
				}			
			}
	
			setTurnText();
		}
		
	}
	
	public void setMyBoard(Board b) {
		MY_BOARD = b;
		if (Main.gameType == GameType.USER_VS_COMPUTER) {
			enemy.setEnemmyBoard(MY_BOARD);
		}
		refreshPanels();
	}
	
	public Move getMove() {
		synchronized (moveLock) {
			return move;
		}
	}
	
	public void setMove(Move aMove) {
		synchronized (moveLock) {
			move = aMove;
		}
	}
	
	
}