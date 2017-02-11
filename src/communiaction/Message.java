package communiaction;

import java.io.Serializable;

import model.Board;
import model.Point;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum TypeMsg {
		WELCOME, ATTACK, END, BOARD
	}
	
	private Point point;
	private String text;
	private TypeMsg type;
	private int id;
	private Board board;
	
	public Message(TypeMsg aType) {
		type = aType;
	}
	
	public Message(int id,Point point, String text, TypeMsg type) {
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

	public TypeMsg getType() {
		return type;
	}

	public void setType(TypeMsg type) {
		this.type = type;
	}
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
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
		if (board != null) {
			bfr.append("BOARD");
		}
		return bfr.toString();
	}
}
