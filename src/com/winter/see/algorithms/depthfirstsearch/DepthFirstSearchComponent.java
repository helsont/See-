package com.winter.see.algorithms.depthfirstsearch;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.winter.see.algorithms.depthfirstsearch.DepthFirstSearch.Node;
import com.winter.see.core.AlgorithmComponent;

public class DepthFirstSearchComponent extends JPanel implements
		AlgorithmComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3737988393522181136L;
	private Node[][] nodes;
	private Node current;
	private ArrayList<Node> path;
	private static final Color TRANS_RED = new Color(255, 0, 56, 240),
			TRANS_WHITE = new Color(255, 255, 255, 100),
			TRANS_BLACK = new Color(255, 255, 255, 240),
			TRANS_YELLOW = new Color(255, 255, 153, 150),
			TRANS_BLUE = new Color(17, 80, 147, 150);

	public DepthFirstSearchComponent() {

	}

	@Override
	protected void paintComponent(Graphics g) {
		if (nodes == null) {
			// System.out.println("Nodes is null.");
			return;
		}
		System.out.println(getWidth());
		// System.out.println("nodes length:" + nodes.length);
		double perc = .8;
		int width = (int) (getWidth() * perc / nodes[0].length);

		int height = (int) (getHeight() * perc / nodes.length);
		width = Math.min(width, height);
		int cY = getHeight() / 2 - nodes.length * width / 2;

		int cX = getWidth() / 2 - (nodes[0].length * width) / 2;

		System.out.println("Cx:" + cX);
		for (int x = 0; x < nodes[0].length; x++) {
			for (int y = 0; y < nodes.length; y++) {

				g.setColor(Color.black);
				if (!nodes[x][y].visited)
					continue;
				if (nodes[x][y].val.equals('1'))
					g.fillRect(cX + nodes[x][y].x * width, cY + nodes[x][y].y
							* width, width, width);
				else
					g.drawRect(cX + nodes[x][y].x * width, cY + nodes[x][y].y
							* width, width, height);
			}
		}

		if (current != null) {
			int x = current.x;
			int y = current.y;
			g.setColor(TRANS_BLUE);
			g.fillRect(cX + width / 4 + x * width, cY + width / 4 + y * width,
					width / 2, width / 2);
		} else
			System.out.println("Current == null");

	}

	@Override
	public void setValues(Object vals) {
		NodeComponent nc = (NodeComponent) vals;
		this.nodes = nc.nodes;
		this.current = nc.current;
		repaint();
	}

	@Override
	public void initializeComponent() {

	}

	static class NodeComponent {
		Node[][] nodes;
		Node current;
		ArrayList<Node> path;

		public NodeComponent(Node[][] nodes, Node current) {
			this.nodes = nodes;
			this.current = current;
		}

		public NodeComponent(ArrayList<Node> path, Node current) {
			this.path = path;
			this.current = current;
		}
	}

}