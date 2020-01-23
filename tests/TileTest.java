package tests;
// author Jon
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.*;


public class TileTest {

	Tile tile;
	Hole hole;
	Rabbit rabbit;
	
	
	
	@Before
	public void setUp() throws Exception {
	
		tile = new Tile();
		hole = new Hole();
		rabbit = new Rabbit();
		hole.occupy(rabbit);
	}

	
	@Test
	public void testIsNotOccupied() {
		assertFalse(tile.isOccupied());
	}

	@Test
	public void testIsOccupied() {
		assertTrue(hole.isOccupied());
	}
	
	@Test
	public void testOccupy() {
		tile.occupy(rabbit);
		assertTrue(tile.isOccupied());
	}

	@Test
	public void testUnoccupy() {
		hole.unoccupy();
		assertFalse(tile.isOccupied());
	}

	
	@Test
	public void testMovePiece() {
		hole.movePiece(tile);
		assertEquals(rabbit,tile.getPiece());
		assertFalse(hole.isOccupied());
		assertTrue(tile.isOccupied());
	}

	@Test
	public void testGetPiece() {
		assertEquals(rabbit, hole.getPiece());
	}
	
	
	public void testFillHole() {
		hole.fillHole();
		assertTrue(hole.isFull());
	}


}
