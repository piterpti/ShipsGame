package layout;

import java.awt.GridLayout;

public class BoardGui extends GridLayout {

	private static final long serialVersionUID = 1L;
	
	public static final int X_SIZE = 11;
	public static final int Y_SIZE = 11;

	public BoardGui() {
		super(X_SIZE, Y_SIZE, 0, 0);
	}
}
