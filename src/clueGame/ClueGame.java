package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board board;
	public static String boardLayout;
	public static String legend;
	public ClueGame() {
		// TODO Auto-generated constructor stub
	}
	public ClueGame(String boardLayout, String legend) {
		rooms=new HashMap<Character, String>();
		board=new Board();
		this.boardLayout=boardLayout;
		this.legend=legend;
	}
	public void loadConfigFiles(){
		try{
			loadRoomConfig();
		}catch(BadConfigFormatException e){
			System.out.println(e.getMessage());
		}
		try{
			board.loadBoardConfig();
		}catch(BadConfigFormatException e){
			System.out.println(e.getMessage());
		}
	
	}
	public Board getBoard(){
		return board;
	}
	//populate rooms map
	public void loadRoomConfig() throws BadConfigFormatException{
		FileReader reader= null;
		Scanner in = null;
		try{
			reader=new FileReader(legend);
			in= new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
		String legendLine=null;
		String room=null;
		char roomKey;
		while(in.hasNextLine()){
			legendLine=in.nextLine();
			String[] testLine=legendLine.split(",");
			if(testLine.length > 2){
				throw new BadConfigFormatException("Cannot have more than two entries on single line in legend.");
			}
			roomKey=legendLine.charAt(0);
			room=legendLine.substring(3);
			rooms.put(roomKey, room);
		}
		board.setRooms(rooms);
	}

}
