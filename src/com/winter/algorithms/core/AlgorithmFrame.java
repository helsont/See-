package com.winter.algorithms.core;

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
	public static boolean PRODUCTION_MODE = false;
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
		algorithmThread = thread;
		stateQueue = new LinkedBlockingQueue<AlgorithmState>();
		thread.setQueue(stateQueue);
		thread.setDelay(def_DELAY);

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

		center.init();
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
			runButton = new JButton("Run");
			instantButton = new JButton("Instant");
			resetButton = new JButton("Reset");
			stepButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					stateQueue.add(AlgorithmState.STEP);
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
					algorithmThread.reset();
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

		public void enableRun() {
			runButton.setEnabled(true);
		}
	}
}
