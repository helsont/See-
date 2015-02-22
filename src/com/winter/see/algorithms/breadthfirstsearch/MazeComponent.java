package com.winter.see.algorithms.breadthfirstsearch;

import com.winter.see.core.AlgorithmComponent;

import javax.swing.*;
import java.awt.*;
import java.beans.Transient;

public class MazeComponent extends JComponent implements AlgorithmComponent {
    private BreadthFirstSearch.Node[][] maze;
    private static final long serialVersionUID = 1L;
    //    private DivisionMaze.Settings hang;
    public boolean showExtra;
    private static final int PREFERRED_WIDTH = 450, PREFERRED_HEIGHT = 450;
    private BreadthFirstSearch.Node current;
    private Path path;
    private int gradient;

    private static final Color TRANS_RED = new Color(255, 0, 56, 240),
            TRANS_BLACK = new Color(0, 0, 0, 40),
            TRANS_YELLOW = new Color(255, 255, 153, 150),
            TRANS_BLUE = new Color(17, 80, 147, 150),
            TRANS_LIGHT_BLUE = new Color(126, 156, 255, 150),
            TRANS_GREEN = new Color(13, 148, 13, 190);
    Color bb = new Color(0, 0, 0, 100);
    public MazeComponent() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        showExtra = true;
    }

    public void initializeComponent() {

    }

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
                if (maze[x][y].isWall())
                    g.fillRect(cX + x * width, cY + y * width, width, width);
                else if (maze[x][y].isStart()) {
                    g.setColor(TRANS_YELLOW);
                    g.fillRect(cX + x * width, cY + y * width, width, width);
                } else if (maze[x][y].isTarget()) {
                    g.setColor(TRANS_GREEN);
                    g.fillRect(cX + x * width, cY + y * width, width, width);
                }
                // TO SHOW THE DISTANCE COLORS AFTER THE PATH HAS BEEN CALCULATED
                // REMOVE THE LAST CONDITION
                if (maze[x][y].visited && maze[x][y].isBlank() && path == null) {
                    int alpha = Math.min(gradient * (maze[x][y].distance), 255);
                    Color c = new Color(255, 0, 16, alpha);
                    g.setColor(c);
                    g.fillRect(cX + x * width, cY + y * width, width, width);
                }
            }
        }

        if (current != null) {
            int x1 = current.x;
            int y1 = current.y;

            g.setColor(TRANS_YELLOW);
            g.fillRect(cX + x1 * width, cY + y1 * width, width, width);
            g.setColor(TRANS_BLUE);
            g.fillRect(cX + width / 4 + x1 * width,
                    cY + width / 4 + y1 * width, width / 2, width / 2);
        }

        if (showExtra) {
            for (int x = 0; x < maze.length; x++) {
                for (int y = 0; y < maze[0].length; y++) {
                    if (maze[x][y].distance == Integer.MAX_VALUE)
                        continue;
                    g.setColor(Color.black);
//                    g.drawString(maze[x][y].x + "," + maze[x][y].y, cX + maze[x][y].x * width + width / 2, cY
//                            + maze[x][y].y * width + width / 2);
                    g.drawString(maze[x][y].distance + "", cX + maze[x][y].x * width + width / 2, cY
                            + maze[x][y].y * width + width / 2);
                }
            }
        }

        if (path != null) {
            BreadthFirstSearch.Node[] list = path.listPath();
            for (int i = 0; i < list.length; i++) {
                BreadthFirstSearch.Node curr = list[i];
                g.setColor(TRANS_LIGHT_BLUE);
                g.fillRect(cX + curr.x * width, cY + curr.y * width, width, width);
            }
        }
    }

    private int calculateGradient() {
        int width = maze.length;
        int height = maze[0].length;
        return (int) (255 / Math.sqrt(width * width + height * height));
    }
    /**
     * Sets the values to be painted.
     *
     * @param vals A container for values.
     */
    @Override
    public synchronized void setValues(Object vals) {
        BreadthFirstSearch.BFSData v = ((BreadthFirstSearch.BFSData) vals);
        this.maze = v.maze;
        this.current = v.current;
        this.path = v.path;
        repaint();
        if (gradient == 0)
            gradient = calculateGradient();
    }

    @Override
    @Transient
    public Dimension getPreferredSize() {
        return new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT);
    }
}
