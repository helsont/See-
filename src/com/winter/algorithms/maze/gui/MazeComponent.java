package com.winter.algorithms.maze.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.beans.Transient;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import com.winter.algorithms.core.AlgorithmComponent;
import com.winter.algorithms.maze.AbstractMazeGenerator;
import com.winter.algorithms.maze.Cell;
import com.winter.algorithms.maze.algorithms.DivisionMaze;

public class MazeComponent extends JComponent implements AlgorithmComponent {
	private Cell[][] maze;
	private static final long serialVersionUID = 1L;
	private Queue<Point> pressed;
	private Queue<Point> moved;
	private DivisionMaze.Settings hang;
	public boolean showExtra;
	private static final int PREFERRED_WIDTH = 450, PREFERRED_HEIGHT = 450;
	private Cell current;
	private static final Color TRANS_RED = new Color(255, 0, 56, 240),
			TRANS_WHITE = new Color(255, 255, 255, 100),
			TRANS_BLACK = new Color(255, 255, 255, 240),
			TRANS_YELLOW = new Color(255, 255, 153, 150),
			TRANS_BLUE = new Color(17, 80, 147, 150);

	public MazeComponent() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		pressed = new LinkedList<Point>();
		moved = new LinkedList<Point>();
		showExtra = false;
	}

	private int diffY = 0;

	public void init() {
		final int y = this.getY();
		diffY = y;
		getParent().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				p.translate(0, -y);
				pressed.add(p);
				repaint();
			}

		});
		getParent().addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				Point p = e.getPoint();
				p.translate(0, -y);
				moved.add(p);
				repaint();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// Point p = e.getPoint();
				// p.translate(0, -y);
				// pressed.add(p);
				// repaint();
			}
		});
	}

	public Color blue = new Color(0, 255, 255, 190);

	public synchronized void paintComponent(Graphics g) {
		if (maze == null) {
			return;
		}
		double perc = 1;
		int width = (int) (getWidth() * perc / maze.length);
		int height = (int) (getHeight() * perc / maze[0].length);
		width = Math.min(width, height);
		int cY = getHeight() / 2 - maze[0].length * width / 2;
		int cX = getWidth() / 2 - maze.length * width / 2;
		for (int x = 0; x < maze.length; x++) {
			for (int y = 0; y < maze[0].length; y++) {
				g.setColor(Color.black);
				g.drawRect(cX + x * width, cY + y * width, width, width);
				if (maze[x][y].value == Cell.WALL)
					g.fillRect(cX + x * width, cY + y * width, width, width);
				if (hang != null) {
					if (x >= hang.x1 && x <= hang.x2 && y >= hang.y1
							&& y <= hang.y2) {
						g.setColor(blue);
						g.fillRect(cX + x * width, cY + y * width, width, width);
					}
				}
			}
		}

		if (current != null) {
			int x1 = current.x;
			int y1 = current.y;

			// This could be one line instead of a for loop
			for (int x = 0; x < maze.length; x++) {
				g.setColor(TRANS_YELLOW);
				g.fillRect(cX + x * width, cY + y1 * width, width, width);
			}

			g.setColor(TRANS_BLUE);
			g.fillRect(cX + width / 4 + x1 * width,
					cY + width / 4 + y1 * width, width / 2, width / 2);

		}

		if (showExtra) {
			for (int x = 0; x < maze.length; x++) {
				for (int y = 0; y < maze[0].length; y++) {
					g.setColor(Color.red);
					g.drawString(x + "," + y, cX + x * width + width / 2, cY
							+ y * width + width / 2);
				}
			}
		}

		if (!moved.isEmpty()) {
			Point p = moved.poll();
			// Out of bounds
			if (p.x < cX || p.y < cY || p.x > cX + width * maze[0].length
					|| p.y > cY + width * maze.length) {
				return;
			}
			int x = (p.x - cX) / width;
			int y = (p.y - cY) / width;
			if (x >= maze.length || x < 0 || y >= maze[0].length || y < 0)
				return;
			Cell c = maze[x][y];
			displayTooltip(g, c, p.x, p.y + diffY);
		}

		if (!pressed.isEmpty()) {
			Point mouse = pressed.poll();
			int x = (mouse.x - cX) / width;
			int y = (mouse.y - cY) / width;
			if (x >= maze.length || x < 0 || y >= maze[0].length || y < 0)
				return;
			Cell c = maze[x][y];
			c.value = (c.value == Cell.PATH) ? Cell.WALL : Cell.PATH;
		}
	}

	public void notImplement(Graphics2D g2) {
		Font f = new Font("Arial", Font.PLAIN, 12);
		// code to create f
		String TITLE = "Text to center in a panel.";
		FontRenderContext context = g2.getFontRenderContext();

		TextLayout txt = new TextLayout(TITLE, f, context);
		Rectangle2D bounds = txt.getBounds();
		int xString = (int) ((getWidth() - bounds.getWidth()) / 2.0);
		int yString = (int) ((getHeight() + bounds.getHeight()) / 2.0);
		// g2 is the graphics object
		g2.setFont(f);
		g2.drawString(TITLE, xString, yString);
	}

	public void displayTooltip(Graphics g, Cell c, int x, int y) {
		int width = 45;
		int height = 20;
		int sX = x;
		int sY = y - diffY - height;
		int sTxt = sX + 2;
		int sTyt = sY + 15;

		g.setColor(TRANS_BLACK);
		g.drawRect(sX, sY, width, height);
		g.setColor(TRANS_WHITE);
		g.fillRect(sX, sY, width, height);
		g.setColor(TRANS_RED);
		g.drawString(c.x + "," + c.y, sTxt, sTyt);
	}

	/**
	 * Sets the values to be painted.
	 * 
	 * @param Object
	 *            A container for values.
	 */
	@Override
	public synchronized void setValues(Object vals) {
		AbstractMazeGenerator.CellValues v = ((AbstractMazeGenerator.CellValues) vals);
		this.maze = v.maze;
		this.hang = v.hang;
		this.current = v.current;
		repaint();
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
	}
}
