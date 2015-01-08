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
