package com.winter.algorithms.depthfirstsearch;

import com.winter.algorithms.core.AlgorithmController.VisualHang;
import com.winter.algorithms.core.AlgorithmExecutor;
import com.winter.algorithms.depthfirstsearch.DepthFirstSearch.Graph;

public class DepthFirstSearchExecutor implements AlgorithmExecutor {
	private DepthFirstSearch dfs;
	private DepthFirstSearchComponent comp;
	private VisualHang hang;

	public DepthFirstSearchExecutor(DepthFirstSearch dfs,
			DepthFirstSearchComponent comp) {
		this.dfs = dfs;
		this.comp = comp;
	}

	@Override
	public void reset() {

	}

	@Override
	public void runAlgorithm() {
		dfs.search(new Graph(), hang);
	}

	@Override
	public void setHang(VisualHang hang) {
		this.hang = hang;
	}

	@Override
	public void updateComponent(Object values) {
		comp.setValues(values);
	}

	@Override
	public void updateComponentEnded() {

	}

	@Override
	public void updateComponentStarted() {

	}

}
