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
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.winter.algorithms.core.AlgorithmFrame;
import com.winter.algorithms.maze.AbstractMazeGenerator;
import com.winter.algorithms.maze.algorithms.DepthFirstMaze;
import com.winter.algorithms.maze.algorithms.DivisionMaze;
import com.winter.algorithms.maze.algorithms.GrowingTreeMaze;
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
			dat[i] = types[i].getClass().getSimpleName();
		}
		final JList<String> list = new JList<String>(dat); // data has type
															// Object[]
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
					// TODO Auto-generated catch block
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
		final JLabel error = new JLabel();
		error.setVisible(false);
		error.setForeground(Color.red);
		error.setText("<html>");
		error.setMaximumSize(new Dimension(130, 450));
		JButton button = new JButton(">");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int w = Integer.parseInt((String) width.getValue());
					int h = Integer.parseInt((String) height.getValue());
					if (w % 2 == 0 || h % 2 == 0) {
						error.setText("Only odd numbers<br>can be used.</html>");
						error.setVisible(true);
					} else if (w < 3 || h < 3) {
						error.setText("Must specify values<br>greater than 3.</html>");
						error.setVisible(true);
					} else {
						error.setVisible(false);
						createMaze(list.getSelectedIndex(), w, h);
					}
				} catch (NumberFormatException ex) {
					error.setText("Whole numbers<br> please.</html>");
					error.setVisible(true);

				}

			}
		});
		add(button);
		add(error);
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
	}

	// private int timesChanged = 0;

	private void setMaze(int selectedIndex) {
		int currW = maze.size_col;
		int currH = maze.size_row;
		createMaze(selectedIndex, currW, currH);
	}

	private void createMaze(int i, int width, int height) {
		maze = types[i];

		maze.size_col = width;
		maze.size_row = height;
		maze.resize(width, height);
		thread.setGenerator(maze);
		panel.repaint();
		AlgorithmFrame.changeExecutor(new MazeThread(maze, panel, maze.maze));
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(130, 450);
	}
}
