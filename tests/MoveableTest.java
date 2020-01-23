
package tests;
import game.*;

//author Jon

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MoveableTest {

	private Board gameBoard;
	private Fox fox;
	private Fox fox2;
	private Rabbit rabbit;
	private Rabbit rabbit2;
	
	@Before
	public void setUp() {
		gameBoard = new Board();
		gameBoard.setDefaultBoard();
		rabbit = (Rabbit) gameBoard.getSpot(3, 4).getPiece();
		rabbit2 = (Rabbit) gameBoard.getSpot(1, 0).getPiece();
		fox = (Fox) gameBoard.getSpot(1, 3).getPiece();
		fox2 = (Fox) gameBoard.getSpot(4, 1).getPiece();
	}
	
	protected void tearDown() {
		
	}
	
	
	@Test
	public void moveRabbitOverEmptySpot() {
		gameBoard.takeTurn(3,4,3,2);
		assertEquals(rabbit, (Rabbit) gameBoard.getSpot(3, 2).getPiece());
	}	
	
	@Test
	public void moveRabbitOverOccupiedSpot() {
		Mushroom mushroom = (Mushroom) gameBoard.getSpot(3, 3).getPiece();
		gameBoard.takeTurn(3,4,3,3);
		assertEquals(mushroom, (Mushroom) gameBoard.getSpot(3, 3).getPiece());
		assertEquals(rabbit, (Rabbit) gameBoard.getSpot(3, 4).getPiece());
	}
	@Test
	public void moveRabbitIntoEmptySpot() {
		gameBoard.takeTurn(3,4,2,4);
		assertNotEquals(rabbit, (Rabbit) gameBoard.getSpot(2, 4).getPiece());
		assertEquals(rabbit, (Rabbit) gameBoard.getSpot(3, 4).getPiece());
	}
	
	
	@Test
	public void moveRabbitIntoAHole() {
		assertFalse(((Hole) gameBoard.getSpot(2, 2)).isFull());
		gameBoard.takeTurn(3,4,3,2);
		gameBoard.takeTurn(4,2,2,2);
		assertTrue(((Hole) gameBoard.getSpot(2, 2)).isFull());
	}	
	
	@Test
	public void moveRabbitOverTwoPieces() {
		//rabbit can jump over two pieces
		gameBoard.takeTurn(3,4,3,2);
		gameBoard.takeTurn(3,2,3,0);
		gameBoard.takeTurn(1,0,4,0);
		assertEquals(rabbit2, (Rabbit) gameBoard.getSpot(4, 0).getPiece());
	}	
	@Test
	public void moveRabbitDiagonal() {
		gameBoard.takeTurn(3,4,2,3);
		assertNotEquals(rabbit, (Rabbit) gameBoard.getSpot(2, 3).getPiece());
		assertEquals(rabbit, (Rabbit) gameBoard.getSpot(3, 4).getPiece());
		
	}
	
	@Test
	public void moveRabbitBackAndForth() {
		gameBoard.takeTurn(3,4,3,2);
		gameBoard.takeTurn(3,2,3,4);
		gameBoard.takeTurn(3,4,3,2);
		gameBoard.takeTurn(3,2,3,0);
		assertEquals(rabbit, (Rabbit) gameBoard.getSpot(3, 0).getPiece());	
	}
	
	public void testFoxHasTail() {
		assertEquals(((Fox) gameBoard.getSpot(4, 1).getPiece()),fox.getPair());
		assertNotNull(((Fox) gameBoard.getSpot(4, 1).getPiece()).getPair());
	}
	
	
	@Test
	public void moveFoxOntoTail() {
		gameBoard.takeTurn(4,1,3,1);
		assertEquals(fox2, (Fox) gameBoard.getSpot(3, 1).getPiece());
		assertEquals(fox2.getPair(), (Fox) gameBoard.getSpot(2, 1).getPiece());
	}
	@Test
	public void moveFoxOntoAnotherPiece() {
		gameBoard.takeTurn(1,3,1,0);
		assertEquals(fox, (Fox) gameBoard.getSpot(1, 1).getPiece());
	}	
	@Test
	public void moveFoxInDiffrentDirection() {
		gameBoard.takeTurn(1,3,2,3);
		assertEquals(fox, (Fox) gameBoard.getSpot(1, 3).getPiece());
	}
	
	@Test
	public void moveFoxInDiagonal() {
		gameBoard.takeTurn(1,3,2,4);
		assertEquals(fox, (Fox) gameBoard.getSpot(1, 3).getPiece());
	}
	
	
	@Test
	public void moveRabbitOverPieceAndEmptySpot() {
		gameBoard.takeTurn(1,3,1,1);
		gameBoard.takeTurn(1,0,1,4);
		assertEquals(rabbit2, (Rabbit) gameBoard.getSpot(1, 0).getPiece());
	}
}
