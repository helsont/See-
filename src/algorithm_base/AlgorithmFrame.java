package algorithm_base;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;
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
 * </p>
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public class AlgorithmFrame extends JFrame {

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
			final AlgorithmRunner algorithm, int width, int height) {
		this(panel, algorithm, null, width, height);

	}

	public AlgorithmFrame(final AlgorithmComponent panel,
			final AlgorithmRunner algorithm, JPanel south) {
		this(panel, algorithm, south, DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);

	}

	public AlgorithmFrame(AlgorithmComponent panel, AlgorithmRunner algorithm) {
		this(panel, algorithm, DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
	}

	public AlgorithmFrame(AlgorithmComponent panel,
			final AlgorithmRunner algorithm, JPanel south, int width, int height) {
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		add((JComponent) south, BorderLayout.SOUTH);
		add((JComponent) panel, BorderLayout.CENTER);
		// ((JComponent) panel).setSize(((JComponent)panel, height);

		final JButton stepButton = new JButton("Step");
		final JButton runButton = new JButton("Run");
		final JButton instantButton = new JButton("Instant");
		final JButton resetButton = new JButton("Reset");

		JPanel controls = new JPanel();
		controls.add(stepButton);
		controls.add(runButton);
		controls.add(resetButton);
		controls.add(instantButton);

		// Create the slider
		final int min_DELAY = 1;
		final int max_DELAY = 2000;
		final int def_DELAY = 100;
		JSlider speed = new JSlider(JSlider.HORIZONTAL, min_DELAY, max_DELAY,
				def_DELAY);
		speed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				System.out.println(source.getValue());
				// Because there must be a sleep time of at least 1
				algorithm.setDelay(max_DELAY - source.getValue() + min_DELAY);
			}
		});
		controls.add(speed);

		algorithm.setDelay(def_DELAY);
		add(controls, BorderLayout.NORTH);

		final BlockingQueue<AlgorithmState> queue = new LinkedBlockingQueue<AlgorithmState>();
		queue.add(AlgorithmState.STEP);

		algorithm.setQueue(queue);

		final AlgorithmThread thread = new AlgorithmThread(algorithm);

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
				runButton.setEnabled(true);
				queue.add(AlgorithmState.STEP);
				algorithm.reset();
				thread.start();
			}
		});

		instantButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				algorithm.setDelay(0);
			}
		});

		setVisible(true);
		setLocationRelativeTo(null);
		this.add(south, BorderLayout.SOUTH);
	}

	public static final int DEFAULT_FRAME_WIDTH = 700;
	public static final int DEFAULT_FRAME_HEIGHT = 700;
}
