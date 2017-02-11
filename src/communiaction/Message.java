package communiaction;

import java.io.Serializable;

import model.Point;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum Type {
		WELCOME, ATTACK, END
	}
	
	private Point point;
	private String text;
	private Type type;
	private int id;
	
	public Message(Type aType) {
		type = aType;
	}
	
	public Message(int id,Point point, String text, Type type) {
		super();
		this.point = point;
		this.text = text;
		this.type = type;
		this.id = id;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		StringBuffer bfr = new StringBuffer();
		bfr.append(id + ";");
		bfr.append("Type: ");
		bfr.append(type);
		bfr.append(";");
		if (text != null) {
			bfr.append(text);
			bfr.append(";");
		}
		if (point != null) {
			bfr.append(point.toString());
		}
		return bfr.toString();
	}
}
