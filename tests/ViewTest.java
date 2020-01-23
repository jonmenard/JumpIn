package tests;
//author Jon
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import game.*;



public class ViewTest {

	View view;
	
	@Before
	public void setUp() throws Exception {
		view = new View(new Board());
	}

	@Test
	public void testGetButton() {
		assertTrue(view.getButton(1, 3).isVisible());
	}

}
