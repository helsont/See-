package maze;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import maze.AbstractMazeGenerator.CellValues;
import algorithm_base.AlgorithmComponent;

public class MazeComponent extends JComponent implements AlgorithmComponent {
	private Cell[][] maze;
	private static final long serialVersionUID = 1L;
	
	public synchronized void paintComponent(Graphics g) {
		if (maze == null) {
			return;
		}
		int width = (int) (getWidth() * .87 / maze.length);
		width = Math.min(width, getHeight() / maze.length);
		int cY = getHeight() / 2 - maze.length * width / 2;
		int cX = getWidth() / 2 - maze.length * width / 2;
		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[0].length; y++) {
				g.setColor(Color.black);
				g.drawRect(cX + x * width, cY + y * width, width, width);
				if (maze[x][y].value == Cell.WALL)
					g.fillRect(cX + x * width, cY + y * width, width, width);
				g.setColor(Color.red);
				//g.drawString(x + "," + y, cX + x * width + width / 2, cY + y
				//		* width + width / 2);
			}
		}
	}

	/**
	 * Sets the values to be painted.
	 * 
	 * @param Object
	 *            A container for values.
	 */
	@Override
	public synchronized void setValues(Object vals) {
		this.maze = ((CellValues) vals).maze;
		repaint();
	}
}
