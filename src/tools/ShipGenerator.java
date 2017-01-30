package tools;

import java.util.LinkedList;
import java.util.List;

import game.Board;
import model.Ship;
import model.ShipType;
import model.FieldId;

public class ShipGenerator {

	/**
	 * Generate ships on board
	 * 1 - four masts
	 * 2 - three masts
	 * 3 - two masts
	 * 4 - one masts
	 * @param board
	 */
	public static void GenerateRandomShips(Board board) {
		
		LinkedList<Ship> ships = new LinkedList<>();
		
		List<FieldId> fields = new LinkedList<>();
		fields.add(new FieldId(0, 0));
		ships.add(new Ship(fields, ShipType.ONE_MAST));
		
		fields = new LinkedList<>();
		fields.add(new FieldId(2, 2));
		ships.add(new Ship(fields, ShipType.ONE_MAST));
		
		fields = new LinkedList<>();
		fields.add(new FieldId(4, 4));
		ships.add(new Ship(fields, ShipType.ONE_MAST));
		
		fields = new LinkedList<>();
		fields.add(new FieldId(6, 6));
		ships.add(new Ship(fields, ShipType.ONE_MAST));
		
		board.setShips(ships);
	}
	
}
