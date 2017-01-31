package model;

import java.util.HashSet;
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
		HashSet<Point> points = new HashSet<>();
		Point toAdd = null;
		
		for (Point p : shipFields) {
			int x = p.getX();
			int y = p.getY();
			if (x == 0 && y == 0) {
				toAdd = new Point(x + 1, y);
				points.add(toAdd);
				toAdd = new Point(x + 1, y + 1);
				points.add(toAdd);
				toAdd = new Point(x, y + 1);
				points.add(toAdd);
			} else if (x == 9 && y == 0) {
				toAdd = new Point(x - 1, y);
				points.add(toAdd);
				toAdd = new Point(x - 1, y + 1);
				points.add(toAdd);
				toAdd = new Point(x, y + 1);
				points.add(toAdd);
			} else if (x == 0 && y == 9) {
				toAdd = new Point(x, y - 1);
				points.add(toAdd);
				toAdd = new Point(x + 1, y - 1);
				points.add(toAdd);
				toAdd = new Point(x + 1, y);
				points.add(toAdd);
			} else if (x == 9 && y == 9) {
				toAdd = new Point(x - 1, y);
				points.add(toAdd);
				toAdd = new Point(x - 1, y - 1);
				points.add(toAdd);
				toAdd = new Point(x, y - 1);
				points.add(toAdd);
			} else if (x > 0 && y > 0 && x < 9 && y < 9) {
				points.add(new Point(x - 1, y - 1));
				points.add(new Point(x, y - 1));
				points.add(new Point(x + 1, y - 1));
				points.add(new Point(x + 1, y));
				points.add(new Point(x - 1, y));
				points.add(new Point(x - 1, y + 1));
				points.add(new Point(x, y + 1));
				points.add(new Point(x + 1, y + 1));
			} else if (y == 0 && x > 0 && x < 9) {
				points.add(new Point(x - 1, y));
				points.add(new Point(x + 1, y));
				points.add(new Point(x - 1, y + 1));
				points.add(new Point(x + 1, y + 1));
				points.add(new Point(x, y + 1));
			} else if (x == 0 && y > 0 && y < 9) {
				points.add(new Point(x, y - 1));
				points.add(new Point(x, y + 1));
				points.add(new Point(x + 1, y + 1));
				points.add(new Point(x + 1, y - 1));
				points.add(new Point(x + 1, y));
			} else if (y == 9 && x > 0 && x < 9) {
				points.add(new Point(x - 1, y ));
				points.add(new Point(x + 1, y));
				points.add(new Point(x - 1, y - 1));
				points.add(new Point(x + 1, y - 1));
				points.add(new Point(x, y - 1));
			} else if (x == 9 && y > 0 && y < 9) {
				points.add(new Point(x, y - 1));
				points.add(new Point(x, y + 1));
				points.add(new Point(x - 1, y + 1));
				points.add(new Point(x - 1, y - 1));
				points.add(new Point(x - 1, y));
			}
		}
		
		LinkedList<Point> result = new LinkedList<>();
		result.addAll(points);
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
