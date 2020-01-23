package tests;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.*;

public class PastMovesTest {

	PastMoves pm;
	int[] array;
	
	
	@Before
	public void setUp() throws Exception {
		pm = new PastMoves();
		pm.saveTurn(0, 0, 0, 0);
		
	}

	@Test
	public void testSaveTurn() {
		assertTrue(pm.canUndo());
	}

	@Test
	public void testRedo() {
		int[] a = {0,0,0,0};
		pm.undo();
		int[] b = pm.redo();
		assertTrue(java.util.Arrays.equals(a,b));
	}

	@Test
	public void testUndo() {
		int[] a = {0,0,0,0};
		int[] b = pm.undo();
		assertTrue(java.util.Arrays.equals(a,b));
		
	}

	@Test
	public void testCanUndo() {
		assertTrue(pm.canUndo());
	}

	@Test
	public void testCanRedo() {
		assertFalse(pm.canRedo());
	}
	
	@Test
	public void canSaveAfterUndo() {
		
		for(int i = 1; i < 10; i++) {
			pm.saveTurn(0,0,0,i);
			if(i == 4) pm.undo();
		}
		int[] a = {0,0,0,9};
		int[] b = pm.undo();
		assertTrue(java.util.Arrays.equals(a,b));
		
	}
	
	
	
	

}
