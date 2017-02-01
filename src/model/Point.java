package model;

import java.util.HashSet;
import java.util.LinkedList;

public class Point {

	private int x;
	private int y;
	private boolean damaged = false;
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isDamged() {
		return damaged;
	}

	public void setDamged(boolean damged) {
		this.damaged = damged;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point p = (Point) obj;
			if (x == p.getX() && y == p.getY()) {
				return true;
			}
		} else {
			return false;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return x + "x" + y;
	}
	
	public LinkedList<Point> getNeigbourPoints() {
		HashSet<Point> points = new HashSet<>();
		
		if (x == 0 && y == 0) {
			points.add(new Point(x + 1, y));
			points.add(new Point(x + 1, y + 1));
			points.add(new Point(x, y + 1));
		} else if (x == 9 && y == 0) {
			points.add(new Point(x - 1, y));
			points.add(new Point(x - 1, y + 1));
			points.add(new Point(x, y + 1));
		} else if (x == 0 && y == 9) {
			points.add(new Point(x, y - 1));
			points.add(new Point(x + 1, y - 1));
			points.add(new Point(x + 1, y));
		} else if (x == 9 && y == 9) {
			points.add(new Point(x - 1, y));
			points.add(new Point(x - 1, y - 1));
			points.add(new Point(x, y - 1));
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
		
		return new LinkedList<>(points);
	}
	
	public LinkedList<Point> getNeigbourPointsWithoutSlant(){
		HashSet<Point> points = new HashSet<>();
		
		if (x == 0 && y == 0) {
			points.add(new Point(x + 1, y));
			points.add(new Point(x, y + 1));
		} else if (x == 9 && y == 0) {
			points.add(new Point(x - 1, y));
			points.add(new Point(x, y + 1));
		} else if (x == 0 && y == 9) {
			points.add(new Point(x, y - 1));
			points.add(new Point(x + 1, y));
		} else if (x == 9 && y == 9) {
			points.add(new Point(x - 1, y));
			points.add(new Point(x, y - 1));
		} else if (x > 0 && y > 0 && x < 9 && y < 9) {
			points.add(new Point(x - 1, y - 1));
			points.add(new Point(x, y - 1));
			points.add(new Point(x + 1, y));
			points.add(new Point(x - 1, y));
			points.add(new Point(x, y + 1));
		} else if (y == 0 && x > 0 && x < 9) {
			points.add(new Point(x - 1, y));
			points.add(new Point(x + 1, y));
			points.add(new Point(x, y + 1));
		} else if (x == 0 && y > 0 && y < 9) {
			points.add(new Point(x, y - 1));
			points.add(new Point(x, y + 1));
			points.add(new Point(x + 1, y));
		} else if (y == 9 && x > 0 && x < 9) {
			points.add(new Point(x - 1, y ));
			points.add(new Point(x + 1, y));
			points.add(new Point(x, y - 1));
		} else if (x == 9 && y > 0 && y < 9) {
			points.add(new Point(x, y - 1));
			points.add(new Point(x, y + 1));
			points.add(new Point(x - 1, y));
		}
		
		return new LinkedList<>(points);
	}
}
