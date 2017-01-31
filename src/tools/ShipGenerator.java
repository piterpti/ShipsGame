package tools;

import java.util.LinkedList;
import java.util.Random;

import model.Board;
import model.Point;
import model.Ship;

public class ShipGenerator {

	public static void generateShips(Board board) {
		
		LinkedList<Ship> ships = new LinkedList<>();
		Random generator = new Random();
		
		LinkedList<Point> lockField = new LinkedList<>();
		
		int x = generator.nextInt(10);
		int y = generator.nextInt(10);
		boolean vertical = generator.nextInt(2) == 0 ? true : false;
		
		LinkedList<Point> shipFields = new LinkedList<>();
		vertical = false;
		if (vertical) {
			if (y + 3 < 9) {
				shipFields.add(new Point(x, y));
				shipFields.add(new Point(x, y + 1));
				shipFields.add(new Point(x, y + 2));
				shipFields.add(new Point(x, y + 3));
			} else if (y - 3 > 0) {
				shipFields.add(new Point(x, y));
				shipFields.add(new Point(x, y - 1));
				shipFields.add(new Point(x, y - 2));
				shipFields.add(new Point(x, y - 3));
			}
			ships.add(new Ship(shipFields));
			
		} else {
			if (x + 3 < 9) {
				shipFields.add(new Point(x, y));
				shipFields.add(new Point(x + 1, y));
				shipFields.add(new Point(x + 2, y));
				shipFields.add(new Point(x + 3, y));
			} else if (x - 3 > 0) {
				shipFields.add(new Point(x, y));
				shipFields.add(new Point(x - 1, y));
				shipFields.add(new Point(x - 2, y));
				shipFields.add(new Point(x - 3, y));
			}
			ships.add(new Ship(shipFields));
		}
		
		board.setShips(ships);
	}
	
}
