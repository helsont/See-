package algorithm_base;

import java.util.concurrent.BlockingQueue;

/**
 * 
 * @author winter
 * 
 */
public interface AlgorithmRunner extends Runnable {
	/**
	 * This queue contains either AlgorithmState.STEP or AlgorithmStep.RUN,
	 * dependent on what button the user presses.
	 * 
	 * @param queue
	 *            The queue which receives AlgorithmState changes from the user.
	 */
	public void setQueue(BlockingQueue<AlgorithmState> queue);

	/**
	 * The delay by which the user specifies the speed at which the algorithm
	 * refreshes.
	 * 
	 * @param delay
	 *            The delay of Thread.sleep
	 */
	public void setDelay(int delay);

	/**
	 * Reinitializes the values of the algorithm to start new.
	 */
	public void reset();
}
