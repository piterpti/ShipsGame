package bot;

import model.Board;
import tools.ShipGenerator;

public class Enemy implements EnemeyMethods {

	public static Board myBoard;
	public static Board enemmyBoard;
	
	public Enemy() {
		myBoard = new Board();
		ShipGenerator.generateShips(myBoard);
//		enemmyBoard = GameFrame.myBoard;
	}

	@Override
	public void getNextPoint() {
		
	}
	
}
