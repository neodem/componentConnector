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
	public boolean solveConnection(ComponentSet set) {
		int best = set.getTotalSize();
		boolean changed = false;
		while (true) {
			Connection largest = ComponentSet.findTheLargestConnection(set);

			if(optimizeConnection(largest, set)) {
				changed = true;
			} else {
				return false;
			}
			
			int optimizedSize = set.getTotalSize();
			if (optimizedSize < best) {
				best = optimizedSize;
			} else {
				return changed;
			}
		}
	}

}
