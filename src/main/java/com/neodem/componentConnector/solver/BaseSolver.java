package com.neodem.componentConnector.solver;

import java.util.List;

import org.apache.commons.logging.Log;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;
import com.neodem.componentConnector.tools.Calculator;
import com.neodem.componentConnector.tools.DefaultCalculator;

/**
 * @author vfumo
 * 
 */
public abstract class BaseSolver implements Solver {

	protected abstract Log getLog();

	protected List<ConnectionOptimizer> connectionOptimizers;
	private static final Calculator calc = new DefaultCalculator();

	public BaseSolver(List<ConnectionOptimizer> connectionOptimizers) {
		this.connectionOptimizers = connectionOptimizers;
	}

	public final ComponentSet solve(ComponentSet set) {
		getLog().debug("solve() : " + set.getTotalSize());
		
		ComponentSet solved = solveConnection(set);

		getLog().debug("solve() complete : " + solved.getTotalSize());
		
		return solved;
	}

	public abstract ComponentSet solveConnection(ComponentSet set);

	/**
	 * recursively attempt to optimize the given connection until no progress is
	 * made. Will only affect the inputted set if an optimization can be made.
	 * 
	 * @param c
	 * @param set
	 * @param target
	 * @return
	 */
	protected int optimizeConnection(Connection c, ComponentSet set, int startSize) {

		//getLog().debug("optimizeConnection : " + c + " : " + startSize);

		if (calc.calculateDistance(c) == 0) {
			// no need, we're already optimized as best as we can
			return startSize;
		}

		// try to make the connection better by running all optimizers
		for (ConnectionOptimizer o : connectionOptimizers) {
			o.optimize(c, set);
		}
		
		int optimizedTotalDistance = set.recalculate();

		if (optimizedTotalDistance >= startSize) {
			// no improvement
			return startSize;
		}

		// try again
		return optimizeConnection(c, set, optimizedTotalDistance);
	}

	/**
	 * @param connectionOptimizer
	 *            the connectionOptimizer to set
	 */
	public void setConnectionOptimizer(List<ConnectionOptimizer> connectionOptimizers) {
		this.connectionOptimizers = connectionOptimizers;
	}
}
