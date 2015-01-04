package com.winter.algorithms.merge_sort;

import com.winter.algorithms.core.AlgorithmFrame;

public class MergeSortFrame {
	public static void main(String[] args) {
		MergeSortComponent panel = new MergeSortComponent();
		Double[] values = new Double[VALUES_LENGTH];
		for (int i = 0; i < values.length; i++)
			values[i] = Math.random()
					* (AlgorithmFrame.getDefaultFrameHeight() - 50);
		final MergeSortExecutor sorter = new MergeSortExecutor(values, panel);

		new AlgorithmFrame(panel, sorter);
	}

	private static final int VALUES_LENGTH = 30;
}
