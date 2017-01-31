package tools;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Logger;

import model.Board;
import model.Point;
import model.Ship;

public class ShipGenerator {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void generateShips(Board board) {
		
		LinkedList<Ship> ships = new LinkedList<>();
		HashSet<Point> lockFields = new HashSet<>();
		
		LOGGER.info("Generating ships just started..");
		long startTime = System.currentTimeMillis();
		
		ships.add(generateShip(4, lockFields));
		
		ships.add(generateShip(3, lockFields));
		ships.add(generateShip(3, lockFields));
		
		ships.add(generateShip(2, lockFields));
		ships.add(generateShip(2, lockFields));
		ships.add(generateShip(2, lockFields));
		
		ships.add(generateShip(1, lockFields));
		ships.add(generateShip(1, lockFields));
		ships.add(generateShip(1, lockFields));
		ships.add(generateShip(1, lockFields));
		
		long opertaionTime = System.currentTimeMillis() - startTime;
		
		LOGGER.info("Genrating takes " + opertaionTime + "ms");
		
		board.setShips(ships);
	}
	
	private static Ship generateShip(int shipSize, HashSet<Point> lockFields) {
		shipSize--;
		
		Random generator = new Random();
		boolean repeat = true;
		LinkedList<Point> shipFields = null;
		
		while (repeat) {
			repeat = false;
			shipFields = new LinkedList<>();
			int x = generator.nextInt(10);
			int y = generator.nextInt(10);
			
			boolean vertical = generator.nextInt(2) == 0 ? true : false;
			
			if (vertical) {
				if (y + shipSize < 9) {
					for (int i = 0; i <= shipSize; i++) {
						shipFields.add(new Point(x, y + i));
					}
				} else if (y - shipSize > 0) {
					for (int i = 0; i <= shipSize; i++) {
						shipFields.add(new Point(x, y - i));
					}
				}
			} else {
				if (x + shipSize < 9) {
					for (int i = 0; i <= shipSize; i++) {
						shipFields.add(new Point(x + i, y));
					}
				} else if (x - shipSize > 0) {
					for (int i = 0; i <= shipSize; i++) {
						shipFields.add(new Point(x - i, y));
					}
				}
			}
			
			LinkedList<Point> tmpPoints = new LinkedList<>(shipFields);
			tmpPoints.addAll(new Ship(shipFields).getNeighbourPoints());
			
			for (Point p : tmpPoints) {
				for (Point pLock : lockFields) {
					if (pLock.equals(p)) {
						repeat = true;
						continue;
					}
				}
			}
		}
		
		lockFields.addAll(shipFields);
		
		return new Ship(shipFields);
	}
	
}
