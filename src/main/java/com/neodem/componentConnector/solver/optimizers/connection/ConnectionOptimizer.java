package com.neodem.componentConnector.solver.optimizers.connection;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 * 
 */
public interface ConnectionOptimizer {

	/**
	 * make one change to attempt to optimize the connection. If we don't
	 * succeed nothing changes
	 * 
	 * @param c
	 *            the connection to optimize
	 * @param set
	 *            the entire set (for calculations of total effect of change)
	 * @return true if something changed in the set
	 */
	public abstract boolean optimize(Connection c, ComponentSet set);
}
