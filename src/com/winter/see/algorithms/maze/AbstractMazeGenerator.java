package com.winter.see.algorithms.maze;

import java.util.Random;

import com.winter.see.algorithms.maze.algorithms.DivisionMaze;
import com.winter.see.core.AlgorithmController.VisualHang;

/**
 * A useful class to generate mazes with helping methods, such as init and
 * getAdjacentCell. Measured Row x Column. Example: maze[3][1] means 3 to the
 * right, 1 down, like a coordinate system.
 * 
 * @see com.winter.see.algorithms.maze.Cell
 * @see com.winter.see.algorithms.maze.MazeGenerator
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public abstract class AbstractMazeGenerator implements MazeGenerator {

	public Cell[][] maze;
	public static final int WALL = Cell.WALL, PATH = Cell.PATH;

	public int size_row, size_col;
	protected Random random;
	protected VisualHang hang;

	private String type;
	protected static int LEFT = 1, UP = 2, RIGHT = 3, DOWN = 4;

	/**
	 * 
	 * @param type
	 * @param size_row
	 * @param size_col
	 */
	public AbstractMazeGenerator(String type, int size_row, int size_col) {
		this(size_row, size_col);
		this.type = type;
	}

	/**
	 * 
	 * @param size_row
	 * @param size_col
	 */
	public AbstractMazeGenerator(int size_row, int size_col) {
		if (size_row % 2 != 1)
			throw new IllegalArgumentException(
					"The number of rows in a maze must be an odd number.");
		if (size_col % 2 != 1)
			throw new IllegalArgumentException(
					"The number of columns in a maze must be an odd number.");
		if (size_row < 3)
			throw new IllegalArgumentException(
					"The number of rows must be atleast 3.");
		if (size_col < 3)
			throw new IllegalArgumentException(
					"The number of colums must be atleast 3.");
		this.size_row = size_row;
		this.size_col = size_col;

		maze = new Cell[size_row][size_col];

		for (int y = 0; y < size_col; y++)
			for (int x = 0; x < size_row; x++)
				maze[x][y] = new Cell(x, y);
		random = new Random();
	}

	public void resize(int width, int height) {
		this.size_col = height;
		this.size_row = width;
		maze = new Cell[size_row][size_col];
		for (int y = 0; y < size_col; y++)
			for (int x = 0; x < size_row; x++)
				maze[x][y] = new Cell(x, y);
	}

	/**
	 * Resets the maze to its initial state.
	 */
	public void init() {
		for (int a = 0; a < size_row; a++) {
			for (int b = 0; b < size_col; b++) {
				if (a % 2 == 0 || b % 2 == 0)
					this.maze[a][b].value = WALL;
				else
					this.maze[a][b].value = PATH;
			}
		}
	}

	public void toPath(int x, int y) {
		if (x == 0 || x == size_col || y == 0 || y == size_row)
			throw new Error("Attempting to change walls of maze.");
		get(x, y).value = Cell.PATH;
	}

	public void toWall(int x, int y) {
		if (x == 0 || x == size_col || y == 0 || y == size_row)
			throw new Error("Attempting to change walls of maze.");
		get(x, y).value = Cell.WALL;
	}

	public void flip(int x, int y) {
		if (x == 0 || x == size_col || y == 0 || y == size_row)
			throw new Error("Attempting to change walls of maze.");
		get(x, y).value = (get(x, y).value == Cell.PATH) ? Cell.WALL
				: Cell.PATH;
	}

	public void display() {
		for (int y = 0; y < size_col; y++) {
			for (int x = 0; x < size_row; x++) {
				System.out.print((maze[x][y].value == Cell.WALL ? "#" : "-"));
			}
			System.out.println();
		}
	}

	public int getRand(int min, int max) {
		if (max <= 0) {
			throw new Error("n must be positive:" + max);
		}
		return min + random.nextInt(max - min);
	}

	public int rand(int max_exclusive) {
		return random.nextInt(max_exclusive);
	}

	public String getType() {
		return type;
	}

	public Cell getRight(Cell c) {
		if (c.x + 1 < maze.length)
			return maze[c.x + 1][c.y];
		return null;
	}

	public Cell getLeft(Cell c) {
		if (c.x - 1 >= 0)
			return maze[c.x - 1][c.y];
		return null;
	}

	public Cell getDown(Cell c) {
		if (c.y + 1 < maze[0].length)
			return maze[c.x][c.y + 1];
		return null;
	}

	public Cell getUp(Cell c) {
		if (c.y - 1 >= 0)
			return maze[c.x][c.y - 1];
		return null;
	}

	/**
	 * Returns the specified cell.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Cell get(int x, int y) {
		return maze[x][y];
	}

	/**
	 * Skips over the wall node in between the predefine path cells and returns
	 * the specified adjacent cell.
	 * 
	 * @param c
	 * @param dir
	 * @return
	 */
	public Cell getAdjacentPathNode(Cell c, int dir) {
		if (dir == LEFT && c.x - 2 > 0)
			return maze[c.x - 2][c.y];
		else if (dir == UP && c.y - 2 > 0)
			return maze[c.x][c.y - 2];
		else if (dir == RIGHT && c.x + 2 < maze.length)
			return maze[c.x + 2][c.y];
		else if (dir == DOWN && c.y + 2 < maze[0].length)
			return maze[c.x][c.y + 2];
		return null;
	}

	/**
	 * Returns the adjacent cell with the specified direction.
	 * 
	 * @param c
	 * @param dir
	 *            Use Cell.(Directions): Cell.LEFT, Cell.UP, etc.
	 * @return
	 */
	public Cell getAdjacent(Cell c, int dir) {
		if (dir == LEFT)
			return getLeft(c);
		else if (dir == UP)
			return getUp(c);
		else if (dir == RIGHT)
			return getRight(c);
		else if (dir == DOWN)
			return getDown(c);
		return null;
	}

	/**
	 * Returns a random adjacent cell.
	 * 
	 * @param c
	 * @return
	 */
	public Cell getRandomAdjacentCell(Cell c) {
		int dir = getRand(1, 4);
		if (dir == LEFT)
			return getLeft(c);
		else if (dir == UP)
			return getUp(c);
		else if (dir == RIGHT)
			return getRight(c);
		else if (dir == DOWN)
			return getDown(c);
		return null;
	}

	public int getDirectionOfAdjacent(Cell curr, Cell compare) {
		if (getLeft(curr) == compare)
			return LEFT;
		else if (getUp(curr) == compare)
			return UP;
		else if (getRight(curr) == compare)
			return RIGHT;
		else if (getDown(curr) == compare)
			return DOWN;
		throw new Error("These two cells are not adjacent.");
	}

	public int getDirectionOfAdjacentPathNode(Cell curr, Cell compare) {
		for (int i = 1; i <= 4; i++) {
			Cell result = getAdjacentPathNode(curr, i);
			if (result == compare)
				return i;
		}
		throw new Error("These two cells are not adjacent path nodes.");
	}

	public boolean isAdjacentToPath(Cell c) {
		Cell b = null;
		for (int i = 1; i <= 4; i++) {
			b = getAdjacent(c, i);
			if (b != null && b.value != Cell.PATH)
				return false;
		}
		return true;
	}

	public static class CellValues {
		public Cell[][] maze;
		public DivisionMaze.Settings hang;
		public Cell current;

		public CellValues(Cell[][] maze) {
			this.maze = maze;
		}

		public CellValues(Cell[][] maze, DivisionMaze.Settings hang) {
			this(maze);
			this.hang = hang;
		}

		public CellValues(Cell[][] maze, DivisionMaze.Settings hang,
				Cell current) {
			this(maze, hang);
			this.current = current;
		}

		public CellValues(Cell[][] maze, Cell current) {
			this(maze);
			this.current = current;
		}
	}

	public void setHang(VisualHang hang) {
		this.hang = hang;
	}
}
