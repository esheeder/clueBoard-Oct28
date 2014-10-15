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
		cards = new ArrayList<Card>();

		
		reader= null;
		in = null;
		line = null;
		try{
			reader = new FileReader("clueBoard/Cards.csv");
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
		// format: H/C, name, color, x, y
		@Test
		public void loadCardsTest() {
			
			// test the number of cards
			assertEquals(21, cards.size());
			
			// test the number of players
			assertEquals(6, numOfPlayers);
			
			// test the number of weapons
			assertEquals(6, numOfWeapons);
			
			// test the number of rooms
			assertEquals(6, numOfRooms);
			
			// test deck for a card
			assertTrue(cards.contains(Card("W","pipe")));
			assertTrue(cards.contains(Card("P","mrs peacock")));
			assertTrue(cards.contains(Card("R","kitchen")));
			
		}

}
