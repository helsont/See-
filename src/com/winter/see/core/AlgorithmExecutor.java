/** This file is part of See*.
 *
 * See* is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * See* is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * See*. If not, see <http://www.gnu.org/licenses/>.
 */
package com.winter.see.core;

import com.winter.see.core.AlgorithmController.VisualHang;

/**
 * The actual implementation of the algorithm.
 *
 * @author Helson Taveras hjt2113@columbia.edu
 */
public interface AlgorithmExecutor {

    /**
     * Call the method to run your algorithm, like MergeSort.sort().
     */
    public void runAlgorithm();

    /**
     * The algorithm class must receive an instance of VisualHang to call the
     * {@linkplain VisualHang#hang(Object) hang} method.
     *
     * @param hang The VisualHang that will be used with this class to control
     *             the algorithm's speed.
     */
    public abstract void setHang(VisualHang hang);

    /**
     * <p/>
     * Update the values of your AlgorithmComponent. An example:
     * <p/>
     * <blockquote> drawMazePanel.setValues(new CellValues(getMazeCells(),
     * getCurrentCellX(), getCurrentCellY())); </blockquote>
     * <p/>
     * This could would invoke the DrawMazePanel's <code>setValues</code>
     * method, passing a Cell[][], the x position of the current cell being
     * traversed, and the y position of the current cell. From there the
     * DrawMazePanel can save a copy of these values and display them as it
     * wants to, such as drawing a grid and a highlighted square at the cell
     * specified by (getCurrentCellX(), getCurrentCellY()).
     *
     * @param values A wrapper class which contains useful values to be displayed
     *               on the AlgorithmComponent.
     * @see #updateComponentEnded()
     */
    public abstract void updateComponent(Object values);

    /**
     * Update the values of your AlgorithmComponent when the algorithm has
     * completed. Some algorithms call for displaying many visuals. This allows
     * for those visuals to be wiped off the screen once the algorithm has
     * completed. An example:
     * <p/>
     * <blockquote> drawMazePanel.setValues(new CellValues(getMazeCells(), -1,
     * -1)); </blockquote>
     * <p/>
     * <p/>
     * This could would invoke the DrawMazePanel's <code>setValues</code>
     * method, passing a Cell[][], the x position of the current cell being
     * traversed, and the y position of the current cell. From there the
     * DrawMazePanel can save a copy of these values and display them as it
     * wants to. A -1 could be interpreted as a null value, therefore not
     * drawing a highlighted square.
     * <p/>
     * <p/>
     * Because this method doesn't depend on the current state of the algorithm,
     * no values can be passed into it as a parameter. If we keep with our
     * example of a maze, because there is no "current position," there is no
     * need to keep track of the current cell position.
     *
     * @see #updateComponent(Object)
     */
    public abstract void updateComponentEnded();

    /**
     * Update the values of your AlgorithmComponent before the algorithm has begun.
     * Some algorithms call for displaying many visuals. This allows for those visuals to be
     * started before a STEP action is taken.
     * <p/>
     * Otherwise, you could just call {@link #updateComponentEnded()} if it
     * doesn't matter what the algorithm looks like in the beginning.
     */
    public abstract void updateComponentStarted();

    /**
     * Reinitializes the values of the algorithm to start fresh.
     */
    public void reset();

}
