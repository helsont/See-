package com.winter.algorithms.maze.gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Transient;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.winter.algorithms.core.AlgorithmFrame;
import com.winter.algorithms.maze.AbstractMazeGenerator;
import com.winter.algorithms.maze.algorithms.DepthFirstMaze;
import com.winter.algorithms.maze.algorithms.DivisionMaze;
import com.winter.algorithms.maze.algorithms.GrowingTreeMaze;
import com.winter.algorithms.maze.algorithms.GrowingTreeMaze.Parameters;
import com.winter.algorithms.maze.algorithms.HuntAndKillMaze;

public class SelectGeneratorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3549317837918446112L;
	private AbstractMazeGenerator maze;
	private MazeComponent panel;
	private MazeThread thread;
	private AbstractMazeGenerator[] types = new AbstractMazeGenerator[] {
			new DepthFirstMaze(3, 3), new GrowingTreeMaze(3, 3),
			new DivisionMaze(3, 3), new HuntAndKillMaze(3, 3) };

	public SelectGeneratorPanel(AbstractMazeGenerator maze,
			MazeComponent panel, MazeThread thread) {
		this.maze = maze;
		this.panel = panel;
		this.thread = thread;
		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		setBorder(BorderFactory.createLineBorder(Color.black));
		String[] dat = new String[types.length];
		for (int i = 0; i < dat.length; i++) {
			dat[i] = types[i].getType();
		}
		final JList<String> list = new JList<String>(dat);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false)
					return;
				int NONE_SELECTED = -1;
				if (list.getSelectedIndex() != NONE_SELECTED) {
					setMaze(list.getSelectedIndex());
				}
			}
		});
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(120, 80));
		add(listScroller);

		final JFormattedTextField width = new JFormattedTextField();

		width.setValue("width");
		width.setEnabled(false);
		width.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(final MouseEvent e) {
				width.setEnabled(true);
				width.requestFocusInWindow();
				System.out.println("Width pressed");
				width.setValue(null);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {

						int offset = width.viewToModel(e.getPoint());
						width.setCaretPosition(offset);
						width.getCaret().setVisible(true);
					}
				});
			}

		});
		width.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				try {
					width.commitEdit();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (width.getValue() == null) {
					width.setValue("width");
					width.setEnabled(false);
					width.getCaret().setVisible(false);

				}
			}
		});
		width.setColumns(3);
		add(width);

		final JFormattedTextField height = new JFormattedTextField();

		height.setValue("height");
		height.setEnabled(false);
		height.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(final MouseEvent e) {
				height.requestFocusInWindow();
				height.setEnabled(true);
				height.setValue(null);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						height.getCaret().setVisible(true);
					}
				});
			}

		});
		height.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					height.commitEdit();
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				if (height.getValue() == null) {
					height.setValue("height");
					height.setEnabled(false);
					height.getCaret().setVisible(false);
				}
			}
		});
		height.setColumns(3);
		add(height);
		final ErrorText err = new ErrorText();
		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int w = Integer.parseInt((String) width.getValue());
					int h = Integer.parseInt((String) height.getValue());
					if (w % 2 == 0 || h % 2 == 0) {
						err.setText("Only odd numbers can be used.");
					} else if (w < 3 || h < 3) {
						err.setText("Must specify values greater than 3.");
					} else {
						createMaze(list.getSelectedIndex(), w, h);
					}
				} catch (NumberFormatException ex) {
					err.setText("Whole numbers please.");

				}

			}
		});
		add(button);
		add(err.getErr());
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		validate();
	}

	private class ErrorText implements ActionListener {
		private JTextArea err;
		private int visible;
		private int MAX_VISIBLE = 5000;
		private Timer t;

		public JTextArea getErr() {
			return err;
		}

		public ErrorText() {
			err = new JTextArea();
			err.setLineWrap(true);
			err.setWrapStyleWord(true);
			err.setVisible(false);
			err.setForeground(Color.red);
			err.setBackground(new Color(237, 237, 237));
			err.setMaximumSize(new Dimension(150, 150));
			t = new Timer(1, this);
		}

		public void setText(String text) {
			err.setText(text);
			err.setVisible(true);
			visible = 0;
			t.start();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (visible < MAX_VISIBLE)
				visible++;
			else {
				visible = 0;
				err.setVisible(false);
			}
		}

	}

	private void setMaze(int selectedIndex) {
		int currW = maze.size_col;
		int currH = maze.size_row;
		createMaze(selectedIndex, currW, currH);
	}

	private int getCurrentMazeType() {
		for (int i = 0; i < types.length; i++) {
			if (types[i].getType().equals(maze.getType())) {
				return i;
			}
		}
		return 1;
	}

	private void createMaze(int i, int width, int height) {
		if (i == -1) {
			i = getCurrentMazeType();
		} else
			maze = types[i];
		if (maze.getType().equals("Growing Tree Maze")) {
			createDropDown();
		} else if (petList != null)
			petList.setVisible(false);
		maze.size_col = width;
		maze.size_row = height;
		maze.resize(width, height);
		thread.setGenerator(maze);
		panel.repaint();
		AlgorithmFrame.changeExecutor(new MazeThread(maze, panel, maze.maze));
	}

	private void createMaze(int i, Parameters p) {
		if (i == -1) {
			i = getCurrentMazeType();
		} else
			maze = types[i];
		((GrowingTreeMaze) maze).setParameter(p);
		thread.setGenerator(maze);
		panel.repaint();
		AlgorithmFrame.changeExecutor(new MazeThread(maze, panel, maze.maze));
	}

	private JComboBox<Parameters> petList;

	private void createDropDown() {
		Parameters[] params = GrowingTreeMaze.Parameters.values();
		// Create the combo box, select item at index 4.
		// Indices start at 0, so 4 specifies the pig.
		petList = new JComboBox<Parameters>(params);
		petList.setSelectedIndex(0);
		petList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createMaze(getCurrentMazeType(),
						(Parameters) petList.getSelectedItem());
			}
		});
		add(petList);

		petList.setPreferredSize(new Dimension(120, 300));
		validate();
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(130, 450);
	}
}
