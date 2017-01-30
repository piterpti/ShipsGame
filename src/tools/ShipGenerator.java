package tools;

import game.Board;
import model.ShipType;

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
		
		board.getFields()[0][0] = ShipType.FOUR_MAST;
		board.getFields()[0][1] = ShipType.FOUR_MAST;
		board.getFields()[0][2] = ShipType.FOUR_MAST;
		board.getFields()[0][3] = ShipType.FOUR_MAST;
		
		board.getFields()[2][1] = ShipType.THREE_MAST;
		board.getFields()[2][2] = ShipType.THREE_MAST;
		board.getFields()[2][3] = ShipType.THREE_MAST;
		
		board.getFields()[4][3] = ShipType.THREE_MAST;
		board.getFields()[4][2] = ShipType.THREE_MAST;
		board.getFields()[4][1] = ShipType.THREE_MAST;
	}
	
}
