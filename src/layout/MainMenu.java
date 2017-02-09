package layout; 

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import game.GameFrame;
import game.Main;

public class MainMenu extends JFrame {
	
	private final static Dimension BTN_SIZE = new Dimension(350, 100);
	
	private JButton newGameBotBtn;
	private JButton newGameNetworkBtn;
	private JButton exitBtn;
	
	
	public MainMenu() {
		
		setTitle("Main menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 1));
		
		newGameBotBtn = new JButton("New game");
		newGameNetworkBtn = new JButton("Network game");
		exitBtn = new JButton("Exit");
		
		btnConfig(newGameBotBtn);
		btnConfig(newGameNetworkBtn);
		btnConfig(exitBtn);
		
		addListeners();
		
		add(newGameBotBtn);
		add(newGameNetworkBtn);
		add(exitBtn);
		
		pack();
	}
	
	private void btnConfig(JButton btn) {
		btn.setMaximumSize(BTN_SIZE);
		btn.setMinimumSize(BTN_SIZE);
		btn.setPreferredSize(BTN_SIZE);
		btn.setSize(BTN_SIZE);
		btn.setFont(new Font(btn.getFont().getFontName(), btn.getFont().getStyle(), 24));
		
		
	}


	private void addListeners() {
		
		newGameBotBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}
	
	private static void startGame() {
		Main.startGame();
	}

}
 