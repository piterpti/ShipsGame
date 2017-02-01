package bot;

import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Logger;

import game.Main;
import model.Board;
import model.FieldType;
import model.Point;
import tools.ShipGenerator;

public class Bot {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static Board myBoard;
	public static Board enemmyBoard;
	
	public Bot() {
		myBoard = new Board();
		ShipGenerator.generateShips(myBoard);
		enemmyBoard = Main.myBoard;
	}
	
	public void nextTurn() {
		
		LOGGER.info("BOT TURN..");
		shot();
		Main.userMove = true;
		
	}

	public static Board getMyBoard() {
		return myBoard;
	}

	public static void setMyBoard(Board myBoard) {
		Bot.myBoard = myBoard;
	}

	public static Board getEnemmyBoard() {
		return enemmyBoard;
	}

	public static void setEnemmyBoard(Board enemmyBoard) {
		Bot.enemmyBoard = enemmyBoard;
	}
	
	private void shot() {
		
		LinkedList<Point> damagedPoints = getDamagedShips();
		LinkedList<Point> pointsToAttack = new LinkedList<>();
		
		if (damagedPoints.size() > 0) {
			pointsToAttack.addAll(getAllAvialbleShots(enemmyBoard, damagedPoints));
		} else {
			pointsToAttack.addAll(getAllAvialbleShots());
		}
		
		Random random = new Random();
		
		int shipNumber = random.nextInt(pointsToAttack.size());
		int counter = 0;
		
		Point toAttack = null;
		for (Point p : pointsToAttack) {
			if (counter == shipNumber) {
				toAttack = p;
				break;
			}
			counter++;
		}
		
		enemmyBoard.checkIsShipHit(toAttack);
	}
	
	private LinkedList<Point> getDamagedShips() {
		LinkedList<Point> damged = new LinkedList<>();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				if (enemmyBoard.getFields()[x][y] == FieldType.DAMAGED) {
					damged.add(new Point(x, y));
				}
			}
		}
		return damged;
	}
	
	private LinkedList<Point> getAllAvialbleShots() {
		LinkedList<Point> result = new LinkedList<>();
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				FieldType type = enemmyBoard.getFields()[x][y];
				if (type == FieldType.EMPTY || 
						type == FieldType.SHIP) {
					result.add(new Point(x, y));
				}
			}
		}
		return result;
	}
	
	private LinkedList<Point> getAllAvialbleShots(Board b, LinkedList<Point> points) {
		LinkedList<Point> result = new LinkedList<>();
		for (Point p : points) {
			for (Point pp : p.getNeigbourPointsWithoutSlant()) {
				FieldType type = b.getFields()[pp.getX()][pp.getY()];
				if (type == FieldType.EMPTY || 
						type == FieldType.SHIP) {
					result.add(new Point(pp.getX(), pp.getY()));
				}
			}
		}
		return result;
	}
	
	

}
