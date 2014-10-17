package clueGame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameActionTests {
	
	Solution accusationCorrect;
	Solution accusationIncorrect;
	ClueGame game;
	
	@Before
	public void setUp() {
		accusationCorrect = new Solution();
		accusationIncorrect = new Solution();
		game = new ClueGame();
	}

	@Test
	public void makeAccusationTest() {
		// correct accusations
		accusationCorrect.person = "mrs peacock";
		accusationCorrect.room = "kitchen";
		accusationCorrect.weapon = "candle stick";
		assertTrue(game.checkAccusation(accusationCorrect));
		
		// incorrect accusations
		accusationIncorrect.person = "reverand green";
		accusationIncorrect.room = "billiard room";
		accusationIncorrect.weapon = "dagger";
		assertFalse(game.checkAccusation(accusationIncorrect));
	}

}
