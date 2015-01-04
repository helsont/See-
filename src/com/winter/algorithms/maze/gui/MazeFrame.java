package com.winter.algorithms.maze.gui;

import com.winter.algorithms.core.AlgorithmFrame;
import com.winter.algorithms.maze.AbstractMazeGenerator;
import com.winter.algorithms.maze.algorithms.DepthFirstMaze;
import com.winter.algorithms.maze.algorithms.DivisionMaze;
import com.winter.algorithms.maze.algorithms.GrowingTreeMaze;
import com.winter.algorithms.maze.algorithms.HuntAndKillMaze;

public class MazeFrame {
	public static void main(String[] args) {
		int width = 0;
		int height = 0;
		AbstractMazeGenerator maze = null;
		if (args.length == 3) {
			String type = args[0];
			try {
				width = Integer.parseInt(args[1]);
				height = Integer.parseInt(args[2]);
			} catch (NumberFormatException ex) {
				// printHelp();
			}
			if (type.equalsIgnoreCase("DepthFirstMaze"))
				maze = new DepthFirstMaze(width, height);
			else if (type.equalsIgnoreCase("HuntAndKillMaze"))
				maze = new HuntAndKillMaze(width, width);
			else if (type.equalsIgnoreCase("GrowingTreeMaze")) {
				maze = new GrowingTreeMaze(width, height);
			} else if (type.equalsIgnoreCase("DivisionMaze")) {
				maze = new DivisionMaze(width, height);
			} // else
				// printHelp();
		} else {
			// printHelp();
			width = 25;
			height = 25;
			maze = new DivisionMaze(width, height);
		}

		MazeComponent panel = new MazeComponent();
		MazeThread thread = new MazeThread(maze, panel, maze.maze);
		ExportPanel ex = new ExportPanel(maze, panel);
		SelectGeneratorPanel select = new SelectGeneratorPanel(maze, panel,
				thread);
		new AlgorithmFrame(panel, thread).addPanelEast(select)
				.addPanelSouth(ex);
	}

	private static void printHelp() {
		System.out.println("MazeAlgorithms. Helson Taveras.");
		System.out.println("Example arguments:");
		System.out.println("GrowingTreeMaze 99 99");
		System.out.println("HuntAndKillMaze 57 57");
		System.out.println("DivisionMaze 33 33");
		System.out.println("DepthFirstMaze 9 9");
		System.out.println("Note: all sizes must be odd. Also unexpected\n"
				+ "behavior may occur if the sizes aren't the same.");
	}
}
