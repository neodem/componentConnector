package com.neodem.componentConnector.solver.optimizers.set;

import com.neodem.componentConnector.model.sets.ComponentSet;

public interface SetOptimizer {
	
	/**
	 * return an optimized version of the ComponentSet or
	 * the original set (if it couldn't be improved)
	 * 
	 * @param input
	 * @return
	 */
	public ComponentSet optimize(ComponentSet input);
}
