package com.winter.algorithms.maze.algorithms;

import com.winter.algorithms.maze.AbstractMazeGenerator;
import com.winter.algorithms.maze.Cell;

public class DivisionMaze extends AbstractMazeGenerator {

	public Settings display;

	public DivisionMaze(int size_row, int size_col) {
		super("Division Maze", size_row, size_col);
		init();
	}

	@Override
	public void init() {
		// Creating outer edges only.
		for (int a = 0; a < size_row; a++)
			for (int b = 0; b < size_col; b++)
				if (a == 0 || a == size_row - 1 || b == 0 || b == size_col - 1)
					this.maze[a][b].value = WALL;
				else
					maze[a][b].value = PATH;
	}

	public void recurse(int x1, int y1, int x2, int y2) {
		if (x1 + 2 == x2 && y1 + 2 == y2)
			return;
		display = new Settings(x1, y1, x2, y2);
		hang.hang(null);
		int horizontal = getDivide(y1 + 2, y2 - 2);
		int vertical = getDivide(x1 + 2, x2 - 2);

		if (horizontal == -1 || vertical == -1)
			return;
		int rand = getRand(0, 2);

		int DIVIDE_HORZONTALLY = 1;
		int DIVIDE_VERTICALLY = 0;

		if (rand == DIVIDE_HORZONTALLY
				&& canMakeHPath(horizontal, (x2 - x1) / 2, x2)) {
			// Create a horizontal wall across the screen.
			horizontalWall(x1 + 1, x2 - 1, horizontal);
			// Poke a hole in the wall
			int hole = getHole(x1 + 1, x2 - 1);
			toPath(hole, horizontal);
		} else if (rand == DIVIDE_VERTICALLY
				&& canMakeVPath(vertical, (y2 - y1) / 2, y2)) {
			// Create a vertical wall up the screen.
			verticalWall(y1 + 1, y2 - 1, vertical);

			int hole = getHole(y1 + 1, y2 - 1);
			toPath(vertical, hole);
		} else
			return;
		// comp.compare(true, true);
		if (rand == DIVIDE_HORZONTALLY) {
			// Bottom
			recurse(x1, horizontal, x2, y2);
			// Top
			recurse(x1, y1, x2, horizontal);

		} else {
			// Left
			recurse(x1, y1, vertical, y2);
			// Right
			recurse(vertical, y1, x2, y2);
		}
	}

	public static class Settings {
		public int x1, y1, x2, y2;

		public Settings(int x1, int y1, int x2, int y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
	}

	private boolean canMakeHPath(int x1, int y1, int x2) {
		for (int i = x2; i < x2; i++) {
			if (maze[y1][i].value == Cell.PATH)
				return false;
		}
		return true;
	}

	private boolean canMakeVPath(int y1, int y2, int x1) {
		for (int i = y2; i < y2; i++) {
			if (maze[i][x1].value == Cell.PATH) {
				return false;
			}
		}
		return true;
	}

	private void horizontalWall(int column_num, int column_end, int y) {
		for (int i = column_num; i <= column_end; i++)
			toWall(i, y);
	}

	private void verticalWall(int row_num, int row_end, int x) {
		for (int i = row_num; i <= row_end; i++)
			toWall(x, i);
	}

	/**
	 * Return a random wall between number y1 and y2.
	 * 
	 * @param y1
	 *            Start of range
	 * @param y2
	 *            End of range, inclusive
	 * @return
	 */
	private int getDivide(int y1, int y2) {
		if (y1 > y2)
			return -1;
		if (y2 == y1)
			return y1;
		int r = 0;
		do {
			r = getRand(y1, y2);
			// Only choose numbers that are evenly numbered.
			// This is because walls are odd numbers
		} while (r % 2 == 1);
		return r;

	}

	/**
	 * Return a random even number in between the values specified.
	 * 
	 * @param y1
	 *            Start of range
	 * @param y2
	 *            End of range, inclusive
	 * @return
	 */
	private int getHole(int y1, int y2) {
		int r = 0;
		do {
			r = getRand(y1, y2);
			// Only choose numbers that are evenly numbered.
			// This is because walls are odd numbers
		} while (r % 2 == 0);
		return r;

	}

	public Cell[][] generate() {
		display = new Settings(0, 0, size_row - 1, size_col - 1);
		recurse(0, 0, size_row - 1, size_col - 1);
		return maze;
	}

	@Override
	public String toString() {
		return "DivisionMaze [getType()=" + getType() + "size_row=" + size_row
				+ ", size_col=" + size_col + "]";
	}
}
