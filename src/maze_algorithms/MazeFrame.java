package maze_algorithms;

import maze.MazeExportPanel;
import maze.MazeComponent;
import maze.MazeThread;
import algorithm_base.AlgorithmFrame;

public class MazeFrame {
	public static void main(String[] args) {
		int size = 9;
		MazeComponent panel = new MazeComponent();
		EllerMaze maze = new EllerMaze(size, size);
		MazeThread thread = new MazeThread(maze, panel, maze.maze);
		MazeExportPanel ex = new MazeExportPanel(maze);
		new AlgorithmFrame(panel, thread, ex);
	}

}
