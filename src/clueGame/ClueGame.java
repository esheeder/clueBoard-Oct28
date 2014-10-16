package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClueGame {
	private Map<Character, String> rooms;
	private ArrayList<Card> cards;
	private ArrayList<Player> players;
	private Board board;
	private int numOfPlayers = 0;
	private int numOfWeapons = 0;
	private int numOfRooms = 0;

	public static String boardLayout;
	public static String legend;
	public ClueGame() {
		rooms=new HashMap<Character, String>();
		board=new Board();
		cards = new ArrayList<Card>();
		players = new ArrayList<Player>();
		boardLayout="clueBoard/ClueLayout.csv";
		legend="clueBoard/ClueLegend.txt";
	}
	public ClueGame(String boardLayout, String legend) {
		rooms=new HashMap<Character, String>();
		board=new Board();
		ClueGame.boardLayout=boardLayout;
		ClueGame.legend=legend;
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
	
	public void deal() {
		getCards();
		getPlayers();
		//Collections.shuffle(cards);
		//Collections.shuffle(players);
		int playerNum = 0;
		for(int i = 0; i < cards.size(); i++) {
			if(playerNum < 6) {
				System.out.println("player: " + players.get(playerNum).getName() + " gets a card");
				players.get(playerNum).getMyCards().add(cards.get(i));
			}
			else playerNum = 0;
			playerNum++;
		}
	}
	
	public void selectAnswer() {
		
	}
	
	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
		
	}
	
	public Boolean checkAccusation(Solution solution) {
		return false;
	}
	
	public void loadCards() {
		
		FileReader reader2 = null;
		Scanner in2 = null;
		String line2 = null;
		try{
			reader2 = new FileReader("clueBoard/Cards.csv");
			in2 = new Scanner(reader2);
		}catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
		
		while(in2.hasNextLine()) {
			line2 = in2.nextLine();
			String [] entireline = line2.split(",");
			
			if(entireline[0].equals("P")) numOfPlayers++;
			if(entireline[0].equals("W")) numOfWeapons++;
			if(entireline[0].equals("R")) numOfRooms++;
			
			Card card = new Card(entireline[0],entireline[1]);
			cards.add(card);
		}
	}
	
	public void loadPlayers() {
		
		FileReader reader= null;
		Scanner in = null;
		String line = null;
		try{
			reader = new FileReader("clueBoard/Players.csv");
			in = new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
		
		while(in.hasNextLine()) {
			line = in.nextLine();
			String [] entireline = line.split(",");
			
			if(entireline[0].equals("H")) {
				HumanPlayer h = new HumanPlayer(entireline[1], entireline[2], Integer.parseInt(entireline[3]),Integer.parseInt(entireline[4]));
				players.add(h);
			}
			else {
				ComputerPlayer c = new ComputerPlayer(entireline[1], entireline[2], Integer.parseInt(entireline[3]),Integer.parseInt(entireline[4]));
				players.add(c);
			}
			
		}
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	public int getNumOfWeapons() {
		return numOfWeapons;
	}
	public int getNumOfRooms() {
		return numOfRooms;
	}

}
