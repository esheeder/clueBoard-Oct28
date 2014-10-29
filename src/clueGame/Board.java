package clueGame;

import clueGame.BoardCell;
import clueGame.RoomCell.DoorDirection;

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

public class Board extends JPanel {
	private int numRows;
	private int numCols;
	private BoardCell[][] boardLayout; 
	private Set<BoardCell> targets;
	private Map<Character, String> rooms;
	private Set<BoardCell> visited;
	//Map for adj matrix calculations
	private Map<Integer, LinkedList<BoardCell>> adjMtx;
	private BoardCell startingCell;
	private Set<BoardCell> deadEnds = new HashSet<BoardCell>();
	//private ArrayList<Player> players = new ArrayList<Player>();
	private ClueGame game;
	public Board(ClueGame game) {
		adjMtx= new HashMap<Integer, LinkedList<BoardCell>>();
		visited= new HashSet<BoardCell>();
		targets= new HashSet<BoardCell>();
		this.game = game;
	}
	
	// draw the board
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				boardLayout[i][j].draw(g, this);
			}
		}
		
		for (Player p : game.getPlayers()) {
			p.draw(g);
		}
	}
	
	
	//tell boardLayout what size to be
	public void initializeBoardLayout(){
		FileReader reader= null;
		Scanner setUp=null;
		try{
			reader=new FileReader(ClueGame.boardLayout);
			setUp=new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
		int rows=1;
		String[] line;
		line=setUp.nextLine().split(",");
		numCols=line.length;
		
		while(setUp.hasNextLine()){
			setUp.nextLine();
			rows++;
		}
		numRows=rows;
		boardLayout= new BoardCell[numRows][numCols]; 
		setUp.close();
	}
	//load board into board layout
	public void loadBoardConfig() throws BadConfigFormatException{
		//initialize board size
		initializeBoardLayout();
		FileReader reader= null;
		Scanner in = null;
		try{
			reader=new FileReader(ClueGame.boardLayout);
			in= new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
		String[] line;
		String key=null;
		int row=0;
		//populate board with proper cell types
		while(in.hasNextLine()){
			line=in.nextLine().split(",");
			if(line.length!=numCols){
				throw new BadConfigFormatException("Improper number of columns in boardLayout.");
			}
			int col=0;
			//populate each row left to right
			for(String type:line){
				key=type;
				if(rooms.containsKey(key.charAt(0)) && key.length() == 1 && !key.equals("W")){
					boardLayout[row][col]=new RoomCell(key.charAt(0),RoomCell.DoorDirection.NONE);
				}else if(key.equals("W") && key.length() == 1){
					boardLayout[row][col]=new WalkwayCell();
				}else if(!rooms.containsKey(key.charAt(0))){
					throw new BadConfigFormatException("Invalid symbol in room.");
				}else if(key.charAt(0)=='W' && key.length() != 1){
					throw new BadConfigFormatException("Invalid symbol in room.");
				}else{
					char direction =key.charAt(1);
					switch (direction){
						case 'U':
							boardLayout[row][col]=new RoomCell(key.charAt(0),RoomCell.DoorDirection.UP);
							break;
						case 'D':
							boardLayout[row][col]=new RoomCell(key.charAt(0),RoomCell.DoorDirection.DOWN);
							break;
						case 'L':
							boardLayout[row][col]=new RoomCell(key.charAt(0),RoomCell.DoorDirection.LEFT);
							break;
						case 'R':
							boardLayout[row][col]=new RoomCell(key.charAt(0),RoomCell.DoorDirection.RIGHT);
							break;
						case 'N':
							RoomCell temp = new RoomCell(key.charAt(0),RoomCell.DoorDirection.NONE);
							temp.setName(rooms.get(key.charAt(0)));
							boardLayout[row][col]= temp;
							break;
						default:
							break;
					}
				}
				boardLayout[row][col].setRow(row);
				boardLayout[row][col].setCol(col);
				col++;
			}
			row++;
		}
		in.close();
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
	public int getNumColumns(){
		return numCols;
	}
	public RoomCell getRoomCellAt(int i, int j) {
		BoardCell cell=getBoardPiece(i,j);
		RoomCell room=null;
		if(cell.isRoom()){
			room = (RoomCell)cell;
		}
		return room;
	}
	public BoardCell getCellAt(int i, int j) {
		return boardLayout[i][j];
	}
	//HELPER FUNCTION FOR RETURN ADJLIST, maps an integer to a coordinate
	public int getCellNumber(int row, int col){
		int cellNumber=row*numCols+col;
		return cellNumber;
	}
	public void setRooms( Map<Character,String> rooms){
		this.rooms=rooms;
	}
	public void calcTargets(int row, int col, int roll) {
		
		if(visited.isEmpty())
		{
			startingCell = getCellAt(row, col);
			targets.clear();
			deadEnds.clear();
		}
		
		LinkedList<BoardCell> adjCells=getAdjList(row, col);
		LinkedList<BoardCell> adjList=new LinkedList<BoardCell>();
		
		
		if(adjCells.size() == 1 && roll > 1 )
		{
			deadEnds.add(getCellAt(row, col));
		}
		
		
		for(BoardCell unvisited:adjCells){
			if(!visited.contains(unvisited) ){
				
				adjList.add(unvisited);
		        if(unvisited.isDoorway())
		        {
		        	targets.add(unvisited);
		        }
				
			}
			
		}
		if(roll==1){
			for(BoardCell targetCells:adjList){
			    
				targets.add(targetCells);
				targets.remove(startingCell);
				
			}
		}else{
			for(BoardCell tmp:adjList){
				visited.add(tmp);
				calcTargets(tmp.getRow(), tmp.getCol(),roll-1);
				visited.remove(tmp);
			}
		}
		for(BoardCell deadend: deadEnds)
		{
			targets.remove(deadend);
		}
	}
	
	public Set<BoardCell> getTargets() {
		Set<BoardCell> copyTargets = new HashSet<BoardCell>();
		for(BoardCell b : targets) {
			copyTargets.add(b);
		}
		targets.clear();
		return copyTargets;
	}
	
	public LinkedList<BoardCell> getAdjList(int row, int col) {
		return adjMtx.get(getCellNumber(row,col));
	}
	
	public void calcAdjacencies(){
		int cellNumber = 0;
		cellNumber=0;
		for(int i=0; i < numRows; i++){
			for(int j=0; j < numCols; j++){
				populateAdjMtx(cellNumber, i, j);
				cellNumber++;
			}
		}
	}
	private void populateAdjMtx(int cellNum, int row, int col){
		LinkedList<BoardCell> adjCells=new LinkedList<BoardCell>();
		ArrayList<BoardCell> neighbors=new ArrayList<BoardCell>();
		if(boardLayout[row][col].isRoom() && !boardLayout[row][col].isDoorway()){
			
		}else if(boardLayout[row][col].isDoorway()){
			RoomCell door=(RoomCell)boardLayout[row][col];
			if(door.getDoorDirection()==RoomCell.DoorDirection.UP && row-1 >=0 ){
				neighbors.add(boardLayout[row-1][col]);
			}else if(door.getDoorDirection()==RoomCell.DoorDirection.DOWN && row+1 < numRows){
				neighbors.add(boardLayout[row+1][col]);
			}else if(door.getDoorDirection()==RoomCell.DoorDirection.LEFT && col-1 >= 0){
				neighbors.add(boardLayout[row][col-1]);
			}else if(door.getDoorDirection()==RoomCell.DoorDirection.RIGHT && col+1 < numCols){
				neighbors.add(boardLayout[row][col+1]);
			}else{}
			//check if neighbors is populated
			if(neighbors.size() >0){
				adjCells.add(neighbors.get(0));
			}
		}else{
			if(row+1 < numRows && (boardLayout[row+1][col].isDoorway() || boardLayout[row+1][col].isWalkway())){
				//if doorway is adjacent, check if proper direction
				if(boardLayout[row+1][col].isDoorway()){
					RoomCell room=(RoomCell)boardLayout[row+1][col];
					if(room.getDoorDirection()==RoomCell.DoorDirection.UP){
						neighbors.add(boardLayout[row+1][col]);
					}
				}else{
					neighbors.add(boardLayout[row+1][col]);
				}
			}
			if(row-1 >= 0 && (boardLayout[row-1][col].isDoorway() || boardLayout[row-1][col].isWalkway()) ){
				//if doorway is adjacent, check if proper direction
				if(boardLayout[row-1][col].isDoorway()){
					RoomCell room=(RoomCell)boardLayout[row-1][col];
					if(room.getDoorDirection()==RoomCell.DoorDirection.DOWN){
						neighbors.add(boardLayout[row-1][col]);
					}
				}else{
					neighbors.add(boardLayout[row-1][col]);
				}
			}
			if(col+1 < numCols && (boardLayout[row][col+1].isDoorway() || boardLayout[row][col+1].isWalkway()) ){
				//if doorway is adjacent, check if proper direction
				if(boardLayout[row][col+1].isDoorway()){
					RoomCell room=(RoomCell)boardLayout[row][col+1];
					if(room.getDoorDirection()==RoomCell.DoorDirection.LEFT){
						neighbors.add(boardLayout[row][col+1]);
					}
				}else{
					neighbors.add(boardLayout[row][col+1]);
				}
			}
			if(col-1 >= 0 && (boardLayout[row][col-1].isDoorway() || boardLayout[row][col-1].isWalkway()) ){
				//if doorway is adjacent, check if proper direction
				if(boardLayout[row][col-1].isDoorway()){
					RoomCell room=(RoomCell)boardLayout[row][col-1];
					if(room.getDoorDirection()==RoomCell.DoorDirection.RIGHT){
						neighbors.add(boardLayout[row][col-1]);
					}
				}else{
					neighbors.add(boardLayout[row][col-1]);
				}
			}
			for(int i=0; i<neighbors.size(); i++){
				adjCells.add(neighbors.get(i));
			}
		}
		adjMtx.put(cellNum, adjCells);
	}



}
/* REFERENCE TO WORK WITH FORM CLUE PATHS
private Map<Integer, LinkedList<BoardCell>> adjMtx;
private HashMap<Integer, BoardCell> cells;
private Set<BoardCell> visited;
private Set<BoardCell> targets;
private final int BOARD_HEIGHT=4;
private final int BOARD_WIDTH=4;
public IntBoard() {
	adjMtx= new HashMap<Integer, LinkedList<BoardCell>>();
	cells = new HashMap<Integer, BoardCell>();
	int cellNumber = 0;
	//populate entire adjacency list
	for(int i=0; i < BOARD_WIDTH; i++){
		for(int j=0; j < BOARD_HEIGHT; j++){
			BoardCell cell = new BoardCell(i,j);
			cells.put(cellNumber, cell);
			cellNumber++;
		}
	}
	cellNumber=0;
	for(int i=0; i < BOARD_WIDTH; i++){
		for(int j=0; j < BOARD_HEIGHT; j++){
			calcAdjacencies(cellNumber, getCell(i,j));
			cellNumber++;
			
		}
	}
	visited= new HashSet<BoardCell>();
	targets= new HashSet<BoardCell>();
}
//calculate adjacencies for single cell and populates adjList
public void calcAdjacencies(int cellNum, BoardCell cell){
	int row=cell.getRow();
	int col=cell.getCol();
	LinkedList<BoardCell> adjCells=new LinkedList<BoardCell>();
	ArrayList<BoardCell> neighbors=new ArrayList<BoardCell>();
	if(row+1 < BOARD_HEIGHT)
	neighbors.add(getCell(row+1, col));
	if(row-1 >= 0)
	neighbors.add(getCell(row-1, col));
	if(col+1 < BOARD_HEIGHT)
	neighbors.add(getCell(row, col+1));
	if(col-1 >= 0)
	neighbors.add(getCell(row, col-1));
	for(int i=0; i<neighbors.size(); i++)
	{
			adjCells.add(neighbors.get(i));
	
	}
	adjMtx.put(cellNum, adjCells);
	
}
public void calcTargets(BoardCell cell, int roll){
	//Clear sets for new roll
	visited.clear();
	targets.clear();
	//add cell player is at
	visited.add(cell);
	//find targets
	targetFinder(cell, roll);
	for(BoardCell tmp:targets){
		System.out.println(tmp + " is target");
	}
}
//Helper function for calc targets
public void targetFinder(BoardCell cell,int roll){
	System.out.println(cell + " roll is" + roll);
	LinkedList<BoardCell> adjCells=getAdjList(cell);
	LinkedList<BoardCell> adjList=new LinkedList<BoardCell>();
	for(BoardCell unvisited:adjCells){
		if(!visited.contains(unvisited)){
			adjList.add(unvisited);
		}
		
	}
	if(roll==1){
		for(BoardCell targetCells:adjList){
		
			targets.add(targetCells);

		}
	}else{
		for(BoardCell tmp:adjList){
			visited.add(tmp);
			targetFinder(tmp,roll-1);
			visited.remove(tmp);
		}
	}
}
public Set<BoardCell> getTargets(){
	return targets;
}
public LinkedList<BoardCell> getAdjList(BoardCell cell){
	return adjMtx.get(BOARD_HEIGHT*cell.getRow()+cell.getCol());
}
public BoardCell getCell(int row, int col){
	return cells.get(Math.abs(BOARD_HEIGHT*row + col));
}

}
*/