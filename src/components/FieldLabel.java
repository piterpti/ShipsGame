package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import game.Main;
import model.FieldId;

public class FieldLabel extends JLabel implements MouseListener{

	private static final long serialVersionUID = 1L;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private String board;
	
	static {
		try {
			logger.MyLogger.setup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private FieldId fieldId = null;
	
	public FieldLabel(String aBoard) {
		super();
		this.addMouseListener(this);
		board = aBoard;
	}
	
	public FieldLabel(String txt, String aBoard) {
		super(txt);
		this.addMouseListener(this);
		setFieldId(txt);
		board = aBoard;
	}
	
	public FieldLabel(String txt, int aligment, String aBoard) {
		super(txt, aligment);
		this.addMouseListener(this);
		setFieldId(txt);
		board = aBoard;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// propably frame field so we exit
		if (fieldId == null) {
			return;
		}
		
		// call click event on board..
		Main.boardEvent(board, fieldId);
		
		LOGGER.info("User click on " + fieldId.toString() + " on " + board);
		
	}
	
	private void setFieldId(String txt) {
		if (txt == null) {
			LOGGER.warning("Null text value for FieldLabel");
			return;
		}
		if (txt.indexOf(':') < 0) {
			LOGGER.info("Field label wrong text: " + txt);
			removeMouseListener(this);
			setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			return;
		}
		
		String[] fieldData = txt.split(":");
		
		int yValue = -1;
		char xValue = ' ';
		try {
			yValue = Integer.valueOf(fieldData[0]);
			xValue = fieldData[1].charAt(0);
		} catch (Exception e) {
			LOGGER.warning("Wrong data for FieldID: " + txt);
			return;
		}
		
		fieldId = new FieldId();
		fieldId.setX(xValue);
		fieldId.setY(yValue);
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
	}
	
	public FieldId getFieldId() {
		return fieldId;
	}
	
}
