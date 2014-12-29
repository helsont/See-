package algorithm_base;

/**
 * A wrapper class for the thread that runs the algorithm.
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public class AlgorithmThread implements Runnable {
	private Thread thread;
	private AlgorithmRunner algorithm;
	private boolean isEnabled;

	/**
	 * Creates an AlgorithmThread based on the AlgorithmRunner to run.
	 * 
	 * @param algorithm
	 *            The algorithm to run.
	 */
	public AlgorithmThread(AlgorithmRunner algorithm) {
		this.algorithm = algorithm;
		isEnabled = true;
		thread = new Thread(algorithm);
		thread.start();
	}

	public void start() {
		thread = new Thread(algorithm);
		thread.start();
	}

	@Override
	public void run() {
		if (isEnabled) {
			algorithm.run();
		}
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isEnabled() {
		return isEnabled;
	}
}
