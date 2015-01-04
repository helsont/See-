package com.winter.algorithms.core;

/**
 * A user can either press two buttons to specify the algorithm's speed. STEP,
 * which only runs until the thread blocks again, or RUN, which continuously
 * STEPs without stopping.
 * 
 * @author Helson Taveras hjt2113@columbia.edu
 * 
 */
public enum AlgorithmState {
	/**
	 * STEP runs until the thread blocks again while RUN doesn't stop. END
	 * occurs when an algorithm must be stopped for the supported user action.
	 */
	STEP, RUN, END
}
