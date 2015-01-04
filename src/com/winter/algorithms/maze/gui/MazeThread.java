package com.winter.algorithms.maze.gui;

import com.winter.algorithms.core.AlgorithmController.VisualHang;
import com.winter.algorithms.core.AlgorithmExecutor;
import com.winter.algorithms.maze.AbstractMazeGenerator;
import com.winter.algorithms.maze.AbstractMazeGenerator.CellValues;
import com.winter.algorithms.maze.Cell;
import com.winter.algorithms.maze.algorithms.DivisionMaze;
import com.winter.algorithms.maze.algorithms.HuntAndKillMaze;

public class MazeThread implements AlgorithmExecutor {

	private MazeComponent panel;
	private AbstractMazeGenerator generator;
	private Cell[][] maze;
	private Cell current;

	public MazeThread(AbstractMazeGenerator generator,
			final MazeComponent panel, Cell[][] val) {
		this.maze = val;
		this.panel = panel;
		this.generator = generator;

	}

	public void setGenerator(AbstractMazeGenerator generator) {
		this.generator = generator;
		reset();

	}

	@Override
	public void runAlgorithm() {
		generator.generate();
	}

	@Override
	public void reset() {
		System.out.println("Resetting the maze...");
		generator.init();
		this.maze = new Maze(generator.maze).maze;
	}

	@Override
	public void updateComponent(Object values) {
		if (generator instanceof DivisionMaze) {
			DivisionMaze.Settings temp = ((DivisionMaze) generator).display;
			panel.setValues(new CellValues(new Maze(generator.maze).maze, temp));
		} else if (generator instanceof HuntAndKillMaze) {
			current = ((HuntAndKillMaze) generator).current;
			panel.setValues(new CellValues(new Maze(generator.maze).maze,
					current));
		} else {
			panel.setValues(new CellValues(new Maze(generator.maze).maze));
		}
	}

	@Override
	public void setHang(VisualHang hang) {
		generator.setHang(hang);
	}

	@Override
	public void updateComponentEnded() {
		panel.setValues(new CellValues(new Maze(generator.maze).maze, null,
				null));
	}

	class Maze {
		Cell[][] maze;

		public Maze(Cell[][] other) {
			maze = new Cell[other.length][other[0].length];
			for (int x = 0; x < other.length; x++) {
				for (int y = 0; y < other[0].length; y++) {
					maze[x][y] = new Cell(x, y, other[x][y].value);
				}
			}
		}
	}

	@Override
	public void updateComponentStarted() {
		if (generator instanceof DivisionMaze) {
			DivisionMaze.Settings temp = new DivisionMaze.Settings(0, 0,
					generator.size_row - 1, generator.size_col - 1);
			panel.setValues(new CellValues(new Maze(generator.maze).maze, temp));
		}
	}

	@Override
	public String toString() {
		return "MazeThread [generator=" + generator.toString() + "]";
	}
}