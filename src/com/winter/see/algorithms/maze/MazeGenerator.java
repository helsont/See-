package com.winter.see.algorithms.maze;


/**
 * Used to generates mazes.
 * 
 * @see com.winter.see.algorithms.maze.AbstractMazeGenerator
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
