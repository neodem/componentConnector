package com.neodem.componentConnector.solver;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.optimizers.ConnectionOptimizer;
import com.neodem.componentConnector.solver.optimizers.SetOptimizer;

/**
 * Will make many passes and find the largest connection and try to make it
 * smaller by rotation.
 * 
 * @author vfumo
 * 
 */
public class MultiPassConnectionSolver extends SinglePassConnectionSolver implements Solver {

	private static final Log log = LogFactory.getLog(MultiPassConnectionSolver.class);

	@Override
	protected Log getLog() {
		return log;
	}
	
	public MultiPassConnectionSolver(List<ConnectionOptimizer> connectionOptimizers) {
		super(connectionOptimizers);
	}
	
	public MultiPassConnectionSolver(List<ConnectionOptimizer> connectionOptimizers, SetOptimizer setOptimizer) {
		super(connectionOptimizers, setOptimizer);
	}

	/**
	 * this version will keep trying new largest connections until no progress
	 * is made
	 */
	public int solveConnection(ComponentSet set) {
		int best = set.getTotalSize();
		while (true) {
			Connection largest = ComponentSet.findTheLargestConnection(set);

			int optimizedSize = optimizeConnection(largest, set, best);
			if (optimizedSize < best) {
				best = optimizedSize;
			} else {
				return optimizedSize;
			}
		}
	}

}
