package clueTests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameActionTests {
	
	Solution accusationCorrect;
	Solution accusationIncorrect;
	ClueGame game;
	Board board;
	ComputerPlayer player;
	
	@Before
	public void setUp() {
		accusationCorrect = new Solution();
		accusationIncorrect = new Solution();
		game = new ClueGame("BoardLayout.csv", "BoardLegend.txt");
		game.loadCards();
		game.loadPlayers();
	}

	@Test
	public void makeAccusationTest() {
		game.deal();
		game.setAnswer();
		// correct accusations
		accusationCorrect.person = "mrs peacock";
		accusationCorrect.room = "kitchen";
		accusationCorrect.weapon = "candle stick";
		assertTrue(game.checkAccusation(accusationCorrect));
		
		// incorrect accusations: wrong person
		accusationIncorrect.person = "reverand green";
		accusationIncorrect.room = "kitchen";
		accusationIncorrect.weapon = "candle stick";
		assertFalse(game.checkAccusation(accusationIncorrect));
		
		// incorrect accusations: wrong room
		accusationIncorrect.person = "mrs peacock";
		accusationIncorrect.room = "dining room";
		accusationIncorrect.weapon = "candle stick";
		assertFalse(game.checkAccusation(accusationIncorrect));
		
		// incorrect accusations: wrong weapon
		accusationIncorrect.person = "mrs peacock";
		accusationIncorrect.room = "kitchen";
		accusationIncorrect.weapon = "dagger";
		assertFalse(game.checkAccusation(accusationIncorrect));
		
		
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testTargetLocation() {
		
		game = new ClueGame();
		game.loadConfigFiles();
		player = new ComputerPlayer();
		board = game.getBoard();
		board.calcAdjacencies();
		
		
		// RANDOM CHOICE TESTS
		// Location with no rooms in target, 5 targets
		board.calcTargets(14, 0, 2);
		Set<BoardCell> targets= board.getTargets();
		
		for (BoardCell b: targets) {
			System.out.println(b.getCol() + " " + b.getRow());
		}
		
		int loc_12_0_Tot = 0;
		int loc_16_0_Tot = 0;
		int loc_15_1_Tot = 0;
		int loc_14_2_Tot = 0;
		int loc_13_1_Tot = 0;
		
		// Run the test 100 times
		for(int i = 0; i < 100; i++) {
			BoardCell selected = player.pickLocation(targets);
			if(selected == board.getCellAt(12, 0)) loc_12_0_Tot++;
			else if(selected == board.getCellAt(16, 0)) loc_16_0_Tot++;
			else if(selected == board.getCellAt(15, 1)) loc_15_1_Tot++;
			else if(selected == board.getCellAt(14, 2)) loc_14_2_Tot++;
			else if(selected == board.getCellAt(13, 1)) loc_13_1_Tot++;
			else {
				fail("Invalid target selected");
			}
		}
		
		// Ensure we have 100 total selections
		int selectionsTot = loc_12_0_Tot + loc_16_0_Tot + loc_15_1_Tot + loc_14_2_Tot + loc_13_1_Tot;
		assertEquals(100, selectionsTot);
		
		// Ensure each target was selected more than once
		System.out.println(loc_12_0_Tot + " " + loc_16_0_Tot + " " + loc_15_1_Tot + " " + loc_14_2_Tot + " " + loc_13_1_Tot);
		assertTrue(loc_12_0_Tot > 1);
		assertTrue(loc_15_1_Tot > 1);
		assertTrue(loc_14_2_Tot > 1);
		assertTrue(loc_16_0_Tot > 1);
		assertTrue(loc_13_1_Tot > 1);
		
		
		// ROOM PREFERENCE TESTS
		board.calcTargets(10, 3, 2);
		targets = board.getTargets();
		
		int counter = 0;
		for(int i = 0; i < 100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if(!(selected == board.getCellAt(11, 2))) fail("Invalid target selected");
			else counter++;
		}
		assertEquals(counter, 100);
		
			
	}
	
	@Test
	public void disprovingSuggestionsTest() {
		
		//Testing 1 player 4 times: once for each card, once for no ability to disprove
		Player p = new Player();
		p.setMyCards(new Card("P", "mrs white"));
		p.setMyCards(new Card("P", "professor plum"));
		p.setMyCards(new Card("W", "candle stick"));
		p.setMyCards(new Card("W", "pipe"));
		p.setMyCards(new Card("R", "study"));
		p.setMyCards(new Card("R", "library"));
		
		//Test for the person card
		Card c = p.disproveSuggestion("mrs white", "kitchen", "revolver");
		assertEquals(c.getName(), "mrs white");
		
		//Test for the room card
		c = p.disproveSuggestion("miss scarlett", "study", "revolver");
		assertEquals(c.getName(), "study");
		
		//Test for the room card
		c = p.disproveSuggestion("miss scarlett", "kitchen", "pipe");
		assertEquals(c.getName(), "pipe");
		
		//Test for no matching card
		c = p.disproveSuggestion("miss scarlett", "kitchen", "revolver");
		assertEquals(c, null);
		
		

		//Testing 1 player where they can return all 3 cards
		
		int person = 0;
		int room = 0;
		int weapon = 0;
		
		for (int i = 0; i < 100; i++) {
			c = p.disproveSuggestion("mrs white", "study", "pipe");
			if (c.getName().equals("study")) room++;
			else if (c.getName().equals("mrs white")) person++;
			else if (c.getName().equals("pipe")) weapon++;
			else fail("Invalid card selected");
		}
		assertTrue(person > 1);
		assertTrue(room > 1);
		assertTrue(weapon > 1);
	}
	
		//Testing multiple players
//		ArrayList<Player> testPlayers = new ArrayList<Player>();
//		HumanPlayer hp = new HumanPlayer("Bob", "red", 1, 1);
//		p2 = new ComputerPlayer("Rob", "blue", 2, 2);
//		ComputerPlayer p3 = new ComputerPlayer("Greg", "yellow", 3, 3);
//		ComputerPlayer p4 = new ComputerPlayer("Jim", "black", 4, 4);
//		testPlayers.add(hp);
//		testPlayers.add(p2);
//		testPlayers.add(p3);
//		testPlayers.add(p4);
//		game.setPlayers(testPlayers);
//		p2.setMyCards(new Card("P", "mrs white"));

}
