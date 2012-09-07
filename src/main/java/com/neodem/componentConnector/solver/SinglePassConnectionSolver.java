package com.neodem.componentConnector.solver;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;

/**
 * Will find the single largest connection and optimize it
 * 
 * @author vfumo
 * 
 */
public class SinglePassConnectionSolver extends BaseSolver implements Solver {

	private static final Log log = LogFactory.getLog(SinglePassConnectionSolver.class);

	@Override
	protected Log getLog() {
		return log;
	}

	public SinglePassConnectionSolver(List<ConnectionOptimizer> connectionOptimizers) {
		super(connectionOptimizers);
	}

	public boolean solveConnection(ComponentSet set) {
		Connection largest = ComponentSet.findTheLargestConnection(set);

		if (largest == null) {
			return false;
		}
		
		int i = 0;
		while(optimizeConnection(largest, set)) i++;

		return i != 0;
	}

}
