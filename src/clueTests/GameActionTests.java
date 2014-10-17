package clueTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import clueGame.ClueGame;
import clueGame.Solution;

public class GameActionTests {
	
	Solution accusationCorrect;
	Solution accusationIncorrect;
	ClueGame game;
	
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

}
