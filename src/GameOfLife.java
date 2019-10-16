import game.Application;

import javax.swing.JFrame;

public class GameOfLife extends JFrame {

	private static final long serialVersionUID = 6659425454923937343L;

	public static void main(String[] args) throws InterruptedException {
		Application app = Application.getInstance();
		app.simulate();
	}
}
