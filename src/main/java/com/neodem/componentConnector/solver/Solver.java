package com.neodem.componentConnector.solver;

import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 * 
 */
public interface Solver {

	/**
	 * 
	 * @param set
	 * @return true if the set changed
	 */
	boolean solve(ComponentSet set);
}
