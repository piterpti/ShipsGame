package constants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.logging.Logger;

public class Constants {
	
	/** DEFAULT LOGGER */
	public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/** MENU BUTTON DEFAULT SIZE */
	public final static Dimension BTN_SIZE = new Dimension(350, 100);
	
	/** DEFAULT APP FONT */
	public final static Font FONT = new Font("Dialog", Font.BOLD, 16);
	
	/** COLORS */
	
	public static Color DEFAULT_COLOR = Color.GRAY;
	public static Color WATER_COLOR = new Color(23, 211, 232);
	public static Color SHIP_COLOR = Color.GREEN;
	public static Color DAMAGED_COLOR = Color.RED;
	public static Color DESTROYED_COLOR = Color.RED;
	public static Color SHOOTED_COLOR = new Color(255, 215, 96);
	
	public static Font getFontWithSize(int fontSize) {
		fontSize = fontSize < 1 ? 10 : fontSize;
		return new Font(FONT.getFontName(), FONT.getStyle(), fontSize);
	}
	
	
}
