package layout;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import communiaction.ConnectionConfig;
import components.MenuButton;
import constants.Constants;
import game.Main;
import game.Main.GameType;

public class NetworkSettings extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
		private JLabel portLabel;
	private JLabel nickLabel;
	private JLabel errorLabel;
	private JTextField portTxtField;
	private JTextField nickTxtField;
	private MenuButton nextBtn;
	
	
	public NetworkSettings() {
		setTitle("Network configuration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(3, 2));
		
		portLabel = new JLabel("Port:");
		nickLabel = new JLabel("Nickname:");
		errorLabel = new JLabel("");
		nextBtn = new MenuButton("Confirm");
		portTxtField = new JTextField(20);
		nickTxtField = new JTextField(20);
		
		portTxtField.setText(ConnectionConfig.PORT + "");
		
		portTxtField.setFont(Constants.FONT);
		nickTxtField.setFont(Constants.FONT);
		portLabel.setFont(Constants.FONT);
		nickLabel.setFont(Constants.FONT);
		nextBtn.setFont(Constants.FONT);
		errorLabel.setForeground(Color.RED);
		
		portLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nickLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		addListener();
		
		add(portLabel);
		add(portTxtField);
		
		add(nickLabel);
		add(nickTxtField);
		
		add(errorLabel);
		add(nextBtn);
		
		pack();
	}
	
	private void addListener() {
		nextBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean stop = false;
				String tmpTxt = portTxtField.getText();
				if (tmpTxt == null || tmpTxt.isEmpty()) {
					errorLabel.setText("Enter Port!");
					stop = true;
				}
				
				tmpTxt = nickTxtField.getText();
				if (tmpTxt == null || tmpTxt.isEmpty() && !stop) {
					errorLabel.setText("Enter nickname!");
					stop = true;
				}
				
				if (!stop) {
					errorLabel.setText("");
					
					Main.gameType = GameType.HOST;
					Main.setShips();
					dispose();
				}
			}
		});
	}
	
	
}
