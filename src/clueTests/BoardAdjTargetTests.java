package clueTests;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ClueGame;
import clueGame.RoomCell;

public class BoardAdjTargetTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		ClueGame game = new ClueGame("BoardLayout.csv", "BoardLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();
	}
	@Test
	public void RoomAdjacencyTest()
	{
		LinkedList<BoardCell> testListH= board.getAdjList(5,5);
		LinkedList<BoardCell> testListB= board.getAdjList(3,12);
		LinkedList<BoardCell> testListK= board.getAdjList(2,17);
		LinkedList<BoardCell> testListL= board.getAdjList(13,17);
		Assert.assertEquals(0, testListH.size());
		Assert.assertEquals(0, testListB.size());
		Assert.assertEquals(0, testListK.size());
		Assert.assertEquals(0, testListL.size());
		
	}
	
	@Test
	public void RoomExitAdjacencyTest()
	{
		LinkedList<BoardCell> testListOR= board.getAdjList(24, 5);
		LinkedList<BoardCell> testListSR= board.getAdjList(21, 9);
		LinkedList<BoardCell> testListSU= board.getAdjList(22, 12);
		Assert.assertEquals(1, testListOR.size());
		Assert.assertEquals(1, testListSR.size());
		Assert.assertEquals(1, testListSU.size());
	}
	
	@Test
	public void BesideDoorAdjacency()
	{
		LinkedList<BoardCell> testList1= board.getAdjList(23, 6);
		LinkedList<BoardCell> testList2= board.getAdjList(21, 10);
		LinkedList<BoardCell> testList3= board.getAdjList(18, 16);
		Assert.assertEquals(4, testList1.size());
		Assert.assertEquals(3, testList2.size());
		Assert.assertEquals(4, testList3.size());
	}
	@Test
	public void EdgeOfBoardAdjacency()
	{
		LinkedList<BoardCell> testList1= board.getAdjList(0, 0);
		LinkedList<BoardCell> testList2= board.getAdjList(28, 0);
		LinkedList<BoardCell> testList3= board.getAdjList(0, 20);
		LinkedList<BoardCell> testList4= board.getAdjList(28, 20);
		Assert.assertEquals(0, testList1.size());
		Assert.assertEquals(0, testList2.size());
		Assert.assertEquals(0, testList3.size());
		Assert.assertEquals(2, testList4.size());
	}
	@Test
	public void WalkwayAdjacencyTest()
	{	
		LinkedList<BoardCell> testList1 = board.getAdjList(10, 5);
		LinkedList<BoardCell> testList2 = board.getAdjList(12, 14);
		LinkedList<BoardCell> testList3 = board.getAdjList(22, 15);
		LinkedList<BoardCell> testList4 = board.getAdjList(18, 20);
		
		Assert.assertTrue(testList1.contains(board.getCellAt(9, 5)));
		Assert.assertTrue(testList1.contains(board.getCellAt(10, 6)));
		Assert.assertTrue(testList1.contains(board.getCellAt(11, 5)));
		Assert.assertTrue(testList1.contains(board.getCellAt(10, 4)));
		Assert.assertEquals(4, testList1.size());
		
		Assert.assertTrue(testList2.contains(board.getCellAt(12, 15)));
		Assert.assertTrue(testList2.contains(board.getCellAt(11, 14)));
		Assert.assertTrue(testList2.contains(board.getCellAt(13, 14)));
		Assert.assertEquals(3, testList2.size());
		
		Assert.assertTrue(testList3.contains(board.getCellAt(21, 15)));
		Assert.assertTrue(testList3.contains(board.getCellAt(22, 14)));
		Assert.assertTrue(testList3.contains(board.getCellAt(23, 15)));
		Assert.assertEquals(3, testList3.size());
		
		Assert.assertTrue(testList4.contains(board.getCellAt(19, 20)));
		Assert.assertTrue(testList4.contains(board.getCellAt(18, 19)));
		Assert.assertEquals(2, testList4.size());
	}
	
	
	@Test 
	public void testTargetsIntoRoom()
	{
		board.calcTargets(15, 2, 2);
		Set<BoardCell> targets= board.getTargets();
		System.out.println("before fail");
		Assert.assertTrue(targets.contains(board.getCellAt(16, 1)));
		

		
		board.calcTargets(15, 2, 3);
		Set<BoardCell> targets1= board.getTargets();
		
		Assert.assertTrue(targets1.contains(board.getCellAt(16, 1)));
		Assert.assertTrue(targets1.contains(board.getCellAt(16, 0)));
	}
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(14, 0, 6);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(11, 3)));
		Assert.assertTrue(targets.contains(board.getCellAt(17, 3)));	
		Assert.assertTrue(targets.contains(board.getCellAt(11, 1)));	
	}	
	@Test
	public void testTargetsThreeSteps() {
		board.calcTargets(6, 20, 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(8, 19)));
		Assert.assertTrue(targets.contains(board.getCellAt(6, 17)));	
		Assert.assertTrue(targets.contains(board.getCellAt(5, 18)));	
	}	
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(8, 9, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(7, 9)));
		Assert.assertTrue(targets.contains(board.getCellAt(9, 9)));
		Assert.assertTrue(targets.contains(board.getCellAt(8, 8)));			
		Assert.assertTrue(targets.contains(board.getCellAt(8, 10)));
		
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
