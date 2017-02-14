package layout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import game.Game;
import game.Main;
import model.Board;
import tools.ShipGenerator;

public class SetShips extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton generateShipBtn;
	private JButton playBtn;
	
	private Board board;
	
	
	private SetShipBoard shipsBoard;
	

	public SetShips(Game game) {
		super();
		frameSettings();
		addListeners(game);
		initBoard();
	}
	
	private void frameSettings() {
		setTitle("Set your ships");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		shipsBoard = new SetShipBoard();
		add(shipsBoard, BorderLayout.CENTER);
		
		generateShipBtn = new JButton("Generate ships");
		playBtn = new JButton("Play");
		
		cnfBtn(generateShipBtn);
		cnfBtn(playBtn);
		
		add(generateShipBtn, BorderLayout.NORTH);
		add(playBtn, BorderLayout.SOUTH);
		
		pack();
	}
	
	private void addListeners(Game game) {
		generateShipBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.clear();
				ShipGenerator.generateShips(board);
				shipsBoard.refresh(board);
			}
		});
		
		playBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				if (game != null) {
					game.setVisible(true);
					game.setMyBoard(board);
				} else {
					Main.backToMenu();
				}
			}
		});
	}
	
	private void initBoard() {
		board = new Board();
		board.setMyBoard(true);
		ShipGenerator.generateShips(board);
		shipsBoard.refresh(board);
	}
	
	private void cnfBtn(JButton btn) {
		Dimension dim = new Dimension(200, 100);
		btn.setMinimumSize(dim);
		btn.setPreferredSize(dim);
		btn.setMaximumSize(dim);
		btn.setSize(dim);
	}
	
}
