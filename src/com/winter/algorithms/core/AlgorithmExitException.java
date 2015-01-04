package com.winter.algorithms.core;

/**
 * This exception is created (and caught) when {@link AlgorithmState}'s
 * AlgorithmState.END is in the AlgorithmState Queue. This relieves the
 * programmer from the pressure of having to know or handle the situation when
 * the user wants to exit the AlgorithmExecutor.
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public class AlgorithmExitException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2194598018475647337L;

	/**
	 * An AlgorithmExitException with the specified message.
	 * 
	 * @param message
	 *            The message.
	 */
	public AlgorithmExitException(String message) {
		super(message);
	}
}
