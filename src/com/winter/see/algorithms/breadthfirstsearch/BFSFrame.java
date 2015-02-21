package com.winter.see.algorithms.breadthfirstsearch;

import com.winter.see.core.AlgorithmFrame;

import java.io.IOException;

/**
 * Created by winter on 2/20/15.
 */
public class BFSFrame {

    public static void main(String[] args) throws IOException {
        MazeComponent panel = new MazeComponent();
        MazeSolverThread thread = new MazeSolverThread(panel);
        new AlgorithmFrame(panel, thread);
    }
}
