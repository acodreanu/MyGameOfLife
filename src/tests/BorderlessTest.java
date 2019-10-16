package tests;

import static org.junit.Assert.*;
import game.Application;
import game.Application.Matrix;

import org.junit.Test;

public class BorderlessTest {

	@Test
	public void test() throws InterruptedException {
		Application app = Application.getInstance();
		app.m.addPoint(5, 99);
		app.border = false;
		app.update();
		if(app.m.getMatrixLenght() != 200 && app.m.getMatrixWidth() != 200)
			fail("Matrix size is not doubled");
		app.m.reset();
	}

}
