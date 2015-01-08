package com.winter.algorithms.core;

/**
 * <p>
 * This is the visual aspect of the algorithm. The <code>setValues</code>
 * receives an Object that has whatever values necessary to draw the algorithm.
 * Example code:
 * <p>
 * <blockquote>
 * 
 * <pre>
 * public void setValues(Object vals) {
 * 	MergeSortValues algValues = (MergeSortValues) vals;
 * 	this.values = algValues.values;
 * 	this.marked1 = algValues.marked1;
 * 	this.marked2 = algValues.marked2;
 * 	repaint();
 * }
 * </pre>
 * 
 * </blockquote>
 * 
 * <p>
 * The repaint() call is necessary to update the screen once the values have
 * been received.
 * <p>
 * Requirements for a subclass of this interface:
 * <li>must extend JComponent.
 * <li>must override <code>paintComponent</code> in JComponent.
 * </p>
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */

public interface AlgorithmComponent {
	/**
	 * Updates the view of the algorithm to the specified values contained
	 * within an value holder.
	 * 
	 * @param vals
	 *            A class that has the values to update the view.
	 */
	public void setValues(Object vals);

	/**
	 * Initializes the algorithm component after the JFrame has been prepared.
	 */
	public void initializeComponent();

}
