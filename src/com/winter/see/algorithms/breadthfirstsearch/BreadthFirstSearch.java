package com.winter.see.algorithms.breadthfirstsearch;

import com.winter.see.core.AlgorithmController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * For an algorithms class.
 */
public class BreadthFirstSearch {

    public Node[][] maze;
    private Queue<Node> toVisit;
    private Node start;
    private AlgorithmController.VisualHang hang;
    private Path path;
    private boolean noPath = true;
    private static int LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
    private char[][] chars;

    public BreadthFirstSearch() {
        toVisit = new LinkedList<Node>();
    }

    public BreadthFirstSearch(BufferedReader br) throws IOException {
        this(MazeReader.readFile(br));
    }

    public BreadthFirstSearch(char[][] chars) {
        this();
        this.chars = chars;
    }

    public void init() {
        maze = new Node[chars.length][chars[0].length];
        // System.out.println(Arrays.deepToString(chars));
        for (int x = 0; x < chars.length; x++) {
            for (int y = 0; y < chars[0].length; y++) {
                maze[x][y] = new Node(x, y, chars[x][y]);
                if (maze[x][y].isStart()) {
                    start = maze[x][y];
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

//    private Path findPath() {
//        toVisit.clear();
//        start.visited = true;
//        start.distance = 0;
//        toVisit.add(start);
//        while (!toVisit.isEmpty()) {
//            Node curr = toVisit.poll();
//            hang.hang(new BFSData(maze, curr));
//            if (curr.isTarget()) {
//                // get the path
//                noPath = false;
//                printBackwardsPath(curr);
//                return path = getPath(curr);
//            }
//            addNeighbors(curr);
//        }
//        return null;
//    }

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
        if (x >= 0 && x <= mazeWidth() - 1 && y <= mazeHeight() - 1 && y >= 0) {
            return maze[x][y];
        }
        return null;
    }

    private boolean calculateDistances() {
        int dist = 1;
        toVisit.clear();
        start.visited = true;
        start.distance = 0;
        toVisit.add(start);
        while (!toVisit.isEmpty()) {
            Node curr = toVisit.poll();
            dist = curr.distance + 1;
            hang.hang(new BFSData(maze, curr));
            if (curr.isTarget()) {
                noPath = false;
                path = getPath(curr);
            }
            calcNeighbors(curr, dist);
        }
        return noPath;
    }

    private void calcNeighbors(Node n, int dist) {
        int x = n.x;
        int y = n.y;
        Node[] dirs = new Node[4];
        dirs[LEFT] = getNode(x - 1, y);
        dirs[UP] = getNode(x, y - 1);
        dirs[RIGHT] = getNode(x + 1, y);
        dirs[DOWN] = getNode(x, y + 1);
        for (int i = 0; i < dirs.length; i++) {
            if (dirs[i] == null)
                continue;
            Node curr = dirs[i];
            int prevDistance = curr.distance;
            // We might have found a better way to get to this
            // spot
            if (!dirs[i].isWall() && ((curr.visited && dist < prevDistance) || (!curr.visited))) {
                toVisit.add(curr);
                curr.parent = n;
                curr.distance = dist;
                curr.visited = true;
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
        return calculateDistances();
    }

    public Path solveMaze() {
        calcDistances();
        return path;
    }

    public void reset() {
        path = null;
        init();
        noPath = true;
    }

    public class Node {
        int x, y;
        boolean visited = false;
        char data;
        Node parent;
        int distance = Integer.MAX_VALUE;

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

        public boolean isBlank() {
            return !(isWall() || isStart() || isTarget());
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
