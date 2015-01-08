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
import javax.swing.JOptionPane;
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
	private AbstractMazeGenerator generator;

	public ExportPanel(AbstractMazeGenerator generator,
			final MazeComponent panel) {
		this.generator = generator;
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
				File file = null;
				try {
					StringBuilder br = new StringBuilder();
					for (int x = 0; x < getGenerator().maze.length; x++) {
						for (int y = 0; y < getGenerator().maze[0].length; y++) {
							br.append(getGenerator().maze[y][x].value);
						}
						br.append("\n");
					}

					String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss")
							.format(Calendar.getInstance().getTime());
					file = new File(getGenerator().getType() + timeLog
							+ ".txt");

					// This will output the full path where the file will be
					// written to.
					writer = new BufferedWriter(new FileWriter(file));
					writer.write(br.toString());
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						// Close the writer regardless of what happens.
						writer.close();
						JOptionPane.showConfirmDialog(null,
								"Exporting completed: " + file.getName(),
								"Success", JOptionPane.CLOSED_OPTION);

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
	}

	private AbstractMazeGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(AbstractMazeGenerator generator) {
		this.generator = generator;
	}
}
