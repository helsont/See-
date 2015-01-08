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
