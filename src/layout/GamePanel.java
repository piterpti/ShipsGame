package layout;

import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.swing.JPanel;

import communiaction.Message;
import communiaction.Message.Type;
import components.FieldLabel;
import game.Game;
import game.Game.Move;
import model.Board;
import model.FieldType;
import model.Point;

public class GamePanel extends JPanel {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private final static Cursor DEFAULT_CUROSR = new Cursor(Cursor.DEFAULT_CURSOR);
	private final static Cursor HAND_CUROSR = new Cursor(Cursor.HAND_CURSOR);

	private static final long serialVersionUID = 1L;
	
	private LinkedList<FieldLabel> fields;
	
	private boolean enemy = false;

	public GamePanel(boolean aEnemy) {
		super(new GridLayout(11, 11));
		fields = new LinkedList<>();
		enemy = aEnemy;
		addLabels();
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
		add(label);
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
					fl.setText("Ship");
					break;
				case DAMAGED:
					fl.setText("Damaged");
					break;
				case DESTROYED:
					fl.setText("Destroyed");
					break;
				case EMPTY:
					fl.setText("");
					break;
				case SHOOTED:
					fl.setText(".");
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
					fl.setText("dmg");
					break;
				case DESTROYED:
					fl.setText("dstr");
					break;
				case SHOOTED:
					fl.setText(".");
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
//		if (enemy) {
//			Point clickedPoint = new Point(clicked.getxPos(), clicked.getyPos());
//			GameFrame.enemyBoard.checkIsShipHit(clickedPoint);
//			setCursor(DEFAULT_CUROSR);
//			GameFrame.checkWin();
//			GameFrame.bot.nextTurn();
//			GameFrame.refreshPanels();
//		}
		
		Point clickedPoint = new Point(clicked.getxPos(), clicked.getyPos());
		
		Message msg = new Message(1, clickedPoint, "this is text..", Type.ATTACK);
		
		Game.move(msg);
		
	}
	
	public void enemyMove(String attack) {
		
		
		
		Game.MY_BOARD.checkIsShipHit(null);
		Game.refreshPanels();
		
		
	}
	
	class FieldClick implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
				
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (enemy && Game.move == Move.PLAYER) {
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
			handleClickEvent(label);
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
