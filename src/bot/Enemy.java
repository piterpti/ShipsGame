package bot;

import model.Board;
import tools.ShipGenerator;

public class Enemy implements EnemeyMethods {

	public static Board myBoard;
	public static Board enemmyBoard;
	
	public Enemy(Board aEnemyBoard) {
		myBoard = new Board();
		ShipGenerator.generateShips(myBoard);
		enemmyBoard = aEnemyBoard;
	}

	@Override
	public void getNextPoint() {
		
	}
	
}
