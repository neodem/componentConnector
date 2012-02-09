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
public class SinglePassConnectionSolver extends BaseConnectionSolver implements Solver {

	private static final Log log = LogFactory.getLog(SinglePassConnectionSolver.class);

	@Override
	protected Log getLog() {
		return log;
	}

	public SinglePassConnectionSolver(List<ConnectionOptimizer> connectionOptimizers) {
		super(connectionOptimizers);
	}

	public SinglePassConnectionSolver(List<ConnectionOptimizer> connectionOptimizers, SetOptimizer setOptimizer) {
		super(connectionOptimizers, setOptimizer);
	}

	public int solveConnection(ComponentSet set) {
		int startSize = set.getTotalSize();
		Connection largest = ComponentSet.findTheLargestConnection(set);

		if (largest == null) {
			return startSize;
		}

		return optimizeConnection(largest, set, startSize);
	}

}
