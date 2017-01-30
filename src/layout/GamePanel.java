package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import components.FieldLabel;
import game.Board;
import model.FieldId;
import model.ShipType;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private final Color boardFrameColor = new Color(150, 150, 150);
	private final Color DEFAULT_FIELD_COLOR = new Color(200, 200, 200);
	private final Dimension FIELD_SIZE = new Dimension(50, 50);
	
	private BoardGui board;
	
	private LinkedList<FieldLabel> fields = new LinkedList<>();
	
	public GamePanel(String aBoard) {
		super();
		this.board = new BoardGui();
		this.setLayout(board);
		addButtons(aBoard);
	}
	
	private void addButtons(String board) {
		FieldLabel emptyLabel = new FieldLabel(board);
		add(emptyLabel);
		fields.add(emptyLabel);
		
		char letter = 'A';
		for (int i = 1; i < 11; i++) {
			FieldLabel letterLabel = new FieldLabel(letter + "", SwingConstants.CENTER, board);
			setDefaultSettings(letterLabel, boardFrameColor);
			add(letterLabel);
			fields.add(letterLabel);
			letter =(char)((int) letter + 1);
		}
		
		
		for (int i = 1; i < 11; i++) {
			letter = 'A';
			for (int x = 0; x < 11; x++) {
				if (x == 0) {
					FieldLabel numberLabel = new FieldLabel(i + "", SwingConstants.CENTER, board);
					setDefaultSettings(numberLabel, boardFrameColor);
					add(numberLabel);
					fields.add(numberLabel);
				} else {
					FieldLabel label = new FieldLabel(i + ":" + letter, SwingConstants.CENTER, board);
					setDefaultSettings(label, DEFAULT_FIELD_COLOR);
					add(label);
					fields.add(label);
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
	
	public void refreshPanel(Board board) {
		ShipType[][] ships = board.getFields();
		if (ships == null) {
			LOGGER.info("variable fields is null - cannot refresh");
			return;
		}
		for (int x = 0; x < ships[0].length; x++) {
			for (int y = 0; y < ships.length; y++) {
				FieldId id = new FieldId();
				char xChar =(char)((int)'A' + x);
				id.setX(xChar);
				id.setY(y + 1);	
				
				for (FieldLabel label : fields) {
					FieldId labelId = label.getFieldId();
					if (labelId == null) {
						continue;
					}
					
					if (labelId.equals(id)) {
						switch (ships[y][x]) {
							case DAMAGED:
								label.setText("DMG");
								break;
							case DESTROYED:
								label.setText("DES");
								break;
							case NO_ONE:
								label.setText("");
								break;
							case ONE_MAST:
								label.setText("1");
								break;
							case TWO_MAST:
								label.setText("2");
								break;
							case THREE_MAST:
								label.setText("3");
								break;
							case FOUR_MAST:
								label.setText("4");
								break;
						}
					}
				}
			}
		}
	}
	
}
