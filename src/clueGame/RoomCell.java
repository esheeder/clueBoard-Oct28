package clueGame;

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
	
	public RoomCell(char roomInitial, DoorDirection direction) {
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
	@Override
	public void draw(){
		
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public char getInitial() {
		return roomInitial;
	}
	
}
