package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class RoomCell extends BoardCell{
	public static final int DOOR_SIZE = 5;
	public static final int CELL_SIZE = 20;
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
	//Private Variables
	private DoorDirection doorDirection;
	private char roomInitial;
	private String name;
	
	//Used for cells that don't draw a name on the board
	public RoomCell(char roomInitial, DoorDirection direction){
		this.roomInitial=roomInitial;
		this.doorDirection=direction;
		name = null;
	}
	
	//Used for cells that need to draw the name on the board
	public RoomCell(String name, char roomInitial, DoorDirection direction){
		this.name = name;
		this.roomInitial = roomInitial;
		this.doorDirection = direction;
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
		//Rooms are 30 pixels by 30 pixels
		g.setColor(Color.gray);
		g.fillRect(CELL_SIZE*getCol(), CELL_SIZE*getRow(), CELL_SIZE, CELL_SIZE);
		
		//Doors are blue and 5x30
		if(isDoorway()) {
			g.setColor(Color.blue);
			switch(this.doorDirection) {
			
			case DOWN:
				g.fillRect(CELL_SIZE*getCol(), CELL_SIZE*getRow() + CELL_SIZE - DOOR_SIZE, CELL_SIZE, DOOR_SIZE);
				break;
			case UP:
				g.fillRect(CELL_SIZE*getCol(), CELL_SIZE*getRow(), CELL_SIZE, DOOR_SIZE);
				break;
			case RIGHT:
				g.fillRect(CELL_SIZE*getCol() + CELL_SIZE - DOOR_SIZE, CELL_SIZE*getRow(), DOOR_SIZE, CELL_SIZE);
				break;
			case LEFT:
				g.fillRect(CELL_SIZE*getCol(), CELL_SIZE*getRow(), DOOR_SIZE, CELL_SIZE);
				break;
			case NONE:
				break;
			}
		}
		
		//If the room is designated as a "name" room on the spreadsheet, this part will draw the name slightly up and to the left of the cell
		if (name != null) {
			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.PLAIN, 12));
			g.drawString(name, CELL_SIZE*getCol()-15, CELL_SIZE*getRow()-15);
		}
		
	}

}
