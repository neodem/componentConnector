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
	 * @return the solved set (or the oringal one if no better solution found)
	 */
	ComponentSet solve(ComponentSet set);
}
