package layout;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import components.FieldLabel;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final Color boardFrameColor = new Color(100, 130, 200);
	private final Color FIELD_COLOR = new Color(255, 0, 0);
	private final Dimension FIELD_SIZE = new Dimension(50, 50);
	
	private Board board;
	
	public GamePanel() {
		super();
		this.board = new Board();
		this.setLayout(board);
		addButtons();
	}
	
	private void addButtons() {
		FieldLabel emptyLabel = new FieldLabel("");
		add(emptyLabel);
		
		char letter = 'A';
		for (int i = 1; i < 11; i++) {
			FieldLabel letterLabel = new FieldLabel(letter + "", SwingConstants.CENTER);
			setDefaultSettings(letterLabel, boardFrameColor);
			add(letterLabel);
			letter =(char)((int) letter + 1);
		}
		
		
		for (int i = 1; i < 11; i++) {
			letter = 'A';
			for (int x = 0; x < 11; x++) {
				if (x == 0) {
					FieldLabel numberLabel = new FieldLabel(i + "", SwingConstants.CENTER);
					setDefaultSettings(numberLabel, boardFrameColor);
					add(numberLabel);
				} else {
					FieldLabel label = new FieldLabel(i + ":" + letter, SwingConstants.CENTER);
					setDefaultSettings(label, FIELD_COLOR);
					add(label);
					letter =(char)((int) letter + 1);
				}
			}
		}
		
	}
	
	private void setDefaultSettings(JLabel cmp, Color c) {
		cmp.setBackground(c);
		cmp.setSize(FIELD_SIZE);
		cmp.setPreferredSize(FIELD_SIZE);
		cmp.setMinimumSize(FIELD_SIZE);
		cmp.setMaximumSize(FIELD_SIZE);
		cmp.setOpaque(true);
	}
	
}
