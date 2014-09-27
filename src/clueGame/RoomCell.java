package clueGame;

public class RoomCell extends BoardCell{
	public enum DoorDirection {
		UP, DOWN, RIGHT, LEFT, NONE;
		DoorDirection(){
			
		}
	}
	private DoorDirection doorDirection;
	private char roomInitial;
	
	@Override
	public boolean isRoom(){
		return true;
	}
	public RoomCell() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void draw(){
		
	}
	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}
	public Character getInitial() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
