package model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;

public class Ship implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LinkedList<Point> shipFields;
	private int size;
	private int hits = 0;
	
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
		hits++;
		return size < 1;
	}
	
	public boolean isShipDestroyed() {
		return size < 1;
	}
	
	public boolean twoHits() {
		return (!isShipDestroyed() && hits > 1) ? true : false;
	}
	
	public LinkedList<Point> getDamagedFields(Board onBoard) {
		LinkedList<Point> damagedFields = new LinkedList<>();
		
		for (Point p : shipFields) {
			if (onBoard.getFields()[p.getX()][p.getY()] == FieldType.DAMAGED) {
				damagedFields.add(p);
			}
		}
		
		return damagedFields;
	}
	
	public LinkedList<Point> getShipSuspect(Board onBoard) {
		LinkedList<Point> damagedFields = getDamagedFields(onBoard);
						
		Point first = damagedFields.getFirst();
		Point last = damagedFields.getLast();
		
		HashSet<Point> suspects = new HashSet<>();
		
		if (first.getX() == last.getX()) {
			for (Point p : damagedFields) {
				suspects.addAll(p.getNeigbourPointsVertical());
			}
		} else {
			for (Point p : damagedFields) {
				suspects.addAll(p.getNeigbourPointsHorizontal());
			}
		}
		
		LinkedList<Point> shipSuspects = new LinkedList<>();
		for (Point p : suspects) {
			FieldType type = onBoard.getFields()[p.getX()][p.getY()];
			if (type == FieldType.EMPTY ||
					type == FieldType.SHIP) {
				shipSuspects.add(p);
			}
		}
		
		return shipSuspects;
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
