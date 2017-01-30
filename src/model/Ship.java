package model;

import java.util.LinkedList;

public class Ship {

	private LinkedList<Point> shipFields;
	private final int size;
	
	public Ship(LinkedList<Point> shipFields) {
		this.shipFields = shipFields;
		this.size = shipFields.size();
	}

	public LinkedList<Point> getShipFields() {
		return shipFields;
	}

	public void setShipFields(LinkedList<Point> shipFields) {
		this.shipFields = shipFields;
	}
	
	public int getSize() {
		return size;
	}
}
