package game;

import java.util.LinkedList;
import java.util.logging.Logger;

import model.FieldId;
import model.Ship;
import model.ShipType;

public class Board {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private ShipType[][] fields;
	
	private Ship[] ships;
	
	public Board() {
		fields = new ShipType[11][11];
		clearBoard();
		ships = new Ship[10];
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
	
	public void setShips(LinkedList<Ship> aShips) {
		for (int i = 0; i < aShips.size(); i++) {
			ships[i] = aShips.get(i);
			for (FieldId fId : ships[i].getShipFields()) {
				fields[fId.getX()][fId.getY()] = ships[i].getShipType();
			}
		}
	}
	
	public void clickEventEnemy(FieldId id) {
		for (int x = 0; x < fields.length; x++) {
			for (int  y = 0; y < fields[0].length; y++) {
				FieldId aid = new FieldId();
				char xChar =(char)((int)'A' + x);
				aid.setX(xChar);
				aid.setY(y + 1);
				if (aid.equals(id)) {
					ShipType type = fields[y][x];
					if (type == ShipType.FOUR_MAST || type == ShipType.THREE_MAST ||
							type == ShipType.TWO_MAST || type == ShipType.ONE_MAST) {
						fields[y][x] = ShipType.DAMAGED;
						checkIsShipDestroyed(id);
					}
				}
			}
		}
	}
	
	public void clickEventMy(FieldId id) {
		
	}
	
	private boolean checkIsShipDestroyed(FieldId fId) {
		for (Ship ship : ships) {
			if (ship != null) {
				int shipLive = ship.getLives();
				for (FieldId tmpId : ship.getShipFields()) {
					if (fId.equals(tmpId)) {
						shipLive--;
					}
				}
				
				if (shipLive < 1) {
					LOGGER.info("Ship destroyed!");
				}
			}
		}
		return false;
	}

}
