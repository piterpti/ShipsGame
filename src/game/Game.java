package game;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import communiaction.Client;
import communiaction.Host;
import layout.GamePanel;
import model.Board;
import model.Point;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static Board MY_BOARD;
	public static Board ENEMY_BOARD;
	
	private static GamePanel myPanel;
	private static GamePanel enemyPanel;
	
	private static Host host;
	private static Client client;
	
	private JLabel movementLabel = new JLabel("Ruch");
	
	public Game() {
		initComponents();
		
		
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
	
	private void initHost() {
		host = new Host(Main.PORT);
		host.start();
		
		
	}
	
	public static void move(Point point) {
		switch (Main.gameType) {
		case HOST:
			host.sendRequest(point.toString());
			break;
		case CLIENT:
			client.sendRequest(point.toString());
			break;
		default:
			break;
		}
	}
	
	private void initClient() {
		client = new Client();
		client.start();
		
		
		
		
		
	}
	
}