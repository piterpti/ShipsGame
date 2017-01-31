package model;

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
}
