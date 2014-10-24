package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class RoomCell extends BoardCell{
	public enum DoorDirection {
		UP('U'), DOWN('D'), RIGHT('R'), LEFT('L'), NONE('N');
		private char direction;
		DoorDirection(char aDirection){
			direction=aDirection;
		}
		public char toChar(){
			return direction;
		}
	}
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public RoomCell(char roomInitial, DoorDirection direction){
		this.roomInitial=roomInitial;
		this.doorDirection=direction;
	}
	@Override
	public boolean isRoom(){
		return true;
	}
	@Override
	public boolean isDoorway(){
		if(doorDirection!=DoorDirection.NONE){
			return true;
		}else{
			return false;
		}
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public char getInitial() {
		return roomInitial;
	}
	
	@Override
	public void draw(Graphics g, Board b){
		g.setColor(Color.gray);
		g.fillRect(30*getCol(), 30*getRow(), 30, 30);
		
		if(isDoorway()) {
			g.setColor(Color.blue);
			switch(this.doorDirection) {
			
			case DOWN:
				g.fillRect(30*getCol(), 30*getRow() + 25, 30, 5);
				break;
			case UP:
				g.fillRect(30*getCol(), 30*getRow(), 30, 5);
				break;
			case RIGHT:
				g.fillRect(30*getCol() + 25, 30*getRow(), 5, 30);
				break;
			case LEFT:
				g.fillRect(30*getCol(), 30*getRow(), 5, 30);
				break;
			}
		}
		
		
	}

}
