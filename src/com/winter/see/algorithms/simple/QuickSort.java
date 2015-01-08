package com.winter.see.algorithms.simple;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JPanel;

import com.winter.see.core.AlgorithmComponent;
import com.winter.see.core.AlgorithmExecutor;
import com.winter.see.core.AlgorithmFrame;
import com.winter.see.core.AlgorithmController.VisualHang;

@SuppressWarnings("serial")
public class QuickSort extends JPanel implements AlgorithmComponent,
		AlgorithmExecutor {

	private VisualHang hang;
	private double[] vals;
	private static int MAX_VALUE = 10;
	private static int SIZE = 10;

	public QuickSort(double[] vals) {
		this.vals = vals;
	}

	public static void main(String[] args) {
		QuickSort quickSort = new QuickSort(getRandomValues());
		new AlgorithmFrame(quickSort, quickSort);
	}

	private static double[] getRandomValues() {
		double[] randomValues = new double[SIZE];
		Random r = new Random();
		for (int i = 0; i < SIZE; i++) {
			randomValues[i] = r.nextDouble() * MAX_VALUE;
		}
		return randomValues;
	}

	private void sort(int low, int high) {
		System.out.println(low + "-" + high);
		int mid = low + (high - low) / 2;
		double pivot = vals[mid];
		int i = low;
		int j = high;

		while (i < j) {
			hang.hang(new SortValues(i, j, mid));
			while (vals[i] < pivot) {
				i++;
				hang.hang(new SortValues(i, j, mid));
			}
			while (vals[j] > pivot) {
				j--;
				hang.hang(new SortValues(i, j, mid));
			}
			hang.hang(new SortValues(i, j, mid));
			if (i <= j) {
				swap(i, j);
				i++;
				j--;
			}
			hang.hang(new SortValues(i, j, mid));
			System.out.println("pivot=" + pivot);
		}
		if (low < j)
			sort(low, j);
		if (i < high)
			sort(i, high);
	}

	private void swap(int i, int j) {
		double temp = vals[i];
		vals[i] = vals[j];
		vals[j] = temp;
	}

	@Override
	public void runAlgorithm() {
		sort(0, vals.length - 1);
	}

	@Override
	public void setHang(VisualHang hang) {
		this.hang = hang;
	}

	// Graphics related things below here
	private SortValues graphicValues;

	@Override
	public void updateComponent(Object values) {
		setValues(values);
		repaint();
	}

	@Override
	public void updateComponentEnded() {
		updateComponentStarted();
	}

	@Override
	public void updateComponentStarted() {
		setValues(new SortValues(-1, -1, -1));
		repaint();
	}

	@Override
	public void reset() {
		vals = getRandomValues();
	}

	@Override
	public void setValues(Object vals) {
		this.graphicValues = (SortValues) vals;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (graphicValues == null)
			return;

		Graphics2D g2 = (Graphics2D) g;
		int width = getWidth() / vals.length;
		int height = (int) (getHeight() / MAX_VALUE);
		for (int index = 0; index < vals.length; index++) {
			Double v = (double) vals[index];

			Rectangle2D bar = new Rectangle2D.Double(width * index, getHeight()
					- (v * height), width, v * height);

			if (index == graphicValues.i) {
				g2.setColor(Color.blue);
				g2.fill(bar);
				g.setColor(Color.black);
				g2.drawString("" + index, (int) bar.getX() + width / 2,
						(int) bar.getY());
			}
			if (index == graphicValues.j) {
				g2.setColor(Color.green);
				g2.fill(bar);
				g.setColor(Color.black);
				g2.drawString("" + index, (int) bar.getX() + width / 2,
						(int) bar.getY());
			} else if (index == graphicValues.mid) {
				g.setColor(Color.red);
				g2.fill(bar);
				g.setColor(Color.black);
				g2.drawString("" + index, (int) bar.getX() + width / 2,
						(int) bar.getY());
			} else
				g2.draw(bar);
		}
	}

	@Override
	public void initializeComponent() {
		// Nothing needed here.
	}

	class SortValues {
		double i, j, mid;

		public SortValues(double i, double j, double mid) {
			this.i = i;
			this.j = j;
			this.mid = mid;
		}
	}
}
