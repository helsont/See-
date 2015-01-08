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
