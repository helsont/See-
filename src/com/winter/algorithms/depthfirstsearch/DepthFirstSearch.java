package com.winter.algorithms.depthfirstsearch;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import com.winter.algorithms.core.AlgorithmController.VisualHang;
import com.winter.algorithms.depthfirstsearch.DepthFirstSearchComponent.NodeComponent;

public class DepthFirstSearch {

	public DepthFirstSearch() {

	}

	public void search(Graph g, VisualHang hang) {
		g.nodes = g.readGraph(null);
		ArrayList<Node> visited = new ArrayList<Node>();
		ArrayList<Node> nodes = new ArrayList<Node>();
		Node current = g.nodes[1][1];
		Node last = null;
		search(g, visited, nodes, current);
		while (!nodes.isEmpty()) {
			Node next = getNodeWithLowestDistance(nodes, g.nodes[23][23]);
			hang.hang(new NodeComponent(g.nodes, next));
			if (next.isEnd) {
				last = next;
				hang.hang(new NodeComponent(g.nodes, next));
				break;
			}
			if (!visited.contains(next)) {
				nodes.add(next);
				search(g, visited, nodes, next);
			}
		}
		NodeIterator it = new NodeIterator(last);
		ArrayList<Node> path = new ArrayList<Node>();
		while (it.hasNext()) {
			Node node = it.next();
			path.add(node);
			hang.hang(new NodeComponent(path, node));
		}
	}

	private Node getNodeWithLowestDistance(ArrayList<Node> nodes, Node target) {
		int dist = Integer.MAX_VALUE;
		Node lowest = null;
		for (Node n : nodes) {
			int d = manhattandistance(n.x, n.y, target.x, target.y);
			if (d < dist) {
				d = dist;
				lowest = n;
			}
		}
		return lowest;
	}

	private int manhattandistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}

	private boolean isLegal(Object val) {
		return ((Character) val).equals('0');
	}

	private void search(Graph g, ArrayList<Node> visited,
			ArrayList<Node> nodes, Node n) {
		if (n == null) {
			return;
		}
		visited.add(n);
		n.visited = true;
		Node[] ns = n.toArray(g);
		for (int i = 0; i < 4; i++) {
			if (ns[i] != null && isLegal(ns[i].val)) {
				if (!visited.contains(ns[i])) {
					ns[i].parent = n;
					nodes.add(ns[i]);
				}
			}
		}
	}

	static class Node {
		int x, y;
		Object val;
		Node left, right, up, down;
		Node parent;
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

		public Node[] toArray(Graph g) {
			return new Node[] { left(g), up(g), right(g), down(g) };
		}

		private Node left(Graph g) {
			return (x > 0) ? g.nodes[y][x - 1] : null;
		}

		private Node right(Graph g) {
			return (x < g.nodes[0].length) ? g.nodes[y][x + 1] : null;
		}

		private Node up(Graph g) {
			return (y > 0) ? g.nodes[y - 1][x] : null;
		}

		private Node down(Graph g) {
			return (y < g.nodes.length) ? g.nodes[y + 1][x] : null;
		}

		@Override
		public String toString() {
			return "Node[x=" + x + ", y=" + y + "]";
		}
	}

	static class NodeIterator implements Iterator<Node> {
		private int idx;
		private Node curr;

		public NodeIterator(Node n) {
			curr = n;
		}

		@Override
		public boolean hasNext() {
			return curr.parent != null;
		}

		@Override
		public Node next() {
			return (curr = curr.parent);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	public static class Graph implements ReadGraph {

		public Node[][] nodes;

		public Node[][] testGraph() {
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

			n[3][1].isEnd = true;
			return n;
		}

		private static int countLines(String filename) throws IOException {
			InputStream is = new BufferedInputStream(new FileInputStream(
					filename));
			try {
				byte[] c = new byte[1024];
				int count = 0;
				int readChars = 0;
				boolean empty = true;
				while ((readChars = is.read(c)) != -1) {
					empty = false;
					for (int i = 0; i < readChars; ++i) {
						if (c[i] == '\n') {
							++count;
						}
					}
				}
				return (count == 0 && !empty) ? 1 : count;
			} finally {
				is.close();
			}
		}

		@Override
		public Node[][] readGraph(Object o) {
			Node[][] nodes = null;
			BufferedReader br = null;
			try {
				String name = "Hunt and Kill Maze20150104_203348.txt";
				File file = new File(name);

				// This will output the full path where the file will be
				// written to.
				br = new BufferedReader(new FileReader(file));
				int y = 0;
				br.mark(100);
				int width = br.readLine().toCharArray().length;
				int height = countLines(name) + 1;
				nodes = new Node[width][height];
				br.reset();
				String str = "";
				while ((str = br.readLine()) != null) {
					System.out.println(str);
					char[] c = str.toCharArray();
					for (int x = 0; x < c.length; x++) {
						Character a = c[x];
						nodes[x][y] = new Node(x, y, a);
						if (a.equals('X'))
							nodes[x][y].isEnd = true;
					}
					y++;
				}
				br.close();
				return nodes;
			} catch (IOException e) {

			}
			return nodes;
		}
	}

	interface ReadGraph {
		public Node[][] readGraph(Object o);
	}
}
