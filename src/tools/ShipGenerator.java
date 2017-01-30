package tools;

import java.util.LinkedList;

import model.Board;
import model.Point;
import model.Ship;

public class ShipGenerator {

	public static void generateShips(Board board) {
		
		LinkedList<Ship> ships = new LinkedList<>();
		
		LinkedList<Point> shipFields = new LinkedList<>();
		shipFields.add(new Point(1, 1));
		ships.add(new Ship(shipFields));
		
		shipFields = new LinkedList<>();
		shipFields.add(new Point(3, 2));
		shipFields.add(new Point(3, 3));
		shipFields.add(new Point(3, 4));
		ships.add(new Ship(shipFields));
		
		board.setShips(ships);
	}
	
}
