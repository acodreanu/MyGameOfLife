package tests;

import static org.junit.Assert.*;

import java.util.Random;

import game.Application;

import org.junit.Test;

public class ResetTest {

	@Test
	public void test() {
		Application app = Application.getInstance();
		Random r = new Random();
		for (int i = 0; i < app.m.getMatrixLenght() - 1; i++)
			for (int j = 0; j < app.m.getMatrixWidth() - 1; j++)
				if (r.nextInt(2) == 1)
					app.m.addPoint(i, j);
		app.m.reset();
		for (int i = 0; i < app.m.getMatrixLenght() - 1; i++)
			for (int j = 0; j < app.m.getMatrixWidth() - 1; j++)
				if (app.m.getPoint(i, j) == 1)
					fail("Exista o celula in viata");
	}

}
