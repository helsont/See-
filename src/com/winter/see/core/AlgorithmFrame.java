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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * A JFrame that contains the JComponent AlgorithmComponent and two JPanels, one
 * aligned on BorderLayout.NORTH which has 'Step', 'Run', 'Instant', and 'Reset'
 * buttons, and one aligned on BorderLayout.SOUTH which can be defined by the
 * programmer to be anything he wants.
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public class AlgorithmFrame extends JFrame {

	private static DashboardPanel dashboardPanel;
	private static AlgorithmThread algorithmThread;
	private LinkedBlockingQueue<AlgorithmState> stateQueue;

	private static final int DEFAULT_FRAME_WIDTH = 700;
	private static final int DEFAULT_FRAME_HEIGHT = 700;
	private static final int min_DELAY = 1, max_DELAY = 500, def_DELAY = 100;
	public static boolean SHOW_ERROR_DIALOGS = true;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create an JFrame to display the algorithm.
	 * 
	 * @param panel
	 * @param algorithm
	 * @param width
	 * @param height
	 */
	public AlgorithmFrame(final AlgorithmComponent panel,
			final AlgorithmExecutor algorithm, int width, int height) {
		this(panel, algorithm, null, null, null, width, height);

	}

	/**
	 * 
	 * @param panel
	 *            The display of the algorithm
	 * @param algorithm
	 *            The algorithm itself
	 * @param south
	 *            An extra JPanel to have extra settings, positionally at
	 *            BorderLayout.PAGE_END
	 */
	public AlgorithmFrame(final AlgorithmComponent panel,
			final AlgorithmExecutor algorithm, JPanel south) {
		this(panel, algorithm, null, south, null, DEFAULT_FRAME_WIDTH,
				getDefaultFrameHeight());

	}

	/**
	 * 
	 * @param panel
	 *            The display of the algorithm
	 * @param algorithm
	 *            The algorithm itself
	 */
	public AlgorithmFrame(AlgorithmComponent panel, AlgorithmExecutor algorithm) {
		this(panel, algorithm, DEFAULT_FRAME_WIDTH, getDefaultFrameHeight());
	}

	/**
	 * 
	 * @param center
	 *            BorderLayout.CENTER
	 * @param algorithm
	 * @param west
	 * @param south
	 *            A panel BorderLayout.PAGE_END
	 * @param east
	 *            A panel BorderLayout.LINE_START
	 * @param width
	 * @param height
	 */
	public AlgorithmFrame(AlgorithmComponent center,
			final AlgorithmExecutor algorithm, JPanel west, JPanel south,
			JPanel east, int width, int height) {

		final AlgorithmThread thread = new AlgorithmThread(algorithm,
				new AlgorithmThread.OnCompletion() {

					@Override
					public void onComplete() {
						System.out.println("Completed!");
						// Because there must be a sleep time of at least 1
						algorithmThread.setDelay(max_DELAY
								- dashboardPanel.getSpeedValue() + min_DELAY);
						dashboardPanel.setButtonsEnabledState(false);
						dashboardPanel.enableReset();
					}
				});
		thread.setDelay(max_DELAY - def_DELAY + min_DELAY);
		algorithmThread = thread;
		stateQueue = new LinkedBlockingQueue<AlgorithmState>();
		thread.setQueue(stateQueue);

		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add((JComponent) center, BorderLayout.CENTER);

		if (west != null)
			add((JComponent) west, BorderLayout.LINE_START);
		if (south != null)
			add((JComponent) south, BorderLayout.PAGE_END);
		if (east != null)
			add((JComponent) east, BorderLayout.LINE_END);

		dashboardPanel = new DashboardPanel();
		add(dashboardPanel, BorderLayout.NORTH);

		setVisible(true);
		setLocationRelativeTo(null);

		center.initializeComponent();
		thread.start();
	}

	/**
	 * Adds a JPanel on the left side of the screen.
	 * 
	 * @param panel
	 * @return
	 */
	public AlgorithmFrame addPanelEast(JPanel panel) {
		add(panel, BorderLayout.LINE_END);
		return this;
	}

	/**
	 * Adds a JPanel on the right side of the screen.
	 * 
	 * @param panel
	 * @return
	 */
	public AlgorithmFrame addPanelWest(JPanel panel) {
		add(panel, BorderLayout.LINE_START);
		pack();
		return this;
	}

	/**
	 * Adds a JPanel on the lower side of the screen.
	 * 
	 * @param panel
	 * @return
	 */
	public AlgorithmFrame addPanelSouth(JPanel panel) {
		add(panel, BorderLayout.PAGE_END);
		pack();
		return this;
	}

	/**
	 * Changes the current algorithm executor to the specified one.
	 * 
	 * @param executor
	 *            The new algorithm.
	 */
	public static void changeExecutor(AlgorithmExecutor ex) {
		algorithmThread.changeExecutor(ex);
		dashboardPanel.setButtonsEnabledState(true);
	}

	/**
	 * Changes the current algorithm executor to the specified one.
	 * 
	 * @param executor
	 *            The new algorithm.
	 * @param onChange
	 *            Method to perform once the change has been completed. This is
	 *            helpful when other components must be alerted of the change or
	 *            updated when the change occurs.
	 * 
	 */
	public static void changeExecutor(AlgorithmExecutor ex,
			OnChangeExecutor onChange) {
		algorithmThread.changeExecutor(ex);
		dashboardPanel.setButtonsEnabledState(true);
		onChange.onChangeExecutor();
	}

	/**
	 * Stops the current AlgorithmExecutor from running.
	 */
	public static void stop() {
		algorithmThread.stop();
	}

	/**
	 * Like thread.start(), you can only call this method once. Invokes the
	 * AlgorithmRunner's <code>run</code> method.
	 */
	public static void start() {
		algorithmThread.start();
	}

	public static int getDefaultFrameHeight() {
		return DEFAULT_FRAME_HEIGHT;
	}

	/**
	 * Used to specify an action to perform once the AlgorithmExecutor has
	 * changed. Let components/GUI know of the change here.
	 * 
	 * @author Helson Taveras hjt2113@columbia.edu
	 * 
	 */
	public static interface OnChangeExecutor {
		/**
		 * Method to perform once the change has been completed. This is helpful
		 * when other components must be alerted of the change or updated when
		 * the change occurs.
		 */
		public void onChangeExecutor();
	}

	private class DashboardPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6412929450019213376L;
		private JButton stepButton;
		private JButton runButton;
		private JButton instantButton;
		private JButton resetButton;
		private JSlider speedSlider;

		public DashboardPanel() {
			stepButton = new JButton("Step");
			stepButton
					.setToolTipText("Take one step in the execution of the algorithm.");
			runButton = new JButton("Run");
			runButton
					.setToolTipText("Executes the algorithm at the speed specified "
							+ "without stopping. Press Step at any time to stop the execution.");
			instantButton = new JButton("Instant");
			instantButton
					.setToolTipText("Executes the algorithm as quickly as "
							+ "possible, ignoring the set speed.");
			resetButton = new JButton("Reset");
			resetButton.setToolTipText("Resets the algorithm.");
			stepButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					stateQueue.add(AlgorithmState.STEP);
					if (algorithmThread.isRunningAlgorithm())
						runButton.setEnabled(true);
				}
			});

			runButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					runButton.setEnabled(false);
					stateQueue.add(AlgorithmState.RUN);
				}
			});

			resetButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					algorithmThread.restart();
					setButtonsEnabledState(true);
				}
			});

			instantButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (stateQueue.peek() == null) {
						try {
							stateQueue.put(AlgorithmState.RUN);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					algorithmThread.setDelay(0);
				}
			});

			speedSlider = new JSlider(JSlider.HORIZONTAL, min_DELAY, max_DELAY,
					def_DELAY);
			speedSlider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					System.out.println(source.getValue());
					// Because there must be a sleep time of at least 1
					algorithmThread.setDelay(max_DELAY - source.getValue()
							+ min_DELAY);
				}
			});
			add(stepButton);
			add(runButton);
			add(resetButton);
			add(instantButton);
			add(speedSlider);
		}

		public int getSpeedValue() {
			return speedSlider.getValue();
		}

		public void setButtonsEnabledState(boolean val) {
			stepButton.setEnabled(val);
			runButton.setEnabled(val);
			resetButton.setEnabled(val);
			instantButton.setEnabled(val);
		}

		public void enableReset() {
			resetButton.setEnabled(true);
		}
	}
}
