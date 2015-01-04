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

	private static AlgorithmThread algorithmThread;
	private static JButton rButton;
	public static final int DEFAULT_FRAME_WIDTH = 700;
	public static final int DEFAULT_FRAME_HEIGHT = 700;

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
				DEFAULT_FRAME_HEIGHT);

	}

	/**
	 * 
	 * @param panel
	 *            The display of the algorithm
	 * @param algorithm
	 *            The algorithm itself
	 */
	public AlgorithmFrame(AlgorithmComponent panel, AlgorithmExecutor algorithm) {
		this(panel, algorithm, DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
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
		final AlgorithmThread thread = new AlgorithmThread(algorithm);

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

		final JButton stepButton = new JButton("Step");
		final JButton runButton = new JButton("Run");
		final JButton instantButton = new JButton("Instant");
		final JButton resetButton = new JButton("Reset");
		rButton = resetButton;

		JPanel controls = new JPanel();
		controls.add(stepButton);
		controls.add(runButton);
		controls.add(resetButton);
		controls.add(instantButton);

		// Create the slider
		final int min_DELAY = 1;
		final int max_DELAY = 500;
		final int def_DELAY = 20;
		JSlider speed = new JSlider(JSlider.HORIZONTAL, min_DELAY, max_DELAY,
				def_DELAY);
		speed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				System.out.println(source.getValue());
				// Because there must be a sleep time of at least 1
				thread.setDelay(max_DELAY - source.getValue() + min_DELAY);
			}
		});
		controls.add(speed);

		thread.setDelay(def_DELAY);
		add(controls, BorderLayout.NORTH);

		final LinkedBlockingQueue<AlgorithmState> queue = new LinkedBlockingQueue<AlgorithmState>();

		thread.setQueue(queue);

		algorithmThread = thread;

		stepButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				queue.add(AlgorithmState.STEP);
				runButton.setEnabled(true);
			}
		});

		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				runButton.setEnabled(false);
				queue.add(AlgorithmState.RUN);
			}
		});

		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				algorithmThread.reset();
				runButton.setEnabled(true);
			}
		});

		instantButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thread.setDelay(0);
			}
		});

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
		rButton.setEnabled(true);

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
}
