/**
 * 
 */
package com.neodem.componentConnector.solver2;

import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.optimizer.SetOptimizer;

/**
 * @author vfumo
 *
 */
public class JustOneOptimizerSolver implements Solver {
	
	private SetOptimizer o;

	public JustOneOptimizerSolver(SetOptimizer o) {
		this.o = o;
	}

	/* (non-Javadoc)
	 * @see com.neodem.componentConnector.solver.Solver#solve(com.neodem.componentConnector.model.sets.ComponentSet)
	 */
	public boolean solve(ComponentSet set) {
		ComponentSet best = o.optimize(set);
		return false;
	}

}
