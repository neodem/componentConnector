package com.neodem.componentConnector.solver.optimizers.set;

import org.apache.commons.logging.Log;

import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 * 
 */
public abstract class BaseSetOptimizer implements SetOptimizer {

	protected abstract Log getLog();

	/**
	 * 
	 */
	public ComponentSet optimize(final ComponentSet input) {
		ComponentSet testSet = ComponentSet.copy(input);

		ComponentSet optimized = optimizeSet(testSet);
		int oSize = optimized.getTotalSize();
		int iSize = input.getTotalSize();

		if (oSize < iSize) {
			getLog().debug("improved from : " + iSize + "  to : " + oSize);
			return optimized;
		}

		return input;
	}

	protected abstract ComponentSet optimizeSet(ComponentSet testSet);
}
