package components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.FieldType;

public class FieldLabel extends JLabel {

	private static final long serialVersionUID = 1L;
	
	private static final Dimension DEFAULT_DIM = new Dimension(50, 50);
	
	private int xPos;
	private int yPos;
	
	private FieldType fieldType = FieldType.EMPTY;

	public FieldLabel() {
		super("", SwingConstants.CENTER);
		defaultSettings();
	}
	
	public FieldLabel(String txt) {
		super(txt, SwingConstants.CENTER);
		defaultSettings();
	}
	
	public FieldLabel(int aX, int aY) {
		super("", SwingConstants.CENTER);
		xPos = aX;
		yPos = aY;
		defaultSettings();
	}
	
	public FieldLabel(String txt, int aX, int aY) {
		super(txt, SwingConstants.CENTER);
		xPos = aX;
		yPos = aY;
		defaultSettings();
	}
	
	@Override
	public String toString() {
		return xPos + "x" + yPos;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FieldLabel) {
			FieldLabel label = (FieldLabel) obj;
			
			if (label.getX() == xPos && label.getY() == yPos) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	private void defaultSettings() {
		setSize(DEFAULT_DIM);
		setPreferredSize(DEFAULT_DIM);
		setMinimumSize(DEFAULT_DIM);
		setMaximumSize(DEFAULT_DIM);
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
	}

	public FieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}
}
