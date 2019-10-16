package tests;

import static org.junit.Assert.*;
import game.Application;

import org.junit.BeforeClass;
import org.junit.Test;

public class UnderpopulationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		Application app=Application.getInstance();
		app.m.addPoint(0,1);
		app.m.addPoint(0,2);
		app.m.addPoint(1,1);
		app.m.addPoint(2,1);
		app.update();
		assertEquals("It survived with only one neighbour", 0, app.m.getPoint(2, 1));
		app.m.reset();
	}

}
