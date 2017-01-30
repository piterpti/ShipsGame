package game;

import model.ShipType;

public class Board {

	private ShipType[][] fields;
	
	public Board() {
		fields = new ShipType[11][11];
		clearBoard();
	}
	
	private void clearBoard() {
		if (fields != null && fields.length == 11 && fields[0].length == 1) {
			for (int i = 0; i < fields[0].length; i++) {
				for (int x = 0; x < fields.length; x++) {
					fields[i][x] = ShipType.NO_ONE;
				}
			}
		}
	}

}
