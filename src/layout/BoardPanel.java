package layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import communiaction.Message;
import communiaction.Message.TypeMsg;
import components.FieldLabel;
import constants.Constants;
import game.Game;
import game.Main;
import game.Main.GameType;
import model.Board;
import model.FieldType;
import model.Point;

public class BoardPanel extends JPanel {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private final static Cursor DEFAULT_CUROSR = new Cursor(Cursor.DEFAULT_CURSOR);
	private final static Cursor HAND_CUROSR = new Cursor(Cursor.HAND_CURSOR);

	private static final long serialVersionUID = 1L;
	
	private LinkedList<FieldLabel> fields;
	
	private JPanel fieldPanel;
	private JLabel boardNameLabel;
	
	private boolean enemy = false;

	public BoardPanel(boolean aEnemy, String boardName) {
		super(new BorderLayout());
		fieldPanel = new JPanel(new GridLayout(11, 11));
		
		fields = new LinkedList<>();
		enemy = aEnemy;
		addLabels();
		add(fieldPanel, BorderLayout.CENTER);
		
		boardNameLabel = new JLabel(boardName);
		boardNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(boardNameLabel, BorderLayout.NORTH);
		if (aEnemy) {
			boardNameLabel.setForeground(Color.RED);
		} else {
			boardNameLabel.setForeground(new Color(0, 131, 2));
		}
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
	
	public void setEnemy(boolean aEnemy) {
		enemy = aEnemy;
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
	
	public FieldLabel findLabelWithCords(int xp, int yp) {
		FieldLabel label = new FieldLabel(xp, yp);
		for (FieldLabel fl : fields) {
			if (label.equals(fl)) {
				return fl;
			}
		}
		return null;
	}
	
	public void handleClickEvent(FieldLabel clicked) {

		Point clickedPoint = new Point(clicked.getxPos(), clicked.getyPos());	
		if (Main.gameType == GameType.HOST || Main.gameType == GameType.CLIENT) {
			Message msg = new Message(1, clickedPoint, TypeMsg.ATTACK, Game.move);
			Game.move(msg);
		} else if (Main.gameType == GameType.USER_VS_COMPUTER) {
			Game.userMove(clickedPoint);
		}
		
		setCursor(DEFAULT_CUROSR);
	}

	class FieldClick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
				
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (enemy && Game.isYourMove()) {
				FieldLabel label = (FieldLabel) e.getComponent();
				if (label.getFieldType() == FieldType.EMPTY ||
						label.getFieldType() == FieldType.SHIP) {
					setCursor(HAND_CUROSR);
				}
			}
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
			FieldLabel label = (FieldLabel) e.getComponent();
			if (Game.isYourMove() && enemy) {
				handleClickEvent(label);
			}
			logClick(label);
		}
	}
	
	private void logClick(FieldLabel label) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("User click on ");
		buffer.append(label.getxPos());
		buffer.append("x");
		buffer.append(label.getyPos());
		if (enemy) {
			buffer.append(" enemy board");
		} else {
			buffer.append(" own board");
		}
		LOGGER.info(buffer.toString());
	}
}
