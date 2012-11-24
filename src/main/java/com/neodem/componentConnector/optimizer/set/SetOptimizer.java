package com.neodem.componentConnector.optimizer.set;

import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * for code that takes a ComponentSet and attempts to make it
 * more suitable to solving.
 * 
 * @author vfumo
 *
 */
public interface SetOptimizer {
	
	/**
	 * return an optimized version of the ComponentSet or
	 * the original set (if it couldn't be improved)
	 * 
	 * @param input
	 * @return
	 */
	public ComponentSet optimize(final ComponentSet input);
}
