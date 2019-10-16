package tests;

import static org.junit.Assert.*;
import game.Application;

import org.junit.Test;

public class RevivingTest {

	@Test
	public void test() {
		Application app = Application.getInstance();
		app.m.addPoint(0, 2);
		app.m.addPoint(1, 1);
		app.m.addPoint(2, 0);
		app.m.addPoint(2, 2);
		app.update();
		assertEquals("cell 1 did not revive", 1, app.m.getPoint(1, 2));
		assertEquals("cell 2 did not revive", 1, app.m.getPoint(2, 1));
		app.m.reset();
	}

}
