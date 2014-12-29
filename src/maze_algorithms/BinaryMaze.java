package maze_algorithms;

import java.util.ArrayList;

import maze.AbstractMazeGenerator;
import maze.Cell;

public class BinaryMaze extends AbstractMazeGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BinaryMaze(int size_row, int size_col) {
		super("Binary Maze", size_row, size_col);
		init();
		for (int a = 1; a < size_row - 1; a++)
			this.maze[a][1].value = PATH;
	}

	public ArrayList<Integer> getDirs(int x, int y) {
		ArrayList<Integer> arr = new ArrayList<Integer>(2);
		if (x - 1 > 2)
			arr.add(LEFT);
		if (y - 1 > 1)
			arr.add(UP);
		arr.trimToSize();
		return arr;

	}

	/**
	 * 
	 */
	@Override
	public Cell[][] generate() {
		// We start at the bottom left corner and move left and up. This is why
		// we choose the coordinate
		// (maze[0].length - 2, maze.length -2). Keep in mind that our grid
		// already has a border.
		// We must remain in the bounds of this border. Hence the constraint
		// x>0. However, the constraint
		// y > 1 is to keep the top row of the maze clear.
		// We move across the maze by units of 2 to skip the walls of the maze.
		// Remember that the maze is
		// initialized with a grid, "# # #" running across and "#####" for the
		// next row, this pattern repeating.
		for (int y = maze[0].length - 2; y > 1; y -= 2) {
			for (int x = maze.length - 2; x > 0; x -= 2) {
				System.out.println("(" + x + "," + y + ")");
				ArrayList<Integer> dirs = getDirs(x, y);
				if (dirs.size() == 0)
					continue;
				int dir = dirs.get(rand(dirs.size()));

				System.out.println("Dir:" + dir);

				if (dir == LEFT)
					get(x - 1, y).value = PATH;
				else if (dir == UP)
					get(x, y - 1).value = PATH;
				display();
			}
		}
		display();
		return maze;
	}
}
