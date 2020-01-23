package tests;
import game.*;

//author Jon
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;


public class BoardTest {

	public Board board;
	public Tile tile;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.setDefaultBoard();
		tile = new Tile();
	}

	@Test
	public void testGetSpot() {
		assertEquals(board.getGrid()[3][0],board.getSpot(3, 0));
	}

	@Test
	public void testTakeTurn() {
		Fox fox = (Fox) board.getSpot(1, 3).getPiece();
		board.takeTurn(1, 3, 1, 1);
		assertEquals(fox,(Fox)board.getSpot(1, 1).getPiece());
	}

	@Test
	public void testGetStatus() {
		assertTrue(board.getStatus());
	}
	
	@Test
	public void gameCanBeWon() {
		assertTrue(board.getStatus());
		board.takeTurn(3,4,3,2);
		board.takeTurn(4,2,2,2);
		board.takeTurn(3,2,3,0);
		board.takeTurn(1,0,4,0);
		board.takeTurn(1,3,1,0);
		board.takeTurn(3,0,0,0);
		assertFalse(board.getStatus());
	}

	@Test
	public void testSolverHappyPath() {
		board.solve();
		Stack<Tile[][]> solution = board.solution;
		board.takeTurn(3,4,3,2);
		assertEquals(new Board(solution.pop()).toString() ,board.toString());
		board.takeTurn(4,2,2,2);
		assertEquals(new Board(solution.pop()).toString() ,board.toString());
		board.takeTurn(3,2,3,0);
		assertEquals(new Board(solution.pop()).toString() ,board.toString());
		board.takeTurn(1,0,4,0);
		assertEquals(new Board(solution.pop()).toString() ,board.toString());
		board.takeTurn(1,3,1,0);
		assertEquals(new Board(solution.pop()).toString() ,board.toString());
		board.takeTurn(3,0,0,0);
		assertEquals(new Board(solution.pop()).toString() ,board.toString());
		assertFalse(board.getStatus());
		assertTrue(board.solution.empty());
	}
	
	/*
	@Test
	public void testSolveReturnsNullIfUnsolvable() {
		board.takeTurn(4,2,4,0);
		board.solve();
		assertTrue(board.solution.empty());
	}
	*/
}
