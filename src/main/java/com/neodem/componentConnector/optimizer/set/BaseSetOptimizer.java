package com.neodem.componentConnector.optimizer.set;

import org.apache.commons.logging.Log;

import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.tools.Calculator;

/**
 * @author vfumo
 * 
 */
public abstract class BaseSetOptimizer implements SetOptimizer {
	protected abstract Log getLog();
	
	protected static Calculator calc = new Calculator();

	/**
	 * 
	 */
	public ComponentSet optimize(final ComponentSet input) {
		ComponentSet testSet = ComponentSet.copy(input);

		ComponentSet optimized = optimizeSet(testSet);
		
		int oSize = calc.calculateSetScore(optimized);
		int iSize = calc.calculateSetScore(input);

		if (oSize < iSize) {
			getLog().debug("improved from : " + iSize + "  to : " + oSize);
			return optimized;
		}

		return input;
	}

	protected abstract ComponentSet optimizeSet(ComponentSet testSet);
}
