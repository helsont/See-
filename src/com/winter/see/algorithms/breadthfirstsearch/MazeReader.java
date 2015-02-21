package com.winter.see.algorithms.breadthfirstsearch;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class MazeReader {

    private static boolean printText;
    private static MazeReader mapReader;

    private static MazeReader get() {
        return mapReader == null ? mapReader = new MazeReader() : mapReader;
    }

    /**
     * Reads the file "fileName".txt and sees places it into a 2D array
     */
    public static char[][] readFile(BufferedReader reader) throws IOException {
        get();
        /** An ArrayList composed of Strings */
        // Inefficient? Why not just make it chars?
        ArrayList<String> lines = new ArrayList<String>();
        // Max width of a row
        int width = 0;
        // Height of the file
        int height = 0;

        while (true) {
            // Read the line
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }
            // add every line except for comments
            if (!line.startsWith("#")) {
                line = line.replaceAll("\\s", "");
                if (!line.isEmpty()) {
                    lines.add(line);
                    width = Math.max(width, line.length());
                }
            }
        }

        height = lines.size();
        // Now we know the width and height, so make the matrix
        char[][] matrix = new char[width][height];
        // for each row and column, insert the char.
        for (int y = 0; y < height; y++) {
            String line = (String) lines.get(y);
            if (line.charAt(0) == ' ')
                continue;
            for (int x = 0; x < line.length(); x++) {
                char ch = line.charAt(x);
                // Skip whitespace
                if (ch == ' ')
                    continue;
                matrix[x][y] = ch;
                if (printText)
                    System.out.print(ch);
            }
            if (printText)
                System.out.println();
        }
        return matrix;
    }

    public static MazeReader printTextFile(boolean bool) {
        printText = bool;
        return get();
    }

    public static boolean getPrintTextFile() {
        return printText;
    }
}
