package clueGame;

import clueGame.BoardCell;
import clueGame.RoomCell.DoorDirection;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements MouseListener{
	public static final int CELL_SIZE = 20;
	
	private int numRows;
	private int numCols;
	private BoardCell[][] boardLayout; 
	private Set<BoardCell> targets;
	private Map<Character, String> rooms;
	private Set<BoardCell> visited;
	//Map for adj matrix calculations
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	//private ArrayList<Player> players = new ArrayList<Player>();
	private ClueGame game;
	public Board(ClueGame game) {
		adjMtx= new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited= new HashSet<BoardCell>();
		targets= new HashSet<BoardCell>();
		this.game = game;
		calcAdjacencies();
		addMouseListener(this);
	}

	// draw the board
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				boardLayout[i][j].draw(g, this, Color.YELLOW);
			}
		}

		for (Player p : game.getPlayers()) {
			p.draw(g);
		}
		//If it is the human's turn, display their targets
		if(game.isPlayerMustFinish()){
			for(BoardCell b : targets){
				b.draw(g, this, Color.BLUE);
			}
		}
	}
	//MouseListening
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e){
		if(game.isPlayerMustFinish()){
			//Only do something if it's the player's turn
			BoardCell cell = cellClicked(e.getX(),e.getY());
			if(targets.contains(cell)){
				//Initiate the move
				Player human = game.getPlayers().get(0);
				human.setX(cell.getRow());
				human.setY(cell.getCol());
				//If the cell is a room use a method in player to pop-up a suggestion
				if(cell.isRoom()){
					RoomCell rCell = (RoomCell) cell;
					String room = game.getRooms().get(rCell.getInitial());
					game.showSuggestWindow(room);
				}
				//Unflag because turn is over
				game.setPlayerMustFinish(false);
				repaint();
			} else
				JOptionPane.showMessageDialog(game, "Invalid move, select a highlighted square.");
		}
	}
	public BoardCell cellClicked(int x, int y){
		//Use int division to find cell coord
		int col = x/CELL_SIZE;
		int row = y/CELL_SIZE;
		if(col < numCols && row < numRows)
			return boardLayout[row][col];
		return null;
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
				//Error handling first
				if(!rooms.containsKey(key.charAt(0))){
					throw new BadConfigFormatException("Invalid symbol in room.");
				}
				if(key.charAt(0)=='W' && key.length() != 1){
					throw new BadConfigFormatException("Invalid symbol in room.");
				}
				//Cases where key is length 1
				if(key.length() == 1 && !key.equals("W")){
					boardLayout[row][col]=new RoomCell(key.charAt(0),RoomCell.DoorDirection.NONE);
				}else if(key.equals("W") && key.length() == 1){
					boardLayout[row][col]=new WalkwayCell();
				}else if(key.length() == 2){
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
						boardLayout[row][col]= new RoomCell(rooms.get(key.charAt(0)),key.charAt(0),RoomCell.DoorDirection.NONE);
						break;
					default:
						break;
					}
				}else {
					//Cases have all been covered
					throw new BadConfigFormatException("Invalid symbol in BoardConfig file");
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
	/*
	public void calcTargets(int row, int col, int roll) {
		targets.clear();
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
	*/
	public Set<BoardCell> getTargets() {
		return targets;
	}

	public LinkedList<BoardCell> getAdjList(int row, int col) {
		return adjMtx.get(getCellNumber(row,col));
	}
	public void calcAdjacencies() {
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		// Calculate a list for each cell on the board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				BoardCell currentCell = boardLayout[i][j];
				// Simple case, if cell is a doorway then only adj is direction
				// of doorway
				if (currentCell.isDoorway()) {
					// Convert BoardCell to RoomCell
					RoomCell currentRoomCell = (RoomCell) currentCell;
					// Create the LinkedList and add the appropriate direction
					LinkedList<BoardCell> currentAdjList = new LinkedList<BoardCell>();
					if (currentRoomCell.getDoorDirection() == DoorDirection.DOWN)
						currentAdjList.add(boardLayout[i + 1][j]);
					else if (currentRoomCell.getDoorDirection() == DoorDirection.RIGHT)
						currentAdjList.add(boardLayout[i][j + 1]);
					else if (currentRoomCell.getDoorDirection() == DoorDirection.LEFT)
						currentAdjList.add(boardLayout[i][j - 1]);
					else if (currentRoomCell.getDoorDirection() == DoorDirection.UP)
						currentAdjList.add(boardLayout[i - 1][j]);
					// Add the pair to the adjList map
					adjMtx.put(currentCell, currentAdjList);
				} else if (currentCell.isRoom()) {
					// Add an empty list
					adjMtx.put(currentCell, new LinkedList<BoardCell>());
				} else if (currentCell.isWalkway()) {
					// Create the linked list and add all directions if they are
					// allowed moves
					LinkedList<BoardCell> currentAdjList = new LinkedList<BoardCell>();
					if (isViableMove(i + 1, j, DoorDirection.UP))
						currentAdjList.add(boardLayout[i + 1][j]);
					if (isViableMove(i - 1, j, DoorDirection.DOWN))
						currentAdjList.add(boardLayout[i - 1][j]);
					if (isViableMove(i, j + 1, DoorDirection.LEFT))
						currentAdjList.add(boardLayout[i][j + 1]);
					if (isViableMove(i, j - 1, DoorDirection.RIGHT))
						currentAdjList.add(boardLayout[i][j - 1]);
					// Add the linked list to the map
					adjMtx.put(boardLayout[i][j], currentAdjList);
				}
			}
		}
	}

	public boolean isViableMove(int row, int col, DoorDirection allowedDoorDirection) {
		// If the cell is out of bounds return false
		if (row < 0 || row >= numRows)
			return false;
		if (col < 0 || col >= numCols)
			return false;
		// If cell is a room cell, check allowed direction
		if (getCellAt(row, col).isRoom()) {
			if (getCellAt(row, col).isDoorway()) {
				RoomCell aRoomCell = (RoomCell) getCellAt(row, col);
				if (allowedDoorDirection == aRoomCell.getDoorDirection())
					return true;
			}
			// Not a doorway, return false
			return false;
		}
		// Must be a walkway
		return true;
	}
	
	public void calcTargets(int row, int col, int moves){
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(getCellAt(row, col));
		findAllTargets(getCellAt(row, col), moves);
	}
	
	public void findAllTargets(BoardCell cell, int moves){
		LinkedList<BoardCell> adjCells = nonVisitedAdjCells(cell);
		for(BoardCell c : adjCells){
			visited.add(c);
			if(moves == 1 || c.isDoorway())
				targets.add(c);
			else
				findAllTargets(c, moves-1);
			visited.remove(c);
		}
		
	}
	
	public LinkedList<BoardCell> nonVisitedAdjCells(BoardCell cell){
		LinkedList<BoardCell> nonVisitedCells = new LinkedList<BoardCell>();
		LinkedList<BoardCell> adjLinkedList = adjMtx.get(cell);
		for(BoardCell c : adjLinkedList){
			if(!visited.contains(c)){
				nonVisitedCells.add(c);
			}
		}
		return nonVisitedCells;
	}
	/*
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
		if(boardLayout[row][col].isRoom() && !boardLayout[row][col].isDoorway()){
			//No adjacencies
		}else if(boardLayout[row][col].isDoorway()){
			RoomCell door=(RoomCell)boardLayout[row][col];
			switch(door.getDoorDirection()){
			case UP:
				adjCells.add(boardLayout[row-1][col]);
				break;
			case DOWN:
				adjCells.add(boardLayout[row+1][col]);
				break;
			case LEFT:
				adjCells.add(boardLayout[row][col-1]);
				break;
			case RIGHT:
				adjCells.add(boardLayout[row][col+1]);
				break;
			}
		}else{
			if(row+1 < numRows && (boardLayout[row+1][col].isDoorway() || boardLayout[row+1][col].isWalkway())){
				//if doorway is adjacent, check if proper direction
				if(boardLayout[row+1][col].isDoorway()){
					RoomCell room=(RoomCell)boardLayout[row+1][col];
					if(room.getDoorDirection()==RoomCell.DoorDirection.UP){
						adjCells.add(boardLayout[row+1][col]);
					}
				}else{
					adjCells.add(boardLayout[row+1][col]);
				}
			}
			if(row-1 >= 0 && (boardLayout[row-1][col].isDoorway() || boardLayout[row-1][col].isWalkway()) ){
				//if doorway is adjacent, check if proper direction
				if(boardLayout[row-1][col].isDoorway()){
					RoomCell room=(RoomCell)boardLayout[row-1][col];
					if(room.getDoorDirection()==RoomCell.DoorDirection.DOWN){
						adjCells.add(boardLayout[row-1][col]);
					}
				}else{
					adjCells.add(boardLayout[row-1][col]);
				}
			}
			if(col+1 < numCols && (boardLayout[row][col+1].isDoorway() || boardLayout[row][col+1].isWalkway()) ){
				//if doorway is adjacent, check if proper direction
				if(boardLayout[row][col+1].isDoorway()){
					RoomCell room=(RoomCell)boardLayout[row][col+1];
					if(room.getDoorDirection()==RoomCell.DoorDirection.LEFT){
						adjCells.add(boardLayout[row][col+1]);
					}
				}else{
					adjCells.add(boardLayout[row][col+1]);
				}
			}
			if(col-1 >= 0 && (boardLayout[row][col-1].isDoorway() || boardLayout[row][col-1].isWalkway()) ){
				//if doorway is adjacent, check if proper direction
				if(boardLayout[row][col-1].isDoorway()){
					RoomCell room=(RoomCell)boardLayout[row][col-1];
					if(room.getDoorDirection()==RoomCell.DoorDirection.RIGHT){
						adjCells.add(boardLayout[row][col-1]);
					}
				}else{
					adjCells.add(boardLayout[row][col-1]);
				}
			}
		}
		adjMtx.put(cellNum, adjCells);
	}
	*/


}