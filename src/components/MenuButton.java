package components;

import static constants.Constants.BTN_SIZE;

import java.awt.Font;

import javax.swing.JButton;

public class MenuButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public MenuButton(String label) {
		super(label);
		
		setMaximumSize(BTN_SIZE);
		setMinimumSize(BTN_SIZE);
		setPreferredSize(BTN_SIZE);
		setSize(BTN_SIZE);
		setFont(new Font(getFont().getFontName(), getFont().getStyle(), 24));
		
		System.out.println(getFont().getFontName() + " " + getFont().getStyle());
	}
	
}
