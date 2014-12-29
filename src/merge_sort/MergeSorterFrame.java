package merge_sort;

import algorithm_base.AlgorithmFrame;

public class MergeSorterFrame {
	public static void main(String[] args) {
		ArrayComponent panel = new ArrayComponent();
		Double[] values = new Double[VALUES_LENGTH];
		for (int i = 0; i < values.length; i++)
			values[i] = Math.random() * (AlgorithmFrame.DEFAULT_FRAME_HEIGHT- 50);
		final SorterThread sorter = new SorterThread(values, panel);

		new AlgorithmFrame(panel, sorter);
	}

	private static final int VALUES_LENGTH = 30;
}
