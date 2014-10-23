package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell{

	public WalkwayCell() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isWalkway(){
		return true;
	}
	@Override
	public void draw(Graphics g, Board b){
		// Walkway cells
		g.setColor(Color.YELLOW);
		g.drawRect(10, 10, 15, 15);
	}
}
