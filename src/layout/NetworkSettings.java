package layout;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class NetworkSettings extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JLabel portLabel;
	private JLabel nickLabel;
	private JTextField portTxtField;
	private JTextField nickTxtField;
	
	
	public NetworkSettings() {
		setTitle("Network configuration");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new GridLayout(3, 2));
		
		portLabel = new JLabel("Port:");
		nickLabel = new JLabel("Nickname:");
		portTxtField = new JTextField(20);
		nickTxtField = new JTextField(20);
		
		add(portLabel);
		add(portTxtField);
		
		add(nickLabel);
		add(nickTxtField);
		
		pack();
	}
	
	
}
