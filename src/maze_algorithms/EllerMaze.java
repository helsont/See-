package maze_algorithms;

import java.util.Comparator;
import java.util.HashMap;

import maze.AbstractMazeGenerator;
import maze.Cell;

public class EllerMaze extends AbstractMazeGenerator {
	private HashMap<Integer, Cell> map;

	public EllerMaze(int size_row, int size_col) {
		super("Eller", size_row, size_col);
		init();
		map = new HashMap<Integer, Cell>();
	}

	public void createRow(int row) {

	}

	@Override
	public Cell[][] generate(Comparator<Boolean> comp) {
		int i = 0;
		while (i < size_col) {
			
			createRow(i);
		}
		return maze;
	}

}
