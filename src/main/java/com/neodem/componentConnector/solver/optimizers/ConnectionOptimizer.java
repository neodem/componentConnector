package com.neodem.componentConnector.solver.optimizers;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;


/**
 * @author vfumo
 * 
 */
public interface ConnectionOptimizer {

	/**
	 * make one change to optimize the connection. If we don't succeed nothing
	 * changes
	 * 
	 * @param c
	 *            the connection to optimize
	 * @param set
	 *            the entire set (for calculations of total effect of change)
	 * @return the new total distance or the old one (if nothing changed)
	 */
	public abstract int optimize(Connection c, ComponentSet set);
}
