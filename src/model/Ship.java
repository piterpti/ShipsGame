package model;

import java.util.LinkedList;

public class Ship {

	private LinkedList<Point> shipFields;
	private int size;
	
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
	
	public boolean liveMinusAndItsDead() {
		size--;		
		return size < 1;
	}
	
	public boolean isShipDestroyed() {
		return size < 1;
	}
	
	public LinkedList<Point> getNeighbourPoints() {
		LinkedList<Point> result = new LinkedList<>();
		for (Point p : shipFields) {
			result.addAll(p.getNeigbourPoints());
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuffer bfr = new StringBuffer();
		bfr.append("Ship: ");
		for (Point p : shipFields) {
			bfr.append(p.toString());
			bfr.append(",");
		}
		return bfr.toString();
	}
}
