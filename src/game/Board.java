package game;

import model.ShipType;

public class Board {

	private ShipType[][] fields;
	
	public Board() {
		fields = new ShipType[11][11];
		clearBoard();
	}
	
	private void clearBoard() {
		if (fields != null && fields.length == 11 && fields[0].length == 11) {
			for (int i = 0; i < fields[0].length; i++) {
				for (int x = 0; x < fields.length; x++) {
					fields[i][x] = ShipType.NO_ONE;
				}
			}
		}
	}
	
	public ShipType[][] getFields() {
		return fields;
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		
		for (int x = 0; x < fields[0].length; x++) {
			for (int y = 0; y < fields.length; y++) {
				switch(fields[x][y]) {
				case DAMAGED:
					result.append("d");
					break;
				case DESTROYED:
					result.append("D");
					break;
				case FOUR_MAST:
					result.append("4");
					break;
				case THREE_MAST:
					result.append("3");
					break;
				case TWO_MAST:
					result.append("2");
					break;
				case ONE_MAST:
					result.append("1");
					break;
				case NO_ONE:
					result.append("n");
					break;
				}
			}
			result.append("\n");
		}
		
		return result.toString();
	}

}
