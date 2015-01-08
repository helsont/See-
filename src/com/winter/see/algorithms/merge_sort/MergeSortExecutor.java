package com.winter.see.algorithms.merge_sort;

import java.util.Comparator;

import com.winter.see.core.AlgorithmExecutor;
import com.winter.see.core.AlgorithmFrame;
import com.winter.see.core.AlgorithmController.VisualHang;

/**
 * This runnable executes a sort algorithm. When two elements are compared, the
 * algorithm pauses and updates a panel.
 */
public class MergeSortExecutor implements AlgorithmExecutor {

	private Double[] values;
	private MergeSortComponent panel;
	private VisualHang hang;

	public MergeSortExecutor(Double[] values, MergeSortComponent panel) {
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
					* (AlgorithmFrame.getDefaultFrameHeight() - 50);
		panel.setValues(new Values(values, null, null));
	}

	@Override
	public void runAlgorithm() {
		Comparator<Double> comp = new Comparator<Double>() {
			public int compare(Double d1, Double d2) {
				hang.hang(new Values(values, d1, d2));
				return d1.compareTo(d2);
			}
		};
		MergeSorter.sort(values, comp);
	}

	@Override
	public void setHang(VisualHang hang) {
		this.hang = hang;
	}

	@Override
	public void updateComponent(Object values) {
		panel.setValues(new Values(((Values) values).values,
				((Values) values).marked1, ((Values) values).marked2));
	}

	@Override
	public void updateComponentEnded() {
		panel.setValues(new Values(values, null, null));
	}

	@Override
	public void updateComponentStarted() {
		// Can leave empty
	}

}
