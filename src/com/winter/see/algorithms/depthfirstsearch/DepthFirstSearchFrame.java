package com.winter.see.algorithms.depthfirstsearch;

import com.winter.see.core.AlgorithmFrame;

public class DepthFirstSearchFrame {

	public static void main(String[] args) {
		DepthFirstSearch dfs = new DepthFirstSearch();
		DepthFirstSearchComponent comp = new DepthFirstSearchComponent();
		DepthFirstSearchExecutor exec = new DepthFirstSearchExecutor(dfs, comp);
		new AlgorithmFrame(comp, exec);
	}
}
