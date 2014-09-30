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
	private String boardLayout;
	private String legend;
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
		board.loadBoardConfig();
		loadRoomConfig();
	}
	public Board getBoard(){
		return board;
	}
	//populate rooms map
	public void loadRoomConfig() {
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
			roomKey=legendLine.charAt(0);
			room=legendLine.substring(3);
			rooms.put(roomKey, room);
		}
		board.setRooms(rooms);
	}
	//temp
	public static void main(String[] args){
		ClueGame game=new ClueGame("BoardLayout.csv", "BoardLegend.txt");
		game.loadRoomConfig();
		
	}

}
