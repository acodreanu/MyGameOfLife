package tests;

import static org.junit.Assert.*;
import game.Application;

import org.junit.BeforeClass;
import org.junit.Test;

public class SurvivingTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		Application app=Application.getInstance();
		app.m.addPoint(0, 0);
		app.m.addPoint(0, 1);
		app.m.addPoint(1, 1);
		app.update();
		assertEquals("It didn't survive with 2 neighbours", 1,app.m.getPoint(1, 1));
		app.m.reset();
		app.m.addPoint(1, 0);
		app.m.addPoint(1, 1);
		app.m.addPoint(1, 2);
		app.m.addPoint(2, 1);
		app.update();
		assertEquals("It didn't survive with 3 neighbours", 1,app.m.getPoint(1, 1));
		app.m.reset();
	}
	

}
