package com.neodem.componentConnector.solver;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;

/**
 * @author vfumo
 * 
 */
public class MutiplePathMultiplePassConnectionSolver extends BaseSolver {

	private static final Log log = LogFactory.getLog(MutiplePathMultiplePassConnectionSolver.class);

	@Override
	protected Log getLog() {
		return log;
	}
	
	/**
	 * @param connectionOptimizers
	 */
	public MutiplePathMultiplePassConnectionSolver(List<ConnectionOptimizer> connectionOptimizers) {
		super(connectionOptimizers);
	}
	
	/**
	 * this version will run on all connections in the set over and over again
	 * until we make no more progress
	 */
	public ComponentSet solveConnection(ComponentSet set) {
		int best = set.getTotalSize();
		while (true) {
			List<Connection> largestList = ComponentSet.getAllConnectionsSortedByLargest(set);

			for (Connection c : largestList) {
				optimizeConnection(c, set, best);
			}

			int current = set.recalculate();
			if (current < best) {
				best = current;
			} else {
				return set;
			}
		}
	}
}
