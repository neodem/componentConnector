package com.neodem.componentConnector.solver;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;

/**
 * Will find the largest connection and try to optimize it. If it succeeds,
 * it will try again.
 * 
 * @author vfumo
 * 
 */
public class MultiPassConnectionSolver extends BaseSolver implements Solver {

	private static final Log log = LogFactory.getLog(MultiPassConnectionSolver.class);

	@Override
	protected Log getLog() {
		return log;
	}
	
	public MultiPassConnectionSolver(List<ConnectionOptimizer> connectionOptimizers) {
		super(connectionOptimizers);
	}
	
	/**
	 * this version will keep trying new largest connections until no progress
	 * is made
	 */
	public ComponentSet solveConnection(ComponentSet set) {
		int best = set.getTotalSize();
		while (true) {
			Connection largest = ComponentSet.findTheLargestConnection(set);

			int optimizedSize = optimizeConnection(largest, set, best);
			if (optimizedSize < best) {
				best = optimizedSize;
			} else {
				return set;
			}
		}
	}

}
