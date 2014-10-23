package clueGame;

import java.awt.Graphics;

abstract public class BoardCell {
	private int row;
	private int col;
	public BoardCell(){
		
	}
	public boolean isWalkway(){
		return false;
	}
	public boolean isRoom(){
		return false;
	}
	public boolean isDoorway(){
		return false;
	}
	public int getRow(){
		return row;
	}
	public int getCol(){
		return col;
	}
	public void setRow(int row){
		this.row=row;
	}
	public void setCol(int col){
		this.col=col;
	}
	@Override
	public String toString() {
		String boardCell="BoardCell [row=" + row + ", col=" + col + "]" + " is a ";
		if(isWalkway()){
			boardCell=boardCell.concat("walkway");
		}else if(isDoorway()){
			boardCell=boardCell.concat("door");
		}else if(isRoom()){
			boardCell=boardCell.concat("room");
		}else{
			boardCell=boardCell.concat("undefined");
		}
		return boardCell;
	}
	
	abstract public void draw(Graphics g, Board b);
 	

}
