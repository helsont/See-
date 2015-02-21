package com.winter.see.algorithms.breadthfirstsearch;

import com.winter.see.algorithms.maze.Cell;
import com.winter.see.core.AlgorithmController;
import com.winter.see.core.AlgorithmExecutor;

import java.io.*;
import java.net.URL;

public class MazeSolverThread implements AlgorithmExecutor {

    private MazeComponent panel;
    private BreadthFirstSearch search;
    private Cell[][] maze;
    private Cell curr;
    private String filename = "maze3.data";
    private Path path;

    public MazeSolverThread(final MazeComponent panel) throws IOException {
        this.panel = panel;
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(
                        new File(getPath()))));
        search = new BreadthFirstSearch(br);
    }

    private String getPath() {

        // http://stackoverflow.com/questions/778187/getting-directory-path-to-class-file-containing-main
        URL main = MazeSolverThread.class.getResource(filename);
        File path = new File(main.getPath());
        return path.getPath();
    }

    @Override
    public void runAlgorithm() {
        path = search.solveMaze(maze);
        System.out.println("Completed");
        System.out.println(path);
    }

    @Override
    public void reset() {
        // No reset.
    }

    @Override
    public void updateComponent(Object values) {
        panel.setValues(values);
    }

    @Override
    public void setHang(AlgorithmController.VisualHang hang) {
        search.setHang(hang);
    }

    @Override
    public void updateComponentEnded() {
        panel.setValues(new BreadthFirstSearch.BFSData(search.maze, path));
    }

    @Override
    public void updateComponentStarted() {
        panel.setValues(new BreadthFirstSearch.BFSData(search.maze, (BreadthFirstSearch.Node) null));
    }

    @Override
    public String toString() {
        return maze.toString();
    }
}