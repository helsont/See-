package com.winter.algorithms.maze.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.winter.algorithms.maze.AbstractMazeGenerator;

/**
 * Exports the current maze and also contains a Radio button to show extra
 * visual information of the maze.
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public class ExportPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8142112191243205707L;

	public ExportPanel(final AbstractMazeGenerator generator,
			final MazeComponent panel) {

		JButton export = new JButton("Export");
		JCheckBox showExtra = new JCheckBox("Extra");
		add(export);
		add(showExtra);
		showExtra.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					panel.showExtra = true;
				} else
					panel.showExtra = false;
				panel.repaint();
			}
		});
		export.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedWriter writer = null;

				try {
					StringBuilder br = new StringBuilder();
					for (int x = 0; x < generator.maze.length; x++) {
						for (int y = 0; y < generator.maze[0].length; y++) {
							br.append(generator.maze[y][x].value);
						}
						br.append("\n");
					}

					String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss")
							.format(Calendar.getInstance().getTime());
					File logFile = new File(generator.getType() + timeLog
							+ ".txt");

					// This will output the full path where the file will be
					// written to.
					writer = new BufferedWriter(new FileWriter(logFile));
					writer.write(br.toString());
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						// Close the writer regardless of what happens.
						writer.close();
					} catch (Exception ex) {
						System.out.println(ex.getLocalizedMessage());
					}
				}
			}
		});
	}
}
