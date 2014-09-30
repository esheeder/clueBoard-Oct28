package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private int numRows;
	private int numCols;
	private BoardCell[][] boardLayout; 
	private Set<BoardCell> targets;
	private Map<Character, String> rooms;
	public Board() {
		// TODO Auto-generated constructor stub
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
		while(in.hasNextLine()){
			line=in.nextLine().split(",");
			int col=0;
			for(String type:line){
				key=type;
				//System.out.println("[" + row + "," + col + "]: " + key);
				if(rooms.containsKey(key.charAt(0)) && key.length() == 1){
					boardLayout[row][col]=new RoomCell(key.charAt(0),RoomCell.DoorDirection.NONE);
				}else if(key=="W"){
					boardLayout[row][col]=new Walkway();
				}else if(!rooms.containsKey(key.charAt(0))){
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
							boardLayout[row][col]=new RoomCell(key.charAt(0),RoomCell.DoorDirection.NONE);
							break;
						default:
							break;
					}
				}
				col++;
			}
			//System.out.println(col);
			/*if(col != numCols-1){
				throw new BadConfigFormatException("Bad layout, incorrect number of columns.");
			}*/
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
	public void setRooms( Map<Character,String> rooms){
		this.rooms=rooms;
	}
	
}
