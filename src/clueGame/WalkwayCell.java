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
		g.fillRect(30*getCol(), 30*getRow(), 30, 30);
		g.setColor(Color.BLACK);
		g.drawRect(30*getCol(), 30*getRow(), 30, 30);
	}
}
