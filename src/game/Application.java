package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ConcurrentModificationException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * 
 * @author Codreanu Andrei-Marian 323CB
 * 
 */
public class Application extends JFrame implements ActionListener {

	private static final long serialVersionUID = -1846451947543068298L;
	public Matrix m;
	int BLOCK_SIZE = 10; // the size of a single cell.
	private int fps = 225; // the milliseconds between each frame. (can be
							// adjusted in-game)
	private static Application instance = null;

	// Several Java Swing items necessary for the graphic interface.
	private JMenuBar menu;
	private JMenu options;
	private JMenuItem pause, reset, speed, autofill, grid, borderless, exit;
	private boolean isPaused = false; // pause or resume the game, can be
										// adjusted in-game.
	private boolean showGrid = true; // toggle the grid, can be adjusted
										// in-game.
	public boolean border = true; // make the game be borderless or not, can be
									// adjusted in-game

	private Application() {
		m = new Matrix(40, 40);
		JFrame game = newGame();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setTitle("Game of Life");
		game.setSize(new Dimension(BLOCK_SIZE * 40 + 30, BLOCK_SIZE * 40 + 75));
		game.setMinimumSize(new Dimension(100, 100));
		game.setVisible(true);
	}

	public static Application getInstance() {
		if (instance == null) {
			instance = new Application();
		}
		return instance;
	}

	/**
	 * Simulates the Game Of Life.
	 * 
	 * @throws InterruptedException
	 */
	public void simulate() throws InterruptedException {
		while (true) {
			while (isPaused == true)
				Thread.sleep(100);
			long now = System.currentTimeMillis();
			update();
			m.repaint();
			Thread.sleep(now + fps - System.currentTimeMillis());
		}
	}

	/**
	 * Creates a window which is used to graphically represent the behavior of
	 * the cells. Also, an option bar is added with several commands to control
	 * the game for more flexibility.
	 * 
	 * @return The newly created JFrame.
	 */
	public JFrame newGame() {
		menu = new JMenuBar();
		setJMenuBar(menu);
		options = new JMenu("Options");
		menu.add(options);
		exit = new JMenuItem("Exit");
		pause = new JMenuItem("Pause");
		speed = new JMenuItem("Speed");
		grid = new JMenuItem("Toggle Grid");
		borderless = new JMenuItem("Toggle Border");
		reset = new JMenuItem("Reset");
		autofill = new JMenuItem("AutoFill");
		options.add(pause);
		options.addSeparator();
		options.add(speed);
		options.addSeparator();
		options.add(autofill);
		options.addSeparator();
		options.add(grid);
		options.addSeparator();
		options.add(borderless);
		options.addSeparator();
		options.add(reset);
		options.addSeparator();
		options.add(exit);
		this.add(m);
		pause.addActionListener(this);
		exit.addActionListener(this);
		autofill.addActionListener(this);
		speed.addActionListener(this);
		borderless.addActionListener(this);
		grid.addActionListener(this);
		reset.addActionListener(this);
		return this;
	}

	/**
	 * Computes an entire frame according to the given rule.
	 */
	public void update() {

		if (border == false) {
			for (int i = 0; i < m.mat.length; i++)
				for (int j = 0; j < m.mat[0].length; j++) {
					if (i == m.mat.length - 1 || j == m.mat[0].length - 1) {
						if (m.getPoint(i, j) == 1) {
							int[][] z = new int[m.mat.length * 2][m.mat[0].length * 2];
							for (int i2 = 0; i2 < m.mat.length; i2++)
								for (int j2 = 0; j2 < m.mat[0].length; j2++)
									z[i2][j2] = m.getPoint(i2, j2);
							i = m.mat.length;
							j = m.mat[0].length;
							m.mat = z;
							BLOCK_SIZE = BLOCK_SIZE / 2 + 1;
						}
					}
					if (i == 0 || j == 0) {
						if (m.getPoint(i, j) == 1) {
							int[][] z = new int[m.mat.length * 2][m.mat[0].length * 2];
							for (int i2 = 0; i2 < m.mat.length; i2++)
								for (int j2 = 0; j2 < m.mat[0].length; j2++)
									z[m.mat.length + i2][m.mat[0].length + j2] = m
											.getPoint(i2, j2);
							i = m.mat.length;
							j = m.mat[0].length;
							m.mat = z;
							BLOCK_SIZE = BLOCK_SIZE / 2 + 1;
						}
					}
				}
		}

		int[][] x = new int[m.mat.length][m.mat[0].length];
		for (int i = 0; i < m.mat.length; i++)
			for (int j = 0; j < m.mat[0].length; j++)
				x[i][j] = m.getPoint(i, j);

		for (int i = 0; i < m.mat.length; i++)
			for (int j = 0; j < m.mat[0].length; j++) {
				int n = m.nrVecini(i, j);
				if (n < 2)
					x[i][j] = 0;
				if (n > 3)
					x[i][j] = 0;
				if (n == 3)
					x[i][j] = 1;
			}
		m.mat = x;
	}

	/**
	 * A matrix that represents each cell with the value 0 or 1, dead or alive
	 * in a given grid.
	 * 
	 * @author Codreanu Andrei-Marian 323CB
	 * 
	 */
	public class Matrix extends JPanel implements MouseListener,
			MouseMotionListener {

		private static final long serialVersionUID = 4466372802774513395L;
		private int[][] mat;

		private Matrix(int n, int m) {
			mat = new int[n][m];
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		/**
		 * @return The number of cells in the matrix.
		 */
		public int getMatrixLenght() {
			return m.mat.length;
		}

		/**
		 * @return The number of columns in the matrix.
		 */
		public int getMatrixWidth() {
			return m.mat[0].length;
		}

		/**
		 * Returns the value of the cell at (x,y) in the grid.
		 * 
		 * @param y
		 * @param x
		 * @return 0 or 1
		 */
		public int getPoint(int x, int y) {
			return mat[x][y];
		}

		/**
		 * Adds the value of 1 to the location (x,y) in the grid.
		 * 
		 * @param y
		 * @param x
		 */
		public void addPoint(int x, int y) {
			mat[x][y] = 1;
		}

		/**
		 * Adds the value of 1 to the position of the mouse over the grid.
		 * 
		 * @param e
		 */
		public void addPoint(MouseEvent e) {
			int x = e.getPoint().x / BLOCK_SIZE - 1;
			int y = e.getPoint().y / BLOCK_SIZE - 1;
			addPoint(x, y);
			repaint();
		}

		/**
		 * Resets the grid, killing all cells (value 0).
		 */
		public void reset() {
			for (int i = 0; i < mat.length; i++)
				for (int j = 0; j < mat[0].length; j++)
					mat[i][j] = 0;
			repaint();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			try {
				for (int i = 0; i < m.mat.length; i++)
					for (int j = 0; j < m.mat[0].length; j++) {
						if (m.getPoint(i, j) == 1) {
							// Draw new point
							g.setColor(Color.red);
							g.fillRect(BLOCK_SIZE + (BLOCK_SIZE * i),
									BLOCK_SIZE + (BLOCK_SIZE * j), BLOCK_SIZE,
									BLOCK_SIZE);
						}
					}
			} catch (ConcurrentModificationException cme) {
			}
			// Setup grid
			if (showGrid == true) {
				g.setColor(Color.BLUE);
				for (int i = 0; i <= m.mat.length; i++) {
					g.drawLine(((i * BLOCK_SIZE) + BLOCK_SIZE), BLOCK_SIZE,
							(i * BLOCK_SIZE) + BLOCK_SIZE, BLOCK_SIZE
									+ (BLOCK_SIZE * m.mat[0].length));
				}
				for (int i = 0; i <= m.mat[0].length; i++) {
					g.drawLine(BLOCK_SIZE, ((i * BLOCK_SIZE) + BLOCK_SIZE),
							BLOCK_SIZE * (m.mat.length + 1),
							((i * BLOCK_SIZE) + BLOCK_SIZE));
				}
			}
		}

		/**
		 * Returns the number of neighbors for the cell at position (j,i).
		 * 
		 * @param i
		 *            -> y
		 * @param j
		 *            -> x
		 * @return the number of neighbor cells.
		 */
		public int nrVecini(int i, int j) {
			int nr = 0;
			if (i == mat.length - 1) {
				if (j == mat[0].length - 1) {
					if (mat[i - 1][j] == 1)
						nr++;
					if (mat[i - 1][j - 1] == 1)
						nr++;
					if (mat[i][j - 1] == 1)
						nr++;
					return nr;
				}
				if (j == 0) {
					if (mat[i - 1][j] == 1)
						nr++;
					if (mat[i - 1][j + 1] == 1)
						nr++;
					if (mat[i][j + 1] == 1)
						nr++;
					return nr;
				}
				if (mat[i][j + 1] == 1)
					nr++;
				if (mat[i - 1][j + 1] == 1)
					nr++;
				if (mat[i - 1][j] == 1)
					nr++;
				if (mat[i - 1][j - 1] == 1)
					nr++;
				if (mat[i][j - 1] == 1)
					nr++;
				return nr;
			}
			if (i == 0) {
				if (j == mat[0].length - 1) {
					if (mat[i + 1][j] == 1)
						nr++;
					if (mat[i + 1][j - 1] == 1)
						nr++;
					if (mat[i][j - 1] == 1)
						nr++;
					return nr;
				}
				if (j == 0) {
					if (mat[i + 1][j] == 1)
						nr++;
					if (mat[i + 1][j + 1] == 1)
						nr++;
					if (mat[i][j + 1] == 1)
						nr++;
					return nr;
				}
				if (mat[i + 1][j] == 1)
					nr++;
				if (mat[i + 1][j + 1] == 1)
					nr++;
				if (mat[i][j + 1] == 1)
					nr++;
				if (mat[i + 1][j - 1] == 1)
					nr++;
				if (mat[i][j - 1] == 1)
					nr++;
				return nr;
			}
			if (j == 0) {
				if (mat[i + 1][j] == 1)
					nr++;
				if (mat[i + 1][j + 1] == 1)
					nr++;
				if (mat[i][j + 1] == 1)
					nr++;
				if (mat[i - 1][j + 1] == 1)
					nr++;
				if (mat[i - 1][j] == 1)
					nr++;
				return nr;
			}
			if (j == mat[0].length - 1) {

				if (mat[i + 1][j] == 1)
					nr++;
				if (mat[i - 1][j] == 1)
					nr++;
				if (mat[i - 1][j - 1] == 1)
					nr++;
				if (mat[i][j - 1] == 1)
					nr++;
				if (mat[i + 1][j - 1] == 1)
					nr++;
				return nr;
			}

			if (mat[i + 1][j] == 1)
				nr++;
			if (mat[i + 1][j + 1] == 1)
				nr++;
			if (mat[i][j + 1] == 1)
				nr++;
			if (mat[i - 1][j + 1] == 1)
				nr++;
			if (mat[i - 1][j] == 1)
				nr++;
			if (mat[i - 1][j - 1] == 1)
				nr++;
			if (mat[i][j - 1] == 1)
				nr++;
			if (mat[i + 1][j - 1] == 1)
				nr++;
			return nr;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			addPoint(e);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			addPoint(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		/**
		 * Fills the grid randomly.
		 * 
		 * @param n
		 *            The percentage of grid to be filled.
		 */
		public void fillRandomly(int n) {
			for (int i = 0; i < mat.length; i++)
				for (int j = 0; j < mat[0].length; j++) {
					if (Math.random() * 100 < n) {
						addPoint(i, j);
					}
				}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(exit))
			System.exit(0);
		if (e.getSource().equals(pause))
			if (isPaused == false) {
				isPaused = true;
				pause.setText("Unpause");
			} else {
				isPaused = false;
				pause.setText("Pause");
			}
		if (e.getSource().equals(reset)) {
			m.reset();
		}
		if (e.getSource().equals(speed)) {
			final JFrame window = new JFrame();
			window.setTitle("Speed");
			window.setSize(300, 60);
			window.setResizable(false);
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			window.add(panel);
			panel.add(new JLabel("Frames per second:"));
			Integer[] secOptions = { 1, 5, 10, 15, 20, 25, 30 };
			final JComboBox cbSeconds = new JComboBox(secOptions);
			panel.add(cbSeconds);
			cbSeconds.setSelectedItem(fps);
			cbSeconds.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					fps = 1000 / (Integer) cbSeconds.getSelectedItem();
					window.dispose();
				}
			});
			window.setVisible(true);
		}
		if (e.getSource().equals(autofill)) {
			final JFrame window = new JFrame();
			window.setTitle("Autofill");
			window.setSize(300, 60);
			window.setResizable(false);
			JPanel panel = new JPanel();
			panel.setOpaque(false);
			window.add(panel);
			panel.add(new JLabel("Percentage?"));
			Object[] options = { "Select", 5, 10, 15, 20, 25, 30, 35, 40, 45,
					50, 55, 60, 65, 70, 75, 80, 85, 90, 95 };
			final JComboBox cbPercent = new JComboBox(options);
			panel.add(cbPercent);
			cbPercent.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (cbPercent.getSelectedIndex() > 0) {
						m.reset();
						m.fillRandomly((Integer) cbPercent.getSelectedItem());
						window.dispose();
					}

				}
			});
			window.setVisible(true);
		}
		if (e.getSource().equals(grid)) {
			if (showGrid == true)
				showGrid = false;
			else
				showGrid = true;
		}
		if (e.getSource().equals(borderless)) {
			if (border == true)
				border = false;
			else
				border = true;
		}
	}

}
