package maze_algorithms;

import java.util.Comparator;

import maze.AbstractMazeGenerator;
import maze.Cell;

public class DepthFirstMaze extends AbstractMazeGenerator {

	public DepthFirstMaze(int size) {
		super(size, size);
	}

	public void maze_generator(int indeks, Cell maze[][], int backtrack_x[],
			int backtrack_y[], int x, int y, int n, int visited,
			Comparator<Boolean> comp) {
		int directions = 4;
		if (comp.compare(visited < n, true) == 0) {
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
					y_next, n, visited, comp);
		}
	}

	public int rand(int randomization) {
		return super.rand(randomization);
	}

	public void print_maze(Cell maze[][], int maze_size) {
		for (int a = 0; a < maze_size * 2 + 1; a++) {
			for (int b = 0; b < maze_size * 2 + 1; b++) {
				if (maze[a][b].value == WALL)
					printf("#");
				else
					printf(" ");
			}
			printf("\n");
		}
	}

	public static void printf(String s) {
		System.out.print(s);
	}

	public boolean is_closed(Cell maze[][], int x, int y) {
		if (this.maze[x - 1][y].value == WALL
				&& this.maze[x][y - 1].value == WALL
				&& this.maze[x][y + 1].value == WALL
				&& this.maze[x + 1][y].value == WALL)
			return true;

		return false;
	}

	@Override
	public Cell[][] generate(Comparator<Boolean> comp) {
		int indeks = 0;

		int backtrack_x[] = new int[size_col * size_row];
		int backtrack_y[] = new int[size_col * size_row];

		init();

		backtrack_x[indeks] = 1;
		backtrack_y[indeks] = 1;

		maze_generator(indeks, maze, backtrack_x, backtrack_y, 1, 1,
				(size_col - 1) / 2 * (size_row - 1) / 2, 1, comp);
		return maze;
	}
}
