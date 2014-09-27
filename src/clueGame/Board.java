package clueGame;

import java.util.Map;
import java.util.Set;

public class Board {
	private int numRows;
	private int numCols;
	private BoardCell[][] boardLayout= new BoardCell[numRows][numCols]; 
	private Set<BoardCell> targets;
	private Map<Character, String> rooms;
	public Board() {
		// TODO Auto-generated constructor stub
	}
	public void loadBoardConfig(){
		
	}
	public BoardCell getBoardPiece(int row, int col){
		return boardLayout[row][col];
	}
	public Map<Character, String> getRooms(){
		return rooms;
	}
	public int getNumRows(){
		return numRows;
	}
	public int getNumCols(){
		return numCols;
	}
	
}
