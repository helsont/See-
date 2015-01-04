package com.winter.algorithms.depthfirstsearch;

import java.util.ArrayList;
import java.util.Stack;

import com.winter.algorithms.core.AlgorithmController.VisualHang;
import com.winter.algorithms.depthfirstsearch.DepthFirstSearchComponent.NodeComponent;

public class DepthFirstSearch {

	public DepthFirstSearch() {

	}

	public void search(Graph g, VisualHang hang) {
		g.nodes = g.readGraph(null);
		ArrayList<Node> visited = new ArrayList<Node>();
		Stack<Node> nodes = new Stack<Node>();
		Node current = g.nodes[1][1];
		search(visited, nodes, current);
		while (!nodes.isEmpty()) {
			Node next = nodes.pop();
			hang.hang(new NodeComponent(g.nodes, next));
			if (!visited.contains(next)) {
				nodes.push(next);
				search(visited, nodes, next);
			}
		}
		System.out.println("Completed.");
	}

	private boolean isLegal(Object val) {
		return ((Character) val).equals('0');
	}

	private void search(ArrayList<Node> visited, Stack<Node> nodes, Node n) {
		if (n == null) {
			return;
		}
		visited.add(n);
		n.visited = true;
		Node[] ns = n.toArray();
		for (int i = 0; i < 4; i++) {
			if (ns[i] != null && isLegal(ns[i].val))
				nodes.push(ns[i]);
		}
	}

	static class Node {
		int x, y;
		Object val;
		Node left, right, up, down;
		boolean visited;
		boolean isEnd = false;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public Node(int x, int y, Object val) {
			this(x, y);
			this.val = val;
		}

		public Node[] toArray() {
			return new Node[] { left, up, right, down };
		}

		@Override
		public String toString() {
			return "Node[x=" + x + ", y=" + y + "]";
		}
	}

	public static class Graph implements ReadGraph {

		public Node[][] nodes;

		@Override
		public Node[][] readGraph(Object o) {
			Node[][] n = new Node[5][5];
			//@formatter:off
			String[] l = new String[] { 
					"11111", 
					"10001", 
					"11101", 
					"10001",
					"11111", 
					};
			char[][] arr = new char[][] {
				l[0].toCharArray(),
				l[1].toCharArray(),
				l[2].toCharArray(),
				l[3].toCharArray(),
				l[4].toCharArray()	
			};
			//@formatter:on
			for (int y = 0; y < 5; y++) {
				for (int x = 0; x < 5; x++) {
					Character c = arr[y][x];
					n[y][x] = new Node(x, y, c);
				}
			}
			for (int y = 0; y < 5; y++) {
				for (int x = 0; x < 5; x++) {
					Node curr = n[y][x];
					if (x > 0) {
						curr.left = n[y][x - 1];
						n[y][x - 1].right = curr;
					}
					if (y > 0) {
						curr.up = n[y - 1][x];
						n[y - 1][x].down = curr;
					}
					if (x < 4) {
						curr.right = n[y][x + 1];
						n[y][x + 1].left = curr;
					}
					if (y < 4) {
						curr.down = n[y + 1][x];
						n[y + 1][x].up = curr;
					}
					int i = 0;
				}
			}

			n[3][1].isEnd = true;
			return n;
		}
	}

	interface ReadGraph {
		public Node[][] readGraph(Object o);
	}
}
