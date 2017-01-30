package model;

public class FieldId {
	
	public FieldId(){};
	
	public FieldId(int x, int y) {
		super();
		X = x;
		Y = y;
	}
	private int X;
	private int Y;
	
	@Override
	public String toString() {
		return X + "" + Y;
	}
	
	public int getX() {
		return X;
	}
	
	public void setX(char x) {
		X = x;
	}
	
	public int getY() {
		return Y;
	}
	
	public void setY(int y) {
		Y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof FieldId) {
			FieldId tmpId = (FieldId) obj;
			if (tmpId.X == X && tmpId.Y == Y) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
