package bot;

import static constants.Constants.LOGGER;

import java.util.LinkedList;
import java.util.Random;

import model.Board;
import model.FieldType;
import model.Point;
import model.Ship;

public class Bot extends Enemy {

	public Bot() {
		super();
	}
	
	public Point nextTurn() {
		return shot();
	}

	public Board getMyBoard() {
		return myBoard;
	}

	public void setMyBoard(Board myBoard) {
		Bot.myBoard = myBoard;
	}

	public Board getEnemmyBoard() {
		return enemmyBoard;
	}

	public static void setEnemmyBoard(Board enemmyBoard) {
		Bot.enemmyBoard = enemmyBoard;
	}
	
	private Point shot() {
		
		Point toAttack = null;
		LinkedList<Point> damagedPoints = getDamagedShips();
		LinkedList<Point> pointsToAttack = new LinkedList<>();
		
		Ship dmgShip = getDamagedShip();
		
		if (dmgShip != null) {
			pointsToAttack.addAll(dmgShip.getShipSuspect(enemmyBoard));
		}
		else if (damagedPoints.size() > 0) {
			pointsToAttack.addAll(getAllAvialbleShots(enemmyBoard, damagedPoints));
		} else {
			pointsToAttack.addAll(getAllAvialbleShots());
		}
		
		if (pointsToAttack.size() > 0) {
			Random random = new Random();
			
			int shipNumber = random.nextInt(pointsToAttack.size());
			int counter = 0;
			
			
			for (Point p : pointsToAttack) {
				if (counter == shipNumber) {
					toAttack = p;
					break;
				}
				counter++;
			}
			
			LOGGER.info("Computer attak point " + toAttack.toString());
			return toAttack;
			
		} else {
			LOGGER.warning("Error toAttack point is null");
		}
		
		return null;
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
	
	private Ship getDamagedShip() {
		LinkedList<Ship> tmpShips = enemmyBoard.getShips();
		
		for (Ship s : tmpShips) {
			if (s.twoHits()) {
				return s;
			}
		}
		return null;
	}
	
	

}
