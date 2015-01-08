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

import java.util.concurrent.LinkedBlockingQueue;

/**
 * A wrapper class for the thread that runs the algorithm. This only uses one
 * Thread to run any algorithm. The currently running AlgorithmExecutor is
 * swapped out when {@link AlgorithmThread#changeExecutor(AlgorithmExecutor)} is
 * called.
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public class AlgorithmThread implements Runnable {
	/**
	 * The thread that runs the AlgorithmController.
	 */
	private Thread thread;
	/**
	 * Manages how an individual AlgorithmExecutor is run.
	 */
	private AlgorithmController controller;
	/**
	 * Executed when an algorithm has completed.
	 */
	private OnCompletion onCompletion;
	/**
	 * If an algorithm is currently running.
	 */
	private boolean isRunningAlgorithm;
	/**
	 * The delay by which the user specifies the speed at which the algorithm
	 * refreshes.
	 */
	private int delay;
	/**
	 * Keeps the states requested by the user.
	 */
	private LinkedBlockingQueue<AlgorithmState> stateQueue;
	/**
	 * Works as a Queue with only a head. Read: only one object is ever is in
	 * the Queue. Future implementations may replace this with a custom object
	 * that blocks the thread, instead of a hefty data structure.
	 */
	private LinkedBlockingQueue<AlgorithmExecutor> executorQueue;
	/**
	 * The currently being executed algorithm.
	 */
	private AlgorithmExecutor current;
	/**
	 * Always true. Keep the thread alive.
	 */
	private boolean running;
	/**
	 * If the algorithm was interrupted prematurely.
	 */
	private boolean interrupted;

	/**
	 * Creates an AlgorithmThread based on the AlgorithmRunner to run.
	 * 
	 * @param algorithm
	 *            The algorithm to run.
	 */
	public AlgorithmThread(AlgorithmExecutor algorithm) {
		current = algorithm;
		executorQueue = new LinkedBlockingQueue<AlgorithmExecutor>(1);
		executorQueue.add(current);
	}

	/**
	 * Creates an AlgorithmThread based on the AlgorithmRunner to run.
	 * 
	 * @param algorithm
	 *            The algorithm to run.
	 * @param onCompletion
	 *            What to do when the algorithm has completed.
	 */
	public AlgorithmThread(AlgorithmExecutor algorithm,
			OnCompletion onCompletion) {
		this(algorithm);
		this.onCompletion = onCompletion;

	}

	/**
	 * Like thread.start(), you can only call this method once. Invokes the
	 * AlgorithmRunner's <code>run</code> method.
	 */
	public void start() {
		if (running)
			throw new RuntimeException("Attempting to start "
					+ "AlgorithmThread twice.");
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Waits for an AlgorithmExecutor. Runs the algorithm. Then calls onComplete
	 * if an OnCompletion is defined.
	 */
	@Override
	public void run() {
		while (running) {
			try {
				AlgorithmExecutor currentAlgorithm = executorQueue.take();
				current = currentAlgorithm;
				runAlgorithm(currentAlgorithm);
				if (onCompletion != null && !interrupted) {
					onCompletion.onComplete();
				}
				// Reset the flag
				if (interrupted) {
					interrupted = false;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets the isRunning flag to true, runs the specified AlgorithmRunner, and
	 * then sets the isRunning flag to false.
	 * 
	 * @param alg
	 *            The AlgorithmRunner to run.
	 */
	private void runAlgorithm(AlgorithmExecutor alg) {
		isRunningAlgorithm = true;
		controller = new AlgorithmController(alg, stateQueue, delay);
		controller.run();
		isRunningAlgorithm = false;
	}

	/**
	 * Returns whether an AlgorithmExecutor is currently running.
	 * 
	 * @return True if it's running.
	 */
	public boolean isRunningAlgorithm() {
		return isRunningAlgorithm;
	}

	/**
	 * Sets value of Thread.sleep.
	 * 
	 * @param delay
	 *            The sleeptime of the main thread.
	 */
	public void setDelay(int delay) {
		this.delay = delay;
		if (controller != null)
			controller.setDelay(delay);
	}

	/**
	 * Resets the currently running AlgorithmExecutor.
	 */
	public void restart() {
		// A hard reset with an interruption
		if (isRunningAlgorithm) {
			interrupted = true;
		}
		controller.stop();
		try {
			executorQueue.put(current);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Changes the current algorithm executor to the specified one.
	 * 
	 * @param executor
	 *            The new algorithm.
	 */
	public void changeExecutor(AlgorithmExecutor executor) {
		if (isRunningAlgorithm) {
			stop();
		}
		try {
			executorQueue.put(executor);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops the current AlgorithmExecutor from running.
	 */
	public void stop() {
		interrupted = true;
		controller.stop();
	}

	/**
	 * This queue contains either AlgorithmState.STEP, AlgorithmStep.RUN,
	 * Algorithm.END dependent on what button the user presses.
	 * 
	 * @param queue
	 *            The queue which receives AlgorithmState changes from the user.
	 */
	public void setQueue(LinkedBlockingQueue<AlgorithmState> queue) {
		this.stateQueue = queue;
	}

	/**
	 * The method onComplete() is invoked after the algorithm has completed.
	 * 
	 * @author Helson Taveras hjt2113@columbia.edu
	 * 
	 */
	public interface OnCompletion {
		/**
		 * Invoked after the algorithm has completed.
		 */
		public void onComplete();
	}
}
