package com.neodem.componentConnector.solver;

import java.util.List;

import org.apache.commons.logging.Log;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;
import com.neodem.componentConnector.tools.Calculator;
import com.neodem.componentConnector.tools.Calculator;

/**
 * @author vfumo
 * 
 */
public abstract class BaseSolver implements Solver {

	protected abstract Log getLog();

	protected List<ConnectionOptimizer> connectionOptimizers;
	
	private static final Calculator calc = new Calculator();

	public BaseSolver(List<ConnectionOptimizer> connectionOptimizers) {
		this.connectionOptimizers = connectionOptimizers;
	}

	public final boolean solve(ComponentSet set) {
		getLog().debug("solve() : " + set.getTotalSize());
		
		boolean changed = solveConnection(set);

		getLog().debug("solve() complete : " + set.getTotalSize());
		
		return changed;
	}

	public abstract boolean solveConnection(ComponentSet set);

	protected boolean optimizeConnection(Connection c, ComponentSet set) {

		//getLog().debug("optimizeConnection : " + c + " : " + startSize);

		if (calc.calculateDistance(c) == 0) {
			// no need, we're already optimized as best as we can
			return false;
		}

		// try to make the connection better by running all optimizers
		boolean changed = false;
		for (ConnectionOptimizer o : connectionOptimizers) {
			if(o.optimize(c, set) && !changed) {
				changed = true;
			}
		}
		
		return changed;
	}

	/**
	 * @param connectionOptimizer
	 *            the connectionOptimizer to set
	 */
	public void setConnectionOptimizer(List<ConnectionOptimizer> connectionOptimizers) {
		this.connectionOptimizers = connectionOptimizers;
	}
}
