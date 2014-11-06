package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell{

	public static final int CELL_SIZE = 20;
	public WalkwayCell() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isWalkway(){
		return true;
	}
	@Override
	public void draw(Graphics g, Board b, Color c){
		// Walkway cells	
		g.setColor(c);
		g.fillRect(CELL_SIZE*getCol(), CELL_SIZE*getRow(), CELL_SIZE, CELL_SIZE);
		g.setColor(Color.BLACK);
		g.drawRect(CELL_SIZE*getCol(), CELL_SIZE*getRow(), CELL_SIZE, CELL_SIZE);
	}
}
