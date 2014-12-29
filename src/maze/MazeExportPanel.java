package maze;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MazeExportPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8142112191243205707L;
	private AbstractMazeGenerator generator;

	public MazeExportPanel(final AbstractMazeGenerator generator) {
		this.generator = generator;

		JButton export = new JButton("Export");
		add(export);
		export.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BufferedWriter writer = null;
				try {
					String output = "";
					
					for (int x = 0; x < generator.maze.length; x++) {
						for (int y = 0; y < generator.maze[0].length; y++) {
							output += generator.maze[x][y].value;
						}
						output+="\n";
					}

					String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss")
							.format(Calendar.getInstance().getTime());
					File logFile = new File("maze" + timeLog + ".txt");

					// This will output the full path where the file will be
					// written to...
					writer = new BufferedWriter(new FileWriter(logFile));
					writer.write(output);
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						// Close the writer regardless of what happens...
						writer.close();
					} catch (Exception ex) {
					}
				}
			}
		});
	}
}
