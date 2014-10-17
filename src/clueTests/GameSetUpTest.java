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
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetUpTest {
	private static ClueGame game;
	private static ArrayList<Player> players; 
	private static ArrayList<Card> cards;
	private int numOfPlayers;
	private int numOfWeapons;
	private int numOfRooms;
	// set up the board and a variety of cards
	@Before
	public void setUp() {
		game = new ClueGame();
		
		// load players
		game.loadPlayers();
		players = game.getPlayers();
		
		// load cards
		game.loadCards();
		cards = game.getCards();
		numOfPlayers = game.getNumOfPlayers();
		numOfWeapons = game.getNumOfWeapons();
		numOfRooms = game.getNumOfRooms();

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
		
	// Test the deal
		@Test
		public void validDealTest() {
			int playerCards = cards.size() / 6;
			int extraCards = 1; // players have within 1 card of other players
			
			game.deal();
			
			// all cards are dealt test
			assertEquals(0, game.getCardsLeft());
			
			// players have roughly same number of cards
			assertEquals(playerCards, players.get(0).getMyCards().size(), extraCards);
			assertEquals(playerCards, players.get(1).getMyCards().size(), extraCards);
			assertEquals(playerCards, players.get(2).getMyCards().size(), extraCards);
			assertEquals(playerCards, players.get(3).getMyCards().size(), extraCards);
			assertEquals(playerCards, players.get(4).getMyCards().size(), extraCards);
			assertEquals(playerCards, players.get(5).getMyCards().size(), extraCards);
			
			// one card is not given to more than one player
			assertTrue(players.get(0).getMyCards().contains(players.get(0).getMyCards().get(0))); // contains the card
			assertFalse(players.get(1).getMyCards().contains(players.get(0).getMyCards().get(0))); // doesn't contain the card
			assertFalse(players.get(2).getMyCards().contains(players.get(0).getMyCards().get(0)));
			assertFalse(players.get(2).getMyCards().contains(players.get(0).getMyCards().get(0)));
			assertFalse(players.get(4).getMyCards().contains(players.get(0).getMyCards().get(0)));
			assertFalse(players.get(5).getMyCards().contains(players.get(0).getMyCards().get(0)));
			
			
		}

}
