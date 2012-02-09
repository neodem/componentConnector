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
	 * @return the size of the set (total distance)
	 */
	int solve(ComponentSet set);
}
