package maze;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;

import maze.AbstractMazeGenerator.CellValues;
import algorithm_base.AlgorithmState;
import algorithm_base.AlgorithmRunner;

public class MazeThread implements AlgorithmRunner, Runnable {
	private BlockingQueue<AlgorithmState> queue;
	private int delay;
	private MazeComponent panel;
	private AbstractMazeGenerator generator;
	private Cell[][] maze;

	public MazeThread(AbstractMazeGenerator generator,
			final MazeComponent panel, Cell[][] val) {
		this.maze = val;
		this.panel = panel;
		this.generator = generator;

	}

	@Override
	public void run() {
		Comparator<Boolean> comp = new Comparator<Boolean>() {
			public int compare(Boolean d1, Boolean d2) {
				try {
					// Block the thread until the user either clicks
					// 'Step' or 'Run'
					AlgorithmState command = queue.take();
					if (command == AlgorithmState.RUN) {
						Thread.sleep(delay);
						if (AlgorithmState.STEP != queue.peek())
							queue.add(AlgorithmState.RUN);
					}
				} catch (InterruptedException exception) {
					Thread.currentThread().interrupt();
				}
				panel.setValues(new CellValues(maze));
				return d1.compareTo(d2);
			}
		};
		generator.generate(comp);
		panel.setValues(new CellValues(maze));
	}

	@Override
	public void setQueue(BlockingQueue<AlgorithmState> queue) {
		this.queue = queue;
	}

	@Override
	public void setDelay(int delay) {
		this.delay = delay;
	}

	@Override
	public void reset() {
		generator.init();
	}
}