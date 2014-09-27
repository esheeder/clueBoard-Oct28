package clueGame;

abstract public class BoardCell {
	private int row;
	private int col;
	public BoardCell() {
		// TODO Auto-generated constructor stub
	}
	public boolean isWalkway(){
		return true;
	}
	public boolean isRoom(){
		return false;
	}
	public boolean isDoorway(){
		return false;
	}
	abstract public void draw();

}
