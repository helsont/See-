package com.winter.algorithms.core;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Ease of use class that automatically takes care of blocking the Thread when
 * the user doesn't press {@linkplain AlgorithmState#STEP AlgorithmState.STEP}
 * or {@linkplain AlgorithmState#RUN AlgorithmState.RUN}.
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public class AlgorithmController implements Runnable {

	/**
	 * A queue that waits for AlgorithmState commands from the user.
	 */
	private LinkedBlockingQueue<AlgorithmState> queue;
	/**
	 * The AlgorithmRunner that will be run from this class.
	 */
	private AlgorithmExecutor runner;
	/**
	 * The delay of Thread.sleep
	 */
	private int delay;
	/**
	 * Controls the flow of control of the algorithm.
	 */
	private VisualHang hang;
	/**
	 * True when the queue's head is AlgorithmState.END.
	 */
	private boolean exit;
	/**
	 * If the AlgorithmExecutor is running.
	 */
	private boolean isRunning;

	/**
	 * Creates a new AlgorithmController.
	 * 
	 * @param algorithm
	 *            The algorithm to execute
	 * @param queue
	 *            The queue with AlgorithmStates
	 * @param delay
	 *            Delay of the sleep thread
	 */
	public AlgorithmController(AlgorithmExecutor algorithm,
			LinkedBlockingQueue<AlgorithmState> queue, int delay) {
		this.runner = algorithm;
		this.queue = queue;
		this.delay = delay;
	}

	/**
	 * Runs the specified AlgorithmRunner and creates a hang mechanism where
	 * {@linkplain AlgorithmState AlgorithmState}s are received from the user
	 * interaction.
	 */
	public void run() {
		runner.reset();
		queue.clear();
		hang = new VisualHang() {

			/**
			 * Takes a command from the queue and processes it. Calls
			 * updateComponent.
			 */
			@Override
			public void hang(Object o) {
				if (exit)
					return;
				// Block the thread until the user either clicks
				// 'Step' or 'Run'
				AlgorithmState command = null;
				try {
					// Get the current command
					command = queue.take();
					if (command == AlgorithmState.RUN) {
						Thread.sleep(delay);
						if (queue.peek() != AlgorithmState.STEP)
							queue.add(AlgorithmState.RUN);
					} else if (command == AlgorithmState.END) {
						// Exit the algorithm
						exit = true;
						throw new AlgorithmExitException(
								"AlgorithmState.END recieved");
					} else
						// If it's a AlgorithmState.STEP we need
						// to make sure there only one thing in there
						// at time
						queue.clear();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				} catch (IllegalMonitorStateException ex) {
					ex.printStackTrace();
				}

				// Paint the updates.
				runner.updateComponent(o);
			}
		};

		runner.setHang(hang);
		runner.updateComponentStarted();
		isRunning = true;
		try {
			runner.runAlgorithm();
		} catch (AlgorithmExitException ex) {
			// This is to be expected when
			// AlgorithmController.stop() is invoked.
			exit = false;
		} catch (Throwable ex) {
			if (AlgorithmFrame.SHOW_ERROR_DIALOGS) {
				// A pretty StackTrace for the user to see the error.
				new StackTraceDialog(ex);
			}
			// We print it anyways.
			ex.printStackTrace();
			stop();
		}
		isRunning = false;
		runner.updateComponentEnded();
		queue.clear();
	}

	/**
	 * <p>
	 * From within your AlgorithmThread, you can pass in a wrapper class into
	 * the hang method with information on the current state of your algorithm.
	 * Examples of information would include:
	 * 
	 * <li>Current node being traversed
	 * <li>Scope of nodes being accessed
	 * <li>A current value
	 * <p>
	 * The {@linkplain AlgorithmController#updateComponent(Object)
	 * updateComponent} then receives this object.
	 * 
	 * @author Helson Taveras hjt2113@columbia.edu
	 * 
	 */
	public static interface VisualHang {
		/**
		 * Pass in a wrapper class into this method (or null) with information
		 * on the current state of your algorithm.
		 * 
		 * @param settings
		 *            Wrapper class
		 * @see AlgorithmController#updateComponent(Object)
		 */
		public void hang(Object settings);
	}

	/**
	 * The delay by which the user specifies the speed at which the algorithm
	 * refreshes.
	 * 
	 * @param delay
	 *            The delay of Thread.sleep
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void stop() {
		if (isRunning)
			try {
				queue.put(AlgorithmState.END);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}