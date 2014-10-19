package clueTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
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
		game = new ClueGame();
		game.loadCards();
		game.loadPlayers();
		game.deal();
		game.setAnswer();
	}

	@Test
	public void makeAccusationTest() {
		
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
		
		player = new ComputerPlayer();
		board = new Board();
		
		// RANDOM CHOICE TESTS
		// Location with no rooms in target, 5 targets
		board.calcTargets(0, 14, 2);
		int loc_0_12_Tot = 0;
		int loc_0_16_Tot = 0;
		int loc_1_15_Tot = 0;
		int loc_2_14_Tot = 0;
		int loc_1_13_Tot = 0;
		
		// Run the test 100 times
		for(int i = 0; i < 100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
			if(selected == board.getCellAt(0, 12)) loc_0_12_Tot++;
			else if(selected == board.getCellAt(0, 16)) loc_0_16_Tot++;
			else if(selected == board.getCellAt(1, 15)) loc_1_15_Tot++;
			else if(selected == board.getCellAt(2, 14)) loc_2_14_Tot++;
			else if(selected == board.getCellAt(1, 13)) loc_1_13_Tot++;
			else {
				fail("Invalid target selected");
			}
		}
		
		// Ensure we have 100 total selections
		int selectionsTot = loc_0_12_Tot + loc_0_16_Tot + loc_1_15_Tot + loc_2_14_Tot + loc_1_13_Tot;
		assertEquals(100, selectionsTot);
		
		// Ensure each target was selected more than once
		assertTrue(loc_0_12_Tot > 1);
		assertTrue(loc_0_16_Tot > 1);
		assertTrue(loc_1_15_Tot > 1);
		assertTrue(loc_1_13_Tot > 1);
		assertTrue(loc_2_14_Tot > 1);
		
		
		// ROOM PREFERENCE TESTS

		
		
			
	}

}
