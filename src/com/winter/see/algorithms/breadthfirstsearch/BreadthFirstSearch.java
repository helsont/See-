package com.winter.see.algorithms.breadthfirstsearch;

import com.winter.see.algorithms.maze.Cell;
import com.winter.see.core.AlgorithmController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * For an algorithms class.
 */
public class BreadthFirstSearch implements MazeSolver {

    public Node[][] maze;
    private Queue<Node> toVisit;
    private Node start;
    private AlgorithmController.VisualHang hang;
    private Path path;

    public BreadthFirstSearch() {
        toVisit = new LinkedList<Node>();

    }

    public BreadthFirstSearch(BufferedReader br) throws IOException {
        this(MazeReader.readFile(br));
    }

    public BreadthFirstSearch(char[][] chars) {
        this();
        // We will access by node[row][column]
        maze = new Node[chars.length][chars[0].length];
//        System.out.println(Arrays.deepToString(chars));
        for (int x = 0; x < chars.length; x++) {
            for (int y = 0; y < chars[0].length; y++) {
                maze[x][y] = new Node(x, y, chars[x][y]);
                if (maze[x][y].isStart()) {
                    toVisit.add(start = maze[x][y]);
                    start.visited = true;
                }
            }
        }
        if (start == null)
            throw new Error("Didn't find a starting node");
        else
            System.out.println("Start node:" + start);
    }

    public void setHang(AlgorithmController.VisualHang hang) {
        this.hang = hang;
    }

    private Path findPath() {
        while (!toVisit.isEmpty()) {
            Node curr = toVisit.poll();
            hang.hang(new BFSData(maze, curr));
            System.out.println(curr);
            if (curr.isTarget()) {
                // get the path
                printBackwardsPath(curr);
                return path = getPath(curr);
            }
            addNeighbors(curr);
        }
        return null;
    }

    private void printBackwardsPath(Node n) {
        while (n.parent != null) {
            System.out.println(n);
            n = n.parent;
        }
        System.out.println(n);
    }

    private Path getPath(Node curr) {
        return new Path(curr);
    }

    public int mazeWidth() {
        return maze[0].length;
    }

    public int mazeHeight() {
        return maze.length;
    }

    private Node getNode(int x, int y) {
        return maze[x][y];
    }

    private void addNeighbors(Node n) {
        int x = n.x;
        int y = n.y;
        if (x > 0) {
            // Add left
            Node left = getNode(x - 1, y);
            if (!left.visited && !left.isWall()) {
                toVisit.add(left);
                left.parent = n;
                left.visited = true;
            }
        }
        if (x < mazeWidth() - 1) {
            // Add right
            Node right = getNode(x + 1, y);
            if (!right.visited && !right.isWall()) {
                toVisit.add(right);
                right.parent = n;
                right.visited = true;
            }
        }
        if (y < mazeHeight() - 1) {
            // Add below
            Node down = getNode(x, y + 1);
            if (!down.visited && !down.isWall()) {
                toVisit.add(down);
                down.parent = n;
                down.visited = true;
            }
        }
        if (y > 0) {
            // Add above
            Node up = getNode(x, y - 1);
            if (!up.visited && !up.isWall()) {
                toVisit.add(up);
                up.parent = n;
                up.visited = true;
            }
        }
    }

    /**
     * returns true and displays distances from start to goal if a path exists
     * returns false if no path exists between start and goal
     *
     * @return
     */
    public boolean calcDistances() {
        return false;
    }

    @Override
    public Path solveMaze(Cell[][] cells) {
        return findPath();
    }


    public class Node {
        int x, y;
        boolean visited = false;
        char data;
        Node parent;

        public Node(int x, int y, char data) {
            this.x = x;
            this.y = y;
            this.data = data;
        }

        public boolean isStart() {
            return data == 'S';
        }

        public boolean isWall() {
            return data == '1';
        }

        public boolean isTarget() {
            return data == 'T';
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", data=" + data +
                    '}';
        }
    }

    public static class BFSData {
        Node current;
        Node[][] maze;
        Path path;

        public BFSData(Node[][] maze, Node current) {
            this.maze = maze;
            this.current = current;

        }

        public BFSData(Node[][] maze, Path path) {
            this.maze = maze;
            this.path = path;
        }
    }
}
