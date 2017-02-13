package layout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import constants.Constants;
import game.Main;

public class WaitFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JButton exitBtn = new JButton("Cancel");
	private JLabel textLabel;
	
	public WaitFrame(String text) {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Wait");
		setLayout(new GridLayout(2, 1));
		textLabel = new JLabel();
		textLabel.setText(text);
		setLabelSettings(textLabel);
		addListener();
		
		add(textLabel);
		add(exitBtn);
		
		pack();
	}
	
	private void addListener() {
		exitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.endGame();
			}
		});
	}

	private void setLabelSettings(JLabel label) {
		Dimension dim = new Dimension(200, 100);
		label.setSize(dim);
		label.setPreferredSize(dim);
		label.setMinimumSize(dim);
		label.setMaximumSize(dim);
		label.setFont(Constants.FONT);
	}
	
	public void setLabelText(String newText) {
		if (newText != null) {
			textLabel.setText(newText);
		}
	}
	
	

}
