package com.neodem.componentConnector.solver;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.common.utility.Flip;
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
	public boolean solveConnection(ComponentSet set) {
		boolean changeHappened = false;
		while (true) {
			
			List<Connection> largestList = ComponentSet.getAllConnectionsSortedByLargest(set);

			Flip changed = new Flip(true);
			
			for (Connection c : largestList) {
				changed.accept(optimizeConnection(c, set));
			}
			
			if(changed.activated()) {
				changeHappened = true;
			} else {
				return changeHappened;
			}
		}
	}
	
}
