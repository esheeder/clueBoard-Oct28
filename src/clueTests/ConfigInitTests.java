package clueTests;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.RoomCell;

public class ConfigInitTests {
	//static so only sets up one time using @BeforeClass
	private static Board board;
	public static final int NUM_ROOMS = 11;
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 23;
	
	@BeforeClass
	public static void testInit(){
		//Our game is BoardLayout.csv and BoardLegend.txt
		ClueGame game = new ClueGame("BoardLayout.csv", "BoardLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}
	//Tests for room legends
	//Test for room initials and number of rooms
	@Test
	public void testRooms(){
		Map<Character, String> rooms=board.getRooms();
		//Check for valid number of rooms
		Assert.assertEquals(NUM_ROOMS, rooms.size());
		//Check for existence of rooms
		Assert.assertEquals("Kitchen", rooms.get('K'));
		Assert.assertEquals("Study", rooms.get('S'));
		Assert.assertEquals("Hall", rooms.get('H'));
		Assert.assertEquals("Lounge", rooms.get('O'));
		Assert.assertEquals("Closet", rooms.get('X'));
	}
	//Test for board dimensions
	@Test
	public void testBoardDimensions() {
		Assert.assertEquals(NUM_ROWS, board.getNumRows());
		Assert.assertEquals(NUM_COLUMNS, board.getNumCols());		
	}
	/*not done
	//Tests for Bad config format exceptions
	@Test (expected= BadConfigFormatException.class)
	public void test() throws BadConfigFormatException, FileNotFoundException{
		//overload board with bad files
		ClueGame game = new ClueGame("BadBoardLayout.csv", "BoardLegend.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();	
	}
	//Tests for Bad config format exceptions
	@Test (expected= BadConfigFormatException.class)
	public void test() throws BadConfigFormatException, FileNotFoundException{
		//overload board with bad files
		ClueGame game = new ClueGame("BadBoardLayout.csv", "BoardLegend.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();	
	}
	//Tests for Bad config format exceptions
	@Test (expected= BadConfigFormatException.class)
	public void test() throws BadConfigFormatException, FileNotFoundException{
		//overload board with bad files
		ClueGame game = new ClueGame("BadBoardLayout.csv", "BoardLegend.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();	
	}*/
}
