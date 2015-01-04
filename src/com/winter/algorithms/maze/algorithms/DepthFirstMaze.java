package com.winter.algorithms.maze.algorithms;

import com.winter.algorithms.maze.AbstractMazeGenerator;
import com.winter.algorithms.maze.Cell;

/**
 * <p>
 * Also known as recursive backtracking.
 * </p>
 * Requirements: <li>memory to store the entire maze in memory</li> <li>stack
 * space again proportional to the size of the maze</li>
 * 
 * <p>
 * Summary:
 * <li>Choose a starting point in the field.</li>
 * <li>Randomly choose a wall at that point carve a passage through to the
 * adjacent cell, but only if the adjacent cell has not been visited yet.
 * <li>This becomes the new current cell.</li>
 * <li>If all adjacent cells have been visited, back up to the last cell that
 * has uncarved walls.
 * <li>Repeat.</li>
 * </p>
 * <p>
 * The algorithm ends when the process has backed all the way up to the starting
 * point.
 * </p>
 * 
 * @author winter
 * @link Summary taken from:
 *       http://weblog.jamisbuck.org/2010/12/27/maze-generation
 *       -recursive-backtracking
 */
public class DepthFirstMaze extends AbstractMazeGenerator {

	public DepthFirstMaze(int width, int height) {
		super("Depth First", width, height);
	}

	public void maze_generator(int indeks, Cell maze[][], int backtrack_x[],
			int backtrack_y[], int x, int y, int n, int visited) {
		int directions = 4;
		hang.hang(null);
		if (visited < n) {
			int neighbour_valid = -1;
			int[] neighbour_x = new int[directions];
			int[] neighbour_y = new int[directions];
			int[] step = new int[directions];

			int x_next = 0;
			int y_next = 0;

			if (y - 2 > 0 && is_closed(maze, x, y - 2)) // upside
			{
				neighbour_valid++;
				neighbour_x[neighbour_valid] = x;
				neighbour_y[neighbour_valid] = y - 2;
				step[neighbour_valid] = 1;
			}

			if (x - 2 > 0 && is_closed(maze, x - 2, y)) // leftside
			{
				neighbour_valid++;
				neighbour_x[neighbour_valid] = x - 2;
				neighbour_y[neighbour_valid] = y;
				step[neighbour_valid] = 2;
			}

			if (x + 2 < size_row && is_closed(maze, x + 2, y)) // rightside
			{
				neighbour_valid++;
				neighbour_x[neighbour_valid] = x + 2;
				neighbour_y[neighbour_valid] = y;
				step[neighbour_valid] = 3;

			}

			if (y + 2 < size_col && is_closed(maze, x, y + 2)) // downside
			{
				neighbour_valid++;
				neighbour_x[neighbour_valid] = x;
				neighbour_y[neighbour_valid] = y + 2;
				step[neighbour_valid] = 4;
			}

			if (neighbour_valid == -1) {
				// backtrack
				x_next = backtrack_x[indeks];
				y_next = backtrack_y[indeks];
				indeks--;
			}

			if (neighbour_valid != -1) {
				int randomization = neighbour_valid + 1;
				int random = rand(randomization);
				x_next = neighbour_x[random];
				y_next = neighbour_y[random];
				indeks++;
				backtrack_x[indeks] = x_next;
				backtrack_y[indeks] = y_next;

				int rstep = step[random];

				if (rstep == 1) {
					this.maze[x_next][y_next + 1].value = PATH;
				} else if (rstep == 2) {
					this.maze[x_next + 1][y_next].value = PATH;
				} else if (rstep == 3) {
					this.maze[x_next - 1][y_next].value = PATH;
				} else if (rstep == 4) {
					this.maze[x_next][y_next - 1].value = PATH;
				}
				visited++;
			}

			maze_generator(indeks, maze, backtrack_x, backtrack_y, x_next,
					y_next, n, visited);
		}
	}

	private boolean is_closed(Cell maze[][], int x, int y) {
		if (this.maze[x - 1][y].value == WALL
				&& this.maze[x][y - 1].value == WALL
				&& this.maze[x][y + 1].value == WALL
				&& this.maze[x + 1][y].value == WALL)
			return true;

		return false;
	}

	@Override
	public Cell[][] generate() {
		int indeks = 0;

		int backtrack_x[] = new int[size_col * size_row];
		int backtrack_y[] = new int[size_col * size_row];

		init();

		backtrack_x[indeks] = 1;
		backtrack_y[indeks] = 1;

		maze_generator(indeks, maze, backtrack_x, backtrack_y, 1, 1,
				(size_col - 1) / 2 * (size_row - 1) / 2, 1);
		return maze;
	}

	@Override
	public String toString() {
		return "DepthFirstMaze [size_col=" + size_col + ", size_row="
				+ size_row + "]";
	}
}
