package clueGame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ClueGame extends JFrame {
	private Map<Character, String> rooms;
	private ArrayList<Card> cards;
	private ArrayList<Player> players;
	private Board board;
	private Solution solution;
	private int numOfPlayers = 0;
	private int numOfWeapons = 0;
	private int numOfRooms = 0;
	private int cardsLeft; 
	private DetectiveNotes myDN;
	public static String boardLayout;
	public static String legend;
	
	// Menu
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createFileDetectiveNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}
	
	// Exit Menu Item
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	// DetectiveNotes Menu Item
	private JMenuItem createFileDetectiveNotesItem() {
		final JMenuItem item = new JMenuItem("Show Notes");
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Open the Detective Notes Dialog Frame
				myDN = new DetectiveNotes();
				myDN.setModal(true);
				myDN.setVisible(true);
			}
		});
		return item;
	}


	public ClueGame() {
		rooms=new HashMap<Character, String>();
		board=new Board(this);
		boardLayout="ClueBoardLayout.csv";
		legend="ClueLegend.txt";
		// JPanel
		add(getBoard(), BorderLayout.CENTER);
		setSize(750,950);
		// JFrame
		JMenuBar file = new JMenuBar();
		setJMenuBar(file);
		file.add(createFileMenu());
	}
	public ClueGame(String boardLayout, String legend) {
		rooms=new HashMap<Character, String>();
		board=new Board(this);
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
		
		//Reading the file
		try{
			reader=new FileReader(legend);
			in= new Scanner(reader);
		}catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
		}
		String legendLine=null;
		String room=null;
		char roomKey;
		
		//Reading the legend file and filling up the map with info from it
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
		Random rn = new Random();
		solution = new Solution();
		
		//Randomly selecting a person, weapon, and room
		int p = rn.nextInt(6);
		int w = rn.nextInt(6) + 6;
		int r = rn.nextInt(9) + 12;
		solution.person = cards.get(p).getName();
		solution.weapon = cards.get(w).getName();
		solution.room = cards.get(r).getName();
		
		//Removing the person, weapon, and room selected
		cards.remove(r);
		cards.remove(w);
		cards.remove(p);
		
		Collections.shuffle(cards);
		Collections.shuffle(players);
		int playerNum = 0;
		for(int i = 0; i < getCards().size(); i++) {
			if(playerNum >= 6) {
				playerNum = 0;
			}
			players.get(playerNum).setMyCards(cards.get(i));
			//TODO Later: Add cardsseen if it is a computer player
			playerNum++;
			//variable used for testing
			cardsLeft--;
		}
	}

	
	public Boolean checkAccusation(Solution accusation) {

		if(!solution.person.equals(accusation.person)) {
			return false;
		}
		else if(!solution.room.equals(accusation.room)) {
			return false;
		}
		else if(!solution.weapon.equals(accusation.weapon)) {
			return false;
		}
		return true;
	}
	
	public void loadCards() {
		cards = new ArrayList<Card>();
		FileReader reader2 = null;
		Scanner in2 = null;
		String line2 = null;
		try{
			reader2 = new FileReader("Cards.csv");
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
		
		players = new ArrayList<Player>();
		
		FileReader reader= null;
		Scanner in = null;
		String line = null;
		try{
			reader = new FileReader("Players.csv");
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
	
	public Card handleSuggestion(String person, String room, String weapon, Player accusingPerson) {
		
		//Start at the next person in the array and go to the end
		for (int i = players.indexOf(accusingPerson) + 1; i < players.size(); i++) {
			Card c = players.get(i).disproveSuggestion(person, room, weapon);
			if (c != null) {
				return c;
			}
		}
		
		//If we didn't find anything, start back at the beginning and run up until the accusingPerson is reached
		for (int i = 0; i < players.indexOf(accusingPerson); i++) {
			Card c = players.get(i).disproveSuggestion(person, room, weapon);
			if (c != null) {
				return c;
			}
		}
		
		//If we ran through everyone and didn't find anything, return null
		return null;
	}
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	//Used for testing
	public void setPlayers(ArrayList<Player> somePlayers) {
		players = somePlayers;
	}
	
	
	// getters

	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	public int getNumOfWeapons() {
		return numOfWeapons;
	}
	public int getNumOfRooms() {
		return numOfRooms;
	}	
	public int getCardsLeft() {
		return cardsLeft;
	}
	
	// set an answer for testing
	public void setAnswer() {
		solution = new Solution();
		solution.person = "mrs peacock";
		solution.weapon = "candle stick";
		solution.room = "kitchen";
	}
	
	
	// used to display the board GUI
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		game.loadConfigFiles();
		game.setTitle("Clue");
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		game.loadPlayers();
		game.getBoard().paintComponent(game.getGraphics());
	}

}
