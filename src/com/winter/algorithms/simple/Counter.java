package com.winter.algorithms.simple;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.winter.algorithms.core.AlgorithmComponent;
import com.winter.algorithms.core.AlgorithmController.VisualHang;
import com.winter.algorithms.core.AlgorithmExecutor;
import com.winter.algorithms.core.AlgorithmFrame;

@SuppressWarnings("serial")
public class Counter extends JPanel implements AlgorithmComponent,
		AlgorithmExecutor {

	private int current;
	private int max = 10;
	private VisualHang hang;

	public static void main(String[] args) {
		Counter alg = new Counter();
		new AlgorithmFrame(alg, alg);
	}

	@Override
	public void runAlgorithm() {
		while (current < max) {
			hang.hang(current);
			current++;
		}
	}

	@Override
	public void setHang(VisualHang hang) {
		this.hang = hang;
	}

	@Override
	public void updateComponent(Object values) {
		repaint();
	}

	@Override
	public void updateComponentEnded() {
		repaint();
	}

	@Override
	public void updateComponentStarted() {
		repaint();
	}

	@Override
	public void reset() {
		current = 0;
	}

	@Override
	public void setValues(Object vals) {

	}

	@Override
	protected void paintComponent(Graphics g) {
		for (int i = 0; i < current; i++) {
			g.drawString("" + current, getWidth() / 2, getHeight() / 2);
		}
	}

	@Override
	public void initializeComponent() {

	}
}
