package clueTests;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

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
	public static final int NUM_ROWS = 29;
	public static final int NUM_COLUMNS = 21;
	
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
		Assert.assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	// Test a few room cells to ensure the room initial is
	// correct.
	@Test
	public void testRoomInitials() {
		Assert.assertEquals('R', board.getRoomCellAt(0, 0).getInitial());
		Assert.assertEquals('B', board.getRoomCellAt(4, 8).getInitial());
		Assert.assertEquals('C', board.getRoomCellAt(24, 17).getInitial());
		Assert.assertEquals('L', board.getRoomCellAt(13, 20).getInitial());
		Assert.assertEquals('X', board.getRoomCellAt(16, 12).getInitial());
	}
	@Test
	public void doorWayTest()
	{
		BoardCell cell = board.getCellAt(0, 0);
		Assert.assertEquals(false, cell.isDoorway());
		
		RoomCell cell2= board.getRoomCellAt(26, 4);
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, cell2.getDoorDirection());
		
		BoardCell cell3 = null;
		int numOfDoors =0;
		for(int i=0; i<board.getNumRows();i++)
		{
			for(int j=0; j<board.getNumColumns();j++)
			{
				cell3 = board.getCellAt(i, j);
				if(cell3 instanceof RoomCell && cell3.isDoorway())
				{
					numOfDoors++;
				}
			}
		}
		Assert.assertEquals(18, numOfDoors);
	}
	//Tests for Bad config format exceptions
	@Test (expected= BadConfigFormatException.class)
	public void BadConfigRowTest() throws BadConfigFormatException, FileNotFoundException{
		//overload board with bad files
		ClueGame game = new ClueGame("BadBoardLayout.csv", "BoardLegend.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();
		
		/*Scanner s = new Scanner(new File(""));
		 while (s.hasNextLine()) 
		 {
	          String line = s.nextLine();
	          String[] cols = line.split(",");
	          if(cols.length != board.getNumColumns())
	          {
	           	throw new BadConfigFormatException();
	          }
	      
		 }*/
	}
	
	@Test (expected= BadConfigFormatException.class)
	public void BadConfigRoomTest() throws BadConfigFormatException, FileNotFoundException{
		//overload board with bad files
		ClueGame game = new ClueGame("BadBoardRoom.csv", "BoardLegend.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();
	}
	@Test (expected= BadConfigFormatException.class)
	public void BadConfigLegendTest() throws BadConfigFormatException, FileNotFoundException{
		//overload board with bad files
		ClueGame game = new ClueGame("BoardLayout.csv", "BadBoardLegend.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();
		/*Scanner s = new Scanner(new File("ClueLegend.txt"));
		while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] cols = line.split(",");
            if(cols.length < 2)
            {
            	throw new BadConfigFormatException();
            }
	    }*/
	}
	
}
