package layout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import components.MenuButton;
import game.Game;
import game.Main;
import game.Main.GameType;

public class NetworkType extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private MenuButton becomeHostBtn;
	private MenuButton joinBtn;
	private MenuButton goBackBtn;
	
	public NetworkType() {
		setTitle("Network settings");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(3, 1));
		
		becomeHostBtn = new MenuButton("Host");
		joinBtn = new MenuButton("Join to game");
		goBackBtn = new MenuButton("Back to menu");
		
		addListeners();
		
		add(becomeHostBtn);
		add(joinBtn);
		add(goBackBtn);
		
		pack();
	}

	private void addListeners() {
		
		joinBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.gameType = GameType.CLIENT;
				Main.gameFrame = new Game();
				Main.gameFrame.setVisible(true);
				dispose();
			}
		});
		
		becomeHostBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new NetworkSettings().setVisible(true);
				dispose();
			}
		});
		
		goBackBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.backToMenu();
			}
		});
	}
	
	
}
