package layout; 

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JFrame;

import components.MenuButton;
import game.Main;

public class MainMenu extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private MenuButton newGameBotBtn;
	private MenuButton newGameNetworkBtn;
	private MenuButton exitBtn;
	
	
	public MainMenu() {
		
		setTitle("Main menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 1));
		
		newGameBotBtn = new MenuButton("New game");
		newGameNetworkBtn = new MenuButton("Network game");
		exitBtn = new MenuButton("Exit");
		
		addListeners();
		
		add(newGameBotBtn);
		add(newGameNetworkBtn);
		add(exitBtn);
		
		pack();
	}
	
	private void addListeners() {
		
		newGameBotBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("Starting game with computer");
				startGame();
			}
		});
		
		newGameNetworkBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LOGGER.info("Starting network game");
				startNetworkGame();
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				LOGGER.info("Exit application");
				System.exit(0);
			}
		});
	}
	
	private static void startNetworkGame() {
		Main.startNetworkGame();
	}

	private static void startGame() {
		Main.setShips();
	}
}
 