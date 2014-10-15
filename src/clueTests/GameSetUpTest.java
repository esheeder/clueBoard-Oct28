package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetUpTest {
	private static Board board;
	private static ArrayList<Player> players; 
	private static ArrayList<Card> cards;
	private int numOfPlayers = 0;
	private int numOfWeapons = 0;
	private int numOfRooms = 0;
	// set up the board and a variety of cards
	@Before
	public void setUp() {
		board = new Board();
		players = new ArrayList<Player>();
		cards = new ArrayList<Card>();
		
		// load players
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
		
		// load cards
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

	
	// Load the people from: 'Players.csv'
	// format: H/C, name, color, x, y
	@Test
	public void loadPeopleTest() {
		
		// test the size of the list of players
		assertEquals(players.size(), 6);
		
		// test the name of a player
		assertEquals("professor plum", players.get(0).getName());
		assertEquals("mrs white", players.get(5).getName());
		
		// test the color of a player
		assertEquals(Color.magenta, players.get(0).getColor());
		assertEquals(Color.yellow, players.get(4).getColor());
		
		// test the starting location of a player
		assertEquals(0, players.get(0).getX());
		assertEquals(3, players.get(0).getY());
		assertEquals(8, players.get(2).getX());
		assertEquals(20, players.get(2).getY());
		
	}
	
	// Load the cards from: 'Cards.csv'
		// format: P/W/R, name
		@Test
		public void loadCardsTest() {
			
			// test the number of cards
			assertEquals(21, cards.size());
			
			// test the number of players
			assertEquals(6, numOfPlayers);
			
			// test the number of weapons
			assertEquals(6, numOfWeapons);
			
			// test the number of rooms
			assertEquals(9, numOfRooms);
			
			// test deck for a card
			assertEquals("mrs peacock", cards.get(0).getName());
			assertEquals("mrs white", cards.get(1).getName());
			assertEquals("PERSON", cards.get(0).getCardType());
			assertEquals("PERSON", cards.get(1).getCardType());
			assertEquals("kitchen", cards.get(12).getName());
			assertEquals("ROOM", cards.get(12).getCardType());
			assertEquals("pipe", cards.get(6).getName());
			assertEquals("WEAPON", cards.get(6).getCardType());
		}

}
