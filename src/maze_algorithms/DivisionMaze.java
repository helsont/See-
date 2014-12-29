package maze_algorithms;

import java.util.Random;

import maze.AbstractMazeGenerator;
import maze.Cell;

public class DivisionMaze extends AbstractMazeGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3148701915929037434L;

	public DivisionMaze(int size_row, int size_col) {
		super("Recursive Division Maze", size_row, size_col);

		// Creating outer edges
		for (int a = 0; a < size_col; a++)
			for (int b = 0; b < size_row; b++)
				if (a == 0 || a == size_col - 1 || b == 0 || b == size_row - 1)
					this.maze[a][b].value = WALL;

	}

	public void recurse(int x1, int y1, int x2, int y2) {
		if (x1 + 2 == x2 && y1 + 2 == y2)
			return;
		display();
		int horizontal = getDivide(x1 + 2, x2 - 2);
		int vertical = getDivide(y1 + 2, y2 - 2);

		System.out.println("Horizontal:" + horizontal);
		System.out.println("Vertical:" + vertical);
		if (horizontal == -1 || vertical == -1)
			return;
		horizontalWall(horizontal);
		display();
		verticalWall(vertical);
		display();
		// Poke a hole in the wall
		int x = getRand(x1 + 1, x2 - 1);
		while (x == vertical)
			x = getRand(x1 + 1, x2 - 1);

		int y = getRand(y1 + 1, y2 - 1);
		while (y == vertical || y == x)
			y = getRand(y1 + 1, y2 - 1);

		maze[vertical][x].value = PATH;
		maze[y][horizontal].value = PATH;
		display();
		// Top left
		recurse(x1, y1, horizontal, vertical);
		// Top Right
		recurse(horizontal, y1, x2, vertical);
		// Bottom Left
		recurse(x1, vertical, horizontal, y2);
		// Bottom Right
		recurse(horizontal, vertical, x2, y2);

		display();
	}

	public void horizontalWall(int column_num) {
		for (int i = 0; i < size_row; i++)
			maze[i][column_num].value = Cell.WALL;

	}

	public void verticalWall(int row_num) {
		for (int i = 0; i < size_col; i++)
			maze[row_num][i].value = Cell.WALL;

	}

	private int getDivide(int y1, int y2) {
		if (y2 < y1)
			return -1;
		int r;
		while ((r = y1 + (new Random()).nextInt(y2)) % 2 != 0) {

		}
		return r;

	}

	@Override
	public Cell[][] generate() {
		recurse(0, 0, size_row - 1, size_col - 1);
		return maze;
	}
}
