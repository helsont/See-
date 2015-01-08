package com.winter.algorithms.maze;


/**
 * Used to generates mazes.
 * 
 * @see com.winter.algorithms.maze.AbstractMazeGenerator
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public interface MazeGenerator {
	/**
	 * Generates a maze.
	 * 
	 * @return A double array of cells.
	 */
	public Cell[][] generate();

}
