package communiaction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import game.Game.Move;
import model.Board;
import model.FieldType;
import model.Point;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public enum TypeMsg {
		WELCOME, ATTACK, END, POINTS
	}
	
	private Point point;
	private TypeMsg type;
	private int id;
	private Board board;
	
	private Move move;
	
	private HashMap<Point, FieldType> enemyPoints = new HashMap<>();
	
	public Message(TypeMsg aType, Move aMove) {
		type = aType;
		move = aMove;
	}
	
	public Message(int id,Point point, TypeMsg type, Move aMove) {
		super();
		this.point = point;
		this.type = type;
		this.id = id;
		move = aMove;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
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
		
		if (point != null) {
			bfr.append(point.toString());
		}
		if (enemyPoints != null && !enemyPoints.isEmpty()) {
			bfr.append("Points:");
			for (Map.Entry<Point, FieldType> entry : enemyPoints.entrySet()) {
				bfr.append(entry.getKey().toString());
				bfr.append("-");
				bfr.append(entry.getValue().toString());
				bfr.append(",");
			}
		}
		
		return bfr.toString();
	}

	public HashMap<Point, FieldType> getEnemyPoints() {
		return enemyPoints;
	}

	public void setEnemyPoints(HashMap<Point, FieldType> enemyPoints) {
		this.enemyPoints = enemyPoints;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}	
}
