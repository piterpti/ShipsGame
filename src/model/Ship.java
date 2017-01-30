package model;

import java.util.List;
import java.util.LinkedList;

public class Ship {

	private List<FieldId> shipFields = new LinkedList<>();
	private ShipType shipType;
	private int lives;
	
	public Ship(List<FieldId> aShipFields, ShipType aShipType) {
		shipFields = aShipFields;
		shipType = aShipType;
		lives = aShipFields.size();
	}

	public List<FieldId> getShipFields() {
		return shipFields;
	}

	public void setShipFields(List<FieldId> shipFields) {
		this.shipFields = shipFields;
	}

	public ShipType getShipType() {
		return shipType;
	}

	public void setShipType(ShipType shipType) {
		this.shipType = shipType;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	
	
}
