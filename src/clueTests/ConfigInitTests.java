package clueTests;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
	public void BadConfigColTest() throws BadConfigFormatException, FileNotFoundException{
		//overload board with bad files
		ClueGame game = new ClueGame("BadBoardLayout.csv", "BoardLegend.txt");
		game.loadRoomConfig();
		game.getBoard().loadBoardConfig();
		
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
	
	}
	@Test
	public void DoorDirectionTest()
	{
		RoomCell door = board.getRoomCellAt(16, 1);
		RoomCell door1 = board.getRoomCellAt(11, 2);
		RoomCell door2 = board.getRoomCellAt(24, 20);
		RoomCell door3 = board.getRoomCellAt(25, 4);
		Assert.assertEquals(RoomCell.DoorDirection.UP, door.getDoorDirection());
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, door1.getDoorDirection());
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, door2.getDoorDirection());
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, door3.getDoorDirection());
		
	}
	@Test
	public void RoomAdjacencyTest()
	{
		RoomCell cellH = board.getRoomCellAt(5, 5);
		RoomCell cellB = board.getRoomCellAt(3, 12);
		RoomCell cellK = board.getRoomCellAt(0, 20);
		RoomCell cellL = board.getRoomCellAt(13, 17);
		LinkedList<BoardCell> testListH= board.getAdjList(cellH);
		LinkedList<BoardCell> testListB= board.getAdjList(cellB);
		LinkedList<BoardCell> testListK= board.getAdjList(cellK);
		LinkedList<BoardCell> testListL= board.getAdjList(cellL);
		Assert.assertEquals(0, testListH.size());
		Assert.assertEquals(0, testListB.size());
		Assert.assertEquals(0, testListK.size());
		Assert.assertEquals(0, testListL.size());
		
	}
	
	@Test
	public void RoomExitAdjacencyTest()
	{
		RoomCell cellOR = board.getRoomCellAt(5, 5);
		RoomCell cellSR = board.getRoomCellAt(3, 12);
		RoomCell cellSU = board.getRoomCellAt(2, 17);
		LinkedList<BoardCell> testListOR= board.getAdjList(cellOR);
		LinkedList<BoardCell> testListSR= board.getAdjList(cellSR);
		LinkedList<BoardCell> testListSU= board.getAdjList(cellSU);
		Assert.assertEquals(1, testListOR.size());
		Assert.assertEquals(1, testListSR.size());
		Assert.assertEquals(1, testListSU.size());
	}
	
	@Test
	public void BesideDoorAdjacency()
	{
		RoomCell cell1 = board.getRoomCellAt(23, 6);
		RoomCell cell2 = board.getRoomCellAt(21, 10);
		RoomCell cell3 = board.getRoomCellAt(18, 16);
		LinkedList<BoardCell> testList1= board.getAdjList(cell1);
		LinkedList<BoardCell> testList2= board.getAdjList(cell2);
		LinkedList<BoardCell> testList3= board.getAdjList(cell3);
		Assert.assertEquals(4, testList1.size());
		Assert.assertEquals(3, testList2.size());
		Assert.assertEquals(4, testList3.size());
	}
	@Test
	public void WalkwayTest()
	{
		RoomCell cell1 = board.getRoomCellAt(11, 5);
		RoomCell cell2 = board.getRoomCellAt(12, 14);
		RoomCell cell3 = board.getRoomCellAt(22, 15);
		RoomCell cell4 = board.getRoomCellAt(18,20);
		
		LinkedList<BoardCell> testList1 = board.getAdjList(cell1);
		LinkedList<BoardCell> testList2 = board.getAdjList(cell2);
		LinkedList<BoardCell> testList3 = board.getAdjList(cell3);

		LinkedList<BoardCell> testList4 = board.getAdjList(cell4);
		
		Assert.assertTrue(testList1.contains(board.getCellAt(10, 5)));
		Assert.assertTrue(testList1.contains(board.getCellAt(11, 6)));
		Assert.assertTrue(testList1.contains(board.getCellAt(12, 5)));
		Assert.assertEquals(3, testList1.size());
		
		Assert.assertTrue(testList2.contains(board.getCellAt(12, 15)));
		Assert.assertTrue(testList2.contains(board.getCellAt(11, 14)));
		Assert.assertTrue(testList2.contains(board.getCellAt(13, 14)));
		Assert.assertEquals(3, testList1.size());
		
		Assert.assertTrue(testList3.contains(board.getCellAt(21, 15)));
		Assert.assertTrue(testList3.contains(board.getCellAt(22, 14)));
		Assert.assertTrue(testList3.contains(board.getCellAt(23, 15)));
		Assert.assertEquals(3, testList1.size());
		
		Assert.assertTrue(testList4.contains(board.getCellAt(19, 20)));
		Assert.assertTrue(testList4.contains(board.getCellAt(18, 19)));
		Assert.assertEquals(2, testList1.size());
	}
	
	
	@Test 
	public void testTargetsIntoRoom()
	{
		
		board.calcTargets(15, 2, 2);
		Set<BoardCell> targets= board.getTargets();
		
		Assert.assertTrue(targets.contains(board.getCellAt(18, 1)));
		Assert.assertTrue(targets.contains(board.getCellAt(15, 0)));
		
		board.calcTargets(15, 2, 3);
		Set<BoardCell> targets1= board.getTargets();
		
		Assert.assertTrue(targets.contains(board.getCellAt(18, 0)));
		Assert.assertTrue(targets.contains(board.getCellAt(14, 0)));
		Assert.assertTrue(targets.contains(board.getCellAt(13, 1)));
		
		
		
	}
	public void testTargetsSixSteps() {
		board.calcTargets(14, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(14, 6)));
		Assert.assertTrue(targets.contains(board.getCellAt(15, 5)));	
		Assert.assertTrue(targets.contains(board.getCellAt(15, 3)));	
	}	
	public void testTargetsThreeSteps() {
		board.calcTargets(6, 20, 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(8, 19)));
		Assert.assertTrue(targets.contains(board.getCellAt(6, 19)));	
		Assert.assertTrue(targets.contains(board.getCellAt(7, 19)));	
	}	
	public void testTargetsOneStep() {
		board.calcTargets(8, 9, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(8, 9)));
		Assert.assertTrue(targets.contains(board.getCellAt(7, 9)));
		Assert.assertTrue(targets.contains(board.getCellAt(7, 9)));			
		Assert.assertTrue(targets.contains(board.getCellAt(7, 9)));
		
		board.calcTargets(8, 14, 1);
		targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(7, 14)));
		Assert.assertTrue(targets.contains(board.getCellAt(9, 14)));	
		Assert.assertTrue(targets.contains(board.getCellAt(8, 13)));
        Assert.assertTrue(targets.contains(board.getCellAt(8, 15)));		
	}
	
	
	@Test
	public void testRoomExit()
	{
		
		board.calcTargets(21, 9, 1);
		Set<BoardCell> targets= board.getTargets();
		
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(21, 10)));
		
		board.calcTargets(22, 12, 2);
		targets= board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(21, 11)));
		Assert.assertTrue(targets.contains(board.getCellAt(21, 13)));
		Assert.assertTrue(targets.contains(board.getCellAt(20, 12)));
	}	
}
