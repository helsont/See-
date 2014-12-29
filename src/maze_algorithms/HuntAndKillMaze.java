package maze_algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import maze.AbstractMazeGenerator;
import maze.Cell;

public class HuntAndKillMaze extends AbstractMazeGenerator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3460149200687354855L;

	public HuntAndKillMaze(int size_row, int size_col) {
		super("Hunt and Kill Maze", size_row, size_col);
		init();
	}

	@Override
	public Cell[][] generate() {
		init();
		visited.clear();
		dirs.clear();
		arr.clear();
		visited.add(maze[1][1]);
		kill(maze[1][1]);
		return maze;
	}

	public ArrayList<Cell> visited = new ArrayList<Cell>();
	public Stack<Integer> dirs = new Stack<Integer>();
	public Stack<Integer> arr = new Stack<Integer>();

	public Stack<Integer> getDirs(int x, int y) {
		if (arr == null)
			arr = new Stack<Integer>();
		arr.clear();
		if (x > 2)
			arr.add(LEFT);
		if (y > 2)
			arr.add(UP);
		if (x < maze.length - 2)
			arr.add(RIGHT);
		if (y < maze[0].length - 2)
			arr.add(DOWN);
		arr.trimToSize();
		return arr;

	}

	public Cell hunt() {
		// System.out.println("Hunting...");
		for (int y = 1; y < maze[0].length - 1; y += 2)
			for (int x = 1; x < maze.length - 1; x += 2)
				if (!visited.contains(get(x, y))
						&& isAdjacentToVisitedNode(get(x, y)) != null
						&& get(x, y).value == Cell.PATH) {
					Cell adj = isAdjacentToVisitedNode(get(x, y));
					Cell curr = get(x, y);
					int direction = getDirectionOfAdjacentPathNode(curr, adj);
					getAdjacent(curr, direction).value = Cell.PATH;
					if (!visited.contains(adj))
						visited.add(adj);
					if (!visited.contains(curr))
						visited.add(getAdjacent(curr, direction));
					visited.add(curr);
					// visited.add(getAdjacent(curr, direction));
					// visited.add(adj);
					// System.out.println("Found continuation point:"
					// + curr.toString());
					// display();
					return curr;
				}
		System.out.println("Maze completed.");
		display();
		System.out.println("-----------------------");
		return null;
	}

	public Cell isAdjacentToVisitedNode(Cell c) {
		for (int i = 1; i <= 4; i++) {
			Cell adj = getAdjacentPathNode(c, i);
			if (adj != null && adj.value == Cell.PATH && visited.contains(adj))
				return adj;
		}
		return null;
	}

	public int iteration = 0;

	public void kill(Cell current) {
		// System.out.println("-----------------------");
		// System.out.println("Current cell:" + current.toString());
		// System.out.println("Iteration: " + iteration++ + ".");
		// System.out.println("Randomizing directions.");
		// Get the possible directions
		dirs = getDirs(current.x, current.y);

		// Randomize the list
		Collections.shuffle(dirs);

		// The current direction which is being checked
		int dir = -1;
		do {
			dir = dirs.pop();
			if (visited.contains(getAdjacentPathNode(current, dir))) {
				dir = -1;
				continue;
			} else
				break;
		} while (dirs.size() > 0);

		if (dir == -1) {
			dirs.clear();
			Cell hunt = hunt();
			if (hunt == null)
				return;
			else
				kill(hunt);
			return;
		}
		// We have found a good direction
		Cell wall = getAdjacent(current, dir);
		wall.value = Cell.PATH;

		Cell next = getAdjacentPathNode(current, dir);
		visited.add(next);
		dirs.clear();
		visited.add(wall);
		kill(next);
	}
}
