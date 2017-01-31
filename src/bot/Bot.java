package bot;

import java.util.logging.Logger;

import game.Main;
import model.Board;
import tools.ShipGenerator;

public class Bot {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static Board myBoard;
	public static Board enemmyBoard;
	
	public Bot() {
		myBoard = new Board();
		ShipGenerator.generateShips(myBoard);
	}
	
	public void nextTurn() {
		
		LOGGER.info("BOT TURN..");
		
		Main.userMove = true;
	}

	public static Board getMyBoard() {
		return myBoard;
	}

	public static void setMyBoard(Board myBoard) {
		Bot.myBoard = myBoard;
	}

	public static Board getEnemmyBoard() {
		return enemmyBoard;
	}

	public static void setEnemmyBoard(Board enemmyBoard) {
		Bot.enemmyBoard = enemmyBoard;
	}
	
	

}
