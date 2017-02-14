package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Logger;

public class Board implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private FieldType[][] fields;
	private LinkedList<Ship> ships;
	
	boolean myBoard = false;
	
	public Board(LinkedList<Ship> aShips) {
		fields = new FieldType[10][10];
		ships = aShips;
		clear();
		putShips();
	}
	
	public Board() {
		fields = new FieldType[10][10];
		clear();
	}
	
	public void clear() {
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				fields[x][y] = FieldType.EMPTY;
			}
		}
		if (ships != null) {
			ships.clear();
		}
	}
	
	private void putShips() {
		for (Ship s : ships) {
			for (Point p : s.getShipFields()) {
				fields[p.getX()][p.getY()] = FieldType.SHIP;
			}
		}
	}

	public FieldType[][] getFields() {
		return fields;
	}

	public void setFields(FieldType[][] fields) {
		this.fields = fields;
	}

	public LinkedList<Ship> getShips() {
		return ships;
	}

	public void setShips(LinkedList<Ship> ships) {
		clear();
		this.ships = ships;
		putShips();
	}
	
	public HashMap<Point, FieldType> checkIsShipHit(Point point) {
		FieldType type = fields[point.getX()][point.getY()];
		
		HashMap<Point, FieldType> resultPoints = new HashMap<>();
		
		if (type == FieldType.SHOOTED || type == FieldType.DAMAGED
				|| type == FieldType.DESTROYED) {
			return null;
		}
		
		else if (type == FieldType.EMPTY) {
			
			fields[point.getX()][point.getY()] = FieldType.SHOOTED;
			resultPoints.put(point, FieldType.SHOOTED);
			
		} else if (type == FieldType.SHIP) {
			
			for (Ship s : ships) {
				boolean shipDestroyed = false;
				for (Point p : s.getShipFields()) {
					if (p.equals(point)) {
						p.setDamged(true);
						shipDestroyed = s.liveMinusAndItsDead();
						fields[p.getX()][p.getY()] = FieldType.DAMAGED;
						resultPoints.put(p, FieldType.DAMAGED);
						LOGGER.info("Ship hit on field: " + p.toString());
					}
				}
				
				if (shipDestroyed) {
					for (Point p : s.getShipFields()) {
						fields[p.getX()][p.getY()] = FieldType.DESTROYED;
						resultPoints.put(p, FieldType.DESTROYED);
						LOGGER.info("Ship destroyed:" + s.toString());
						LinkedList<Point> neihgbours = s.getNeighbourPoints();
						for (Point shooted : neihgbours) {
							FieldType neighbour = fields[shooted.getX()][shooted.getY()];
							if (neighbour == FieldType.EMPTY) {
								fields[shooted.getX()][shooted.getY()] = FieldType.SHOOTED;
								resultPoints.put(shooted, FieldType.SHOOTED);
							}
						}
					}
				}
			}
		}
		
		return resultPoints;
	}

	public boolean isMyBoard() {
		return myBoard;
	}

	public void setMyBoard(boolean myBoard) {
		this.myBoard = myBoard;
	}
	
	public void setFieldsTo(HashMap<Point, FieldType> points) {
		
		for (Map.Entry<Point, FieldType> entry : points.entrySet()) {
			fields[entry.getKey().getX()][entry.getKey().getY()] = entry.getValue();
		}
		
	}
	
	
}
