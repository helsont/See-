package com.winter.algorithms.depthfirstsearch;

import com.winter.algorithms.core.AlgorithmFrame;

public class DepthFirstSearchFrame {

	public static void main(String[] args) {
		DepthFirstSearch dfs = new DepthFirstSearch();
		DepthFirstSearchComponent comp = new DepthFirstSearchComponent();
		DepthFirstSearchExecutor exec = new DepthFirstSearchExecutor(dfs, comp);
		new AlgorithmFrame(comp, exec);
	}
}
