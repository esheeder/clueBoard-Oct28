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
	
}
