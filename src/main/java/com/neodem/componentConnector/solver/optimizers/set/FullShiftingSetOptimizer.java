package com.neodem.componentConnector.solver.optimizers.set;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;
import com.neodem.componentConnector.solver.optimizers.connection.SuperShifter;

/**
 * Will find all connections and then shift ...
 * 
 * @author vfumo
 * 
 */
public class FullShiftingSetOptimizer extends BaseSetOptimizer implements SetOptimizer {

	private static final Log log = LogFactory.getLog(FullShiftingSetOptimizer.class);
	
	@Override
	protected Log getLog() {
		return log;
	}

	@Override
	protected ComponentSet optimizeSet(ComponentSet set) {
		ConnectionOptimizer z = new SuperShifter();

		List<Connection> largestList = ComponentSet.getAllConnectionsSortedByLargest(set);

		for (Connection c : largestList) {
			z.optimize(c, set);
		}

		return set;
	}
}
