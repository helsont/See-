package com.winter.algorithms.maze.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.winter.algorithms.maze.AbstractMazeGenerator;
import com.winter.algorithms.maze.Cell;

/**
 * Highly customizable maze algorithm that allows for different routes to be
 * taken once the algorithm has reached a dead end.
 * 
 * @see GrowingTreeMaze.Parameters
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public class GrowingTreeMaze extends AbstractMazeGenerator {
	private LinkedList<Cell> list;
	private Parameters parameter;
	private ArrayList<Integer> dirs;

	/**
	 * Defines what cell will be chosen to start from once the algorithm has
	 * reached a dead end. </p>
	 * <p>
	 * <b>Newest</b>: the most recent cell (recursive backtracker). <br>
	 * <b>Random</b>: randomly selects a cell (Prim's). <br>
	 * <b>Newest/Random</b>: randomly selects between using Newest or Random,
	 * based on the percentage specified.<br>
	 * <b>Oldest</b>: the oldest cell in the maze. <br>
	 * <b>Middle</b>: the cell that has been made in the middle of the maze
	 * creation. <br>
	 * </p>
	 * 
	 * @author Helson Taveras hjt2113@columbia.edu
	 * 
	 */
	public enum Parameters {
		NEWEST, RANDOM, NEWEST_75_RANDOM_25, NEWEST_50_RANDOM_50, NEWEST_25_RANDOM_75, OLDEST, MIDDLE
	};

	/**
	 * Creates a GrowingTreeMaze with the default of Parameters.NEWEST.
	 * 
	 * @param width
	 *            Width of the maze
	 * @param height
	 *            Height of the maze
	 */
	public GrowingTreeMaze(int width, int height) {
		super("Growing Tree Maze", width, height);
		list = new LinkedList<Cell>();
		// Get first Parameter.
		parameter = Parameters.values()[0];
		dirs = new ArrayList<Integer>();
		dirs.add(0);
		dirs.add(1);
		dirs.add(2);
		dirs.add(3);
		init();
	}

	/**
	 * Creates a GrowingTreeMaze with the default of Parameters.NEWEST.
	 * 
	 * @param width
	 *            Width of the maze
	 * @param height
	 *            Height of the maze
	 * @param type
	 *            The course that the algorithm will take when it reaches a dead
	 *            end
	 */
	public GrowingTreeMaze(int width, int height, Parameters type) {
		this(width, height);
		parameter = type;
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
				Cell next = null;
				// We can only go on if there are more elements
				// to check.
				while (next == null && list.size() > 0) {
					int[] xy = getNext();
					Cell node = maze[xy[0]][xy[1]];
					Collections.shuffle(dirs);
					for (int dir = 0; dir < 4; dir++) {
						Cell c = getAdjacentPathNode(node, dirs.get(dir));
						if (c == null)
							continue;
						if (!list.contains(c) && isAvailable(c.x, c.y)
								&& withinBounds(c.x, c.y)) {
							hang.hang(null);
							Cell wall = getAdjacent(node, dirs.get(dir));
							wall.value = Cell.PATH;
							x_next = c.x;
							y_next = c.y;
							next = c;
							break;
						}
					}
				}
				idx--;
			}
			if (list.size() == 0)
				return;

			if (neighbour_valid != -1) {
				int randomization = neighbour_valid + 1;
				int random = rand(randomization);
				x_next = neighbour_x[random];
				y_next = neighbour_y[random];
				idx++;
				list.add(maze[x_next][y_next]);

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

			recurse(idx, maze, x_next, y_next, n, visited);
		}
	}

	private boolean withinBounds(int x, int y) {
		return x >= 1 && x <= maze.length - 2 && y >= 1
				&& y <= maze[0].length - 2;
	}

	private boolean isAvailable(int x, int y) {
		if ((this.maze[x - 1][y].value == WALL)
				&& (this.maze[x][y - 1].value == WALL)
				&& (this.maze[x][y + 1].value == WALL)
				&& (this.maze[x + 1][y].value == WALL))
			return true;

		return false;
	}

	/**
	 * Returns an array where int[0] is the x_next and int[1] is y_next.
	 * 
	 * @return
	 */
	private int[] getNext() {
		int[] xy = null;
		if (parameter == Parameters.NEWEST) {
			xy = newest();
		} else if (parameter == Parameters.RANDOM) {
			xy = random();
		} else if (parameter == Parameters.NEWEST_75_RANDOM_25) {
			float random = super.random.nextFloat();
			if (random < .75f) {
				xy = newest();
			} else {
				xy = random();
			}
		} else if (parameter == Parameters.NEWEST_50_RANDOM_50) {
			float random = super.random.nextFloat();
			if (random < .5f) {
				xy = newest();
			} else {
				xy = random();
			}
		} else if (parameter == Parameters.NEWEST_25_RANDOM_75) {
			float random = super.random.nextFloat();
			if (random < .25f) {
				xy = newest();
			} else {
				xy = random();
			}
		} else if (parameter == Parameters.OLDEST) {
			xy = oldest();
		} else if (parameter == Parameters.MIDDLE) {
			xy = middle();
		}
		return xy;
	}

	private int[] newest() {
		Cell newest = list.getLast();
		list.removeLast();
		return new int[] { newest.x, newest.y };
	}

	private int[] random() {
		int random = getRand(0, list.size());
		Cell newest = list.get(random);
		list.remove(random);
		return new int[] { newest.x, newest.y };
	}

	private int[] oldest() {
		Cell oldest = list.getFirst();
		list.removeFirst();
		return new int[] { oldest.x, oldest.y };
	}

	private int[] middle() {
		int idx = list.size() / 2;
		Cell middle = list.get(idx);
		list.remove(idx);
		return new int[] { middle.x, middle.y };
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
		list.add(maze[1][1]);
		recurse(indeks, maze, 1, 1, (size_col - 1) / 2 * (size_row - 1) / 2, 1);
		return maze;
	}

	public Parameters getParameter() {
		return parameter;
	}

	public void setParameter(Parameters parameter) {
		this.parameter = parameter;
	}
}
