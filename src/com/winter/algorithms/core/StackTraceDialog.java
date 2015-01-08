package com.winter.algorithms.core;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * A JOptionPane that displays the StackTrace for debugging errors.
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public class StackTraceDialog {
	public Throwable error;

	/**
	 * Creates and displays a new JOptionPane with the specified Throwable.
	 * 
	 * @param error
	 *            The throwable to display
	 */
	public StackTraceDialog(Throwable error) {
		this.error = error;
		JOptionPane.showConfirmDialog(null, getPanel(error), "Oops!",
				JOptionPane.CLOSED_OPTION);
	}

	/**
	 * Creates and displays a new JOptionPane with the specified Throwable.
	 * 
	 * @param error
	 *            The throwable to display
	 * @param title
	 *            The title of the JOptionPane
	 */
	public StackTraceDialog(Throwable error, String title) {
		this.error = error;
		JOptionPane.showConfirmDialog(null, getPanel(error), title,
				JOptionPane.CLOSED_OPTION);
	}

	private JPanel getPanel(Throwable error) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JLabel message = new JLabel("An error has occured:");
		JTextArea area = new JTextArea();
		area.setColumns(10);
		area.setRows(10);

		JScrollPane scrollPane = new JScrollPane(area);
		area.setEditable(false);
		area.setText(getStackTrace(error));
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(message, BorderLayout.PAGE_START);
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(400, 200));
		return panel;
	}

	private String getStackTrace(Throwable error) {
		StringBuilder builder = new StringBuilder(100);
		StackTraceElement[] errs = error.getStackTrace();

		builder.append(error.toString() + "\n");
		for (int i = 0; i < errs.length; i++) {
			builder.append(errs[i] + "\n");
		}
		return builder.toString();
	}
}
