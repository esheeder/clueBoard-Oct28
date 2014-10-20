package clueTests;

import static org.junit.Assert.*;

import java.util.Set;

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
		game = new ClueGame("BoardLayout.csv", "BoardLegend.txt");
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
		
		game = new ClueGame();
		game.loadConfigFiles();
		player = new ComputerPlayer();
		board = game.getBoard();
		board.calcAdjacencies();
		
		
		// RANDOM CHOICE TESTS
		// Location with no rooms in target, 5 targets
		board.calcTargets(14, 0, 2);
		Set<BoardCell> targets= board.getTargets();
		
		int loc_12_0_Tot = 0;
		int loc_16_0_Tot = 0;
		int loc_15_1_Tot = 0;
		int loc_14_2_Tot = 0;
		int loc_13_1_Tot = 0;
		
		// Run the test 100 times
		for(int i = 0; i < 100; i++) {
			BoardCell selected = player.pickLocation(board.getTargets());
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
		assertTrue(loc_12_0_Tot > 1);
		assertTrue(loc_16_0_Tot > 1);
		assertTrue(loc_15_1_Tot > 1);
		assertTrue(loc_14_2_Tot > 1);
		assertTrue(loc_13_1_Tot > 1);
		
		
		// ROOM PREFERENCE TESTS

		
		
			
	}

}
