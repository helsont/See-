package com.winter.see.algorithms.simple;

import com.winter.see.core.AlgorithmComponent;
import com.winter.see.core.AlgorithmController.VisualHang;
import com.winter.see.core.AlgorithmExecutor;
import com.winter.see.core.AlgorithmFrame;

import javax.swing.*;
import java.awt.*;

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
		g.drawString("" + current, getWidth() / 2, getHeight() / 2);
	}

	@Override
	public void initializeComponent() { /* Nothing special needed*/ }
}
