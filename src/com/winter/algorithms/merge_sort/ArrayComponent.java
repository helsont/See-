package com.winter.algorithms.merge_sort;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import com.winter.algorithms.core.AlgorithmComponent;
import com.winter.algorithms.merge_sort.SorterThread.Values;



/**
 * This component draws an array and marks two elements in the array.
 */
public class ArrayComponent extends JComponent implements AlgorithmComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double marked1;
	private Double marked2;
	private Double[] values;

	public synchronized void paintComponent(Graphics g) {
		if (values == null) {
			return;
		}
		Graphics2D g2 = (Graphics2D) g;
		int width = getWidth() / values.length;
		for (int i = 0; i < values.length; i++) {
			Double v = values[i];
			Rectangle2D bar = new Rectangle2D.Double(width * i, 0, width, v);
			if (v == marked1 || v == marked2)
				g2.fill(bar);
			else
				g2.draw(bar);
		}
	}

	/**
	 * Sets the values to be painted.
	 * 
	 * @param values
	 *            the array of values to display
	 * @param marked1
	 *            the first marked element
	 * @param marked2
	 *            the second marked element
	 */
	@Override
	public synchronized void setValues(Object vals) {
		this.values = ((Values) vals).values.clone();
		this.marked1 = ((Values) vals).marked1;
		this.marked2 = ((Values) vals).marked2;
		repaint();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
