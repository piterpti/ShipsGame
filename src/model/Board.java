package model;

import java.util.LinkedList;

public class Board {

	private FieldType[][] fields;
	private LinkedList<Ship> ships;
	
	public Board(LinkedList<Ship> aShips) {
		fields = new FieldType[10][10];
		ships = aShips;
		clear();
		putShips();
	}
	
	public Board() {
		fields = new FieldType[10][10];
		clear();
	}
	
	private void clear() {
		for (int y = 0; y < 10; y++) {
			for (int x = 0; x < 10; x++) {
				fields[x][y] = FieldType.EMPTY;
			}
		}
	}
	
	private void putShips() {
		for (Ship s : ships) {
			for (Point p : s.getShipFields()) {
				fields[p.getX()][p.getY()] = FieldType.SHIP;
			}
		}
	}

	public FieldType[][] getFields() {
		return fields;
	}

	public void setFields(FieldType[][] fields) {
		this.fields = fields;
	}

	public LinkedList<Ship> getShips() {
		return ships;
	}

	public void setShips(LinkedList<Ship> ships) {
		clear();
		this.ships = ships;
		putShips();
	}
}