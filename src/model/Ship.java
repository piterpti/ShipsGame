package model;

import java.util.List;
import java.util.LinkedList;

public class Ship {

	private List<FieldId> shipFields = new LinkedList<>();
	private ShipType shipType;
	
	public Ship(List<FieldId> aShipFields, ShipType aShipType) {
		shipFields = aShipFields;
		shipType = aShipType;
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
}
