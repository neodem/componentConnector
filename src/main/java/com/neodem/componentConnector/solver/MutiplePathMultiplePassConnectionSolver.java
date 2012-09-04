package com.neodem.componentConnector.solver;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.optimizers.ConnectionOptimizer;
import com.neodem.componentConnector.solver.optimizers.SetOptimizer;

/**
 * @author vfumo
 * 
 */
public class MutiplePathMultiplePassConnectionSolver extends MultiPassConnectionSolver {

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
	
	public MutiplePathMultiplePassConnectionSolver(List<ConnectionOptimizer> connectionOptimizers, SetOptimizer setOptimizer) {
		super(connectionOptimizers, setOptimizer);
	}

	/**
	 * this version will run on all connections in the set over and over again
	 * until we make no more progress
	 */
	public int solveConnection(ComponentSet set) {
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
				return current;
			}
		}
	}
}
