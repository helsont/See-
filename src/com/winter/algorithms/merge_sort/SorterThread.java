package com.winter.algorithms.merge_sort;

import java.util.*;
import java.util.concurrent.*;

import com.winter.algorithms.core.AlgorithmController.VisualHang;
import com.winter.algorithms.core.AlgorithmFrame;
import com.winter.algorithms.core.AlgorithmExecutor;
import com.winter.algorithms.core.AlgorithmState;

/**
 * This runnable executes a sort algorithm. When two elements are compared, the
 * algorithm pauses and updates a panel.
 */
public class SorterThread implements AlgorithmExecutor {

	private Double[] values;
	private ArrayComponent panel;
	private BlockingQueue<AlgorithmState> queue;
	private int delay = 100;

	public SorterThread(Double[] values, ArrayComponent panel) {
		this.values = values;
		this.panel = panel;
	}

	static class Values {
		public Double marked1;
		public Double marked2;
		public Double[] values;

		public Values(Double[] values, Double marked1, Double marked2) {
			this.marked1 = marked1;
			this.marked2 = marked2;
			this.values = values;
		}
	}

	@Override
	public void reset() {
		values = new Double[30];
		for (int i = 0; i < values.length; i++)
			values[i] = Math.random()
					* (AlgorithmFrame.DEFAULT_FRAME_HEIGHT - 50);
		panel.setValues(new Values(values, null, null));
	}

	@Override
	public void runAlgorithm() {
		Comparator<Double> comp = new Comparator<Double>() {
			public int compare(Double d1, Double d2) {
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
				panel.setValues(new Values(values, d1, d2));
				return d1.compareTo(d2);
			}
		};
		MergeSorter.sort(values, comp);
		panel.setValues(new Values(values, null, null));
	}

	@Override
	public void setHang(VisualHang hang) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateComponent(Object values) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateComponentEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateComponentStarted() {
		// TODO Auto-generated method stub

	}

}
