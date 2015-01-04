package com.winter.algorithms.maze.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import com.winter.algorithms.maze.AbstractMazeGenerator;
import com.winter.algorithms.maze.Cell;

public class HuntAndKillMaze extends AbstractMazeGenerator {

	public Cell current;
	private ArrayList<Cell> added;
	private ArrayList<Integer> dirs;
	private Stack<Integer> checked;

	public HuntAndKillMaze(int size_row, int size_col) {
		super("Hunt and Kill Maze", size_row, size_col);
		init();
		dirs = new ArrayList<Integer>();
		dirs.add(0);
		dirs.add(1);
		dirs.add(2);
		dirs.add(3);
	}

	private void recurse(int idx, Cell maze[][], int x, int y, int n,
			int visited) {
		int directions = 4;
		hang.hang(null);
		if (visited < n) {
			int neighbour_valid = -1;
			int[] neighbour_x = new int[directions];
			int[] neighbour_y = new int[directions];
			int[] step = new int[directions];

			int x_next = 0;
			int y_next = 0;

			// upside
			if (y - 2 > 0 && is_closed(maze, x, y - 2)) {

				neighbour_valid++;
				neighbour_x[neighbour_valid] = x;
				neighbour_y[neighbour_valid] = y - 2;
				step[neighbour_valid] = 1;
			}
			// leftside
			if (x - 2 > 0 && is_closed(maze, x - 2, y)) {

				neighbour_valid++;
				neighbour_x[neighbour_valid] = x - 2;
				neighbour_y[neighbour_valid] = y;
				step[neighbour_valid] = 2;
			}
			// rightside
			if (x + 2 < size_row && is_closed(maze, x + 2, y)) {
				neighbour_valid++;
				neighbour_x[neighbour_valid] = x + 2;
				neighbour_y[neighbour_valid] = y;
				step[neighbour_valid] = 3;
			}
			// downside
			if (y + 2 < size_col && is_closed(maze, x, y + 2)) {
				neighbour_valid++;
				neighbour_x[neighbour_valid] = x;
				neighbour_y[neighbour_valid] = y + 2;
				step[neighbour_valid] = 4;
			}

			if (neighbour_valid == -1) {
				// backtrack
				int[] xy = hunt(x, y);
				if (xy == null)
					return;
				Cell node = maze[xy[0]][xy[1]];

				Collections.shuffle(dirs);
				for (int dir = 0; dir < 4; dir++) {
					Cell c = getAdjacentPathNode(node, dirs.get(dir));
					if (added.contains(c)) {
						Cell wall = getAdjacent(node, dirs.get(dir));
						wall.value = Cell.PATH;
						break;
					}
				}
				x_next = xy[0];
				y_next = xy[1];
				idx--;
			}

			if (neighbour_valid != -1) {
				int randomization = neighbour_valid + 1;
				int random = rand(randomization);
				x_next = neighbour_x[random];
				y_next = neighbour_y[random];
				idx++;

				int rstep = step[random];

				Cell wall = null;
				if (rstep == 1) {
					wall = maze[x_next][y_next + 1];
					added.add(maze[x_next][y_next + 2]);
				} else if (rstep == 2) {
					wall = maze[x_next + 1][y_next];
					added.add(maze[x_next + 2][y_next]);
				} else if (rstep == 3) {
					wall = maze[x_next - 1][y_next];
					added.add(maze[x_next - 2][y_next]);
				} else if (rstep == 4) {
					wall = maze[x_next][y_next - 1];
					added.add(maze[x_next][y_next - 2]);
				}
				wall.value = Cell.PATH;
				added.add(wall);
				visited++;
			}

			recurse(idx, maze, x_next, y_next, n, visited);
		}
	}

	/**
	 * Returns an array where int[0] is the x_next and int[1] is y_next.
	 * 
	 * @return
	 */
	private int[] hunt(int x1, int y1) {
		for (int y = checked.pop(); y < maze.length; y += 2) {
			for (int x = 1; x < maze[0].length; x += 2) {
				current = maze[x][y];
				hang.hang(null);
				if (isAvailable(x, y)) {
					current = null;
					hang.hang(null);
					checked.push(y);
					return new int[] { x, y };
				}
			}
		}
		return null;
	}

	private boolean isAvailable(int x, int y) {
		if ((this.maze[x - 1][y].value == WALL)
				&& (this.maze[x][y - 1].value == WALL)
				&& (this.maze[x][y + 1].value == WALL)
				&& (this.maze[x + 1][y].value == WALL))
			return true;

		return false;
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
		added = new ArrayList<Cell>(size_col * size_row);
		checked = new Stack<Integer>();
		for (int i = size_row; i > 0; i -= 2) {
			checked.push(i);
		}
		recurse(indeks, maze, 1, 1, (size_col - 1) / 2 * (size_row - 1) / 2, 1);
		return maze;
	}

	@Override
	public String toString() {
		return "HuntAndKillMaze [size_row=" + size_row + ", size_col="
				+ size_col + "]";
	}
}
