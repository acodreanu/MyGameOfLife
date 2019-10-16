package tests;

import static org.junit.Assert.*;
import game.Application;

import org.junit.BeforeClass;
import org.junit.Test;

public class OverpopulationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		Application app=Application.getInstance();
		app.m.addPoint(0, 0);
		app.m.addPoint(0, 1);
		app.m.addPoint(0, 2);
		app.m.addPoint(1, 0);
		app.m.addPoint(1, 1);
		app.update();
		assertEquals("It survived with more than 3 neighbours", 0,app.m.getPoint(1, 1));
		app.m.reset();
	}

}
