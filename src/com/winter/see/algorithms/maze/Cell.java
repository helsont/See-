package com.winter.see.algorithms.maze;

/**
 * Represents on position in the maze space by (x,y) coordinates.
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public class Cell {
	public static final int PATH = 0, WALL = 1;
	public int x, y, value;

	/**
	 * Creates a cell at the specified point
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position
	 */
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a cell at the specified point with the specified value.
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position
	 * @param value
	 *            Cell.PATH or Cell.WALL
	 */
	public Cell(int x, int y, int value) {
		this(x, y);
		this.value = value;
	}

	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (value != other.value)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
