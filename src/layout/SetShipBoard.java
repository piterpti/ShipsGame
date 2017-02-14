package layout;

import static constants.Constants.LOGGER;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import components.FieldLabel;
import constants.Constants;
import model.Board;
import model.FieldType;

public class SetShipBoard extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final static Cursor DEFAULT_CUROSR = new Cursor(Cursor.DEFAULT_CURSOR);
//	private final static Cursor HAND_CUROSR = new Cursor(Cursor.HAND_CURSOR);
	
	private JPanel fieldPanel;
	private LinkedList<FieldLabel> fields;
	private JLabel boardNameLabel;
	
	
	public SetShipBoard() {
		super(new BorderLayout());
		fieldPanel = new JPanel(new GridLayout(11, 11));
		
		fields = new LinkedList<>();
		addLabels();
		add(fieldPanel, BorderLayout.CENTER);
		
		boardNameLabel = new JLabel("Set your ships!");
		boardNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		add(boardNameLabel, BorderLayout.NORTH);
	}
	
	
	
	private void addLabels() {
		FieldLabel emptyLabel = new FieldLabel();
		emptyLabel.setFieldType(FieldType.FRAME);
		addLabel(emptyLabel);
		
		char letter = 'A';
		for (int x = 0; x < 10; x++) {
			FieldLabel letterLabel = new FieldLabel(letter + "");
			addLabel(letterLabel);
			letterLabel.setFieldType(FieldType.FRAME);
			letter++;
		}
		
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 11; x++) {
				FieldLabel label = null;
				if (x == 0) {
					label = new FieldLabel(y + 1 + "");
					label.setFieldType(FieldType.FRAME);
				} else {
					label = new FieldLabel("", x-1, y);
					label.addMouseListener(new FieldClick());
				}
				addLabel(label);
			}
		}
		LOGGER.info("Board created");
	}
	
	private void addLabel(FieldLabel label) {
		fieldPanel.add(label);
		fields.add(label);
	}
	
	public void refresh(Board board) {
		FieldType[][] fieldsBoard = board.getFields();
		
		for (FieldLabel fl : fields) {
			if (fl.getFieldType() == FieldType.FRAME) {
				continue;
			}
			fl.setFieldType(fieldsBoard[fl.getxPos()][fl.getyPos()]);
			if (board.isMyBoard()) {
				switch(fl.getFieldType()) {
				case SHIP:
					fl.setLook(Constants.SHIP_COLOR, "");
					break;
				case DAMAGED:
					fl.setLook(Constants.DAMAGED_COLOR, "");
					break;
				case DESTROYED:
					fl.setLook(Constants.DESTROYED_COLOR, "X");
					break;
				case EMPTY:
					fl.setLook(Constants.WATER_COLOR, "");
					break;
				case SHOOTED:
					fl.setLook(Constants.SHOOTED_COLOR, ".");
					break;
				case FRAME:
					// nothing do to
					break;
				default:
					LOGGER.info("Unkonw action");
					break;
				}
				
			} else {
				switch (fl.getFieldType()) {
				case DAMAGED:
					fl.setLook(Constants.DAMAGED_COLOR, "");
					break;
				case DESTROYED:
					fl.setLook(Constants.DESTROYED_COLOR, "X");
					break;
				case SHOOTED:
					fl.setLook(Constants.SHOOTED_COLOR, ".");
					break;
				case FRAME:
					break;
				default:
					break;
				}
			}
		}
	}
	
	class FieldClick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
				
		}

		@Override
		public void mouseEntered(MouseEvent e) {
	
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setCursor(DEFAULT_CUROSR);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}
	}
	
}
