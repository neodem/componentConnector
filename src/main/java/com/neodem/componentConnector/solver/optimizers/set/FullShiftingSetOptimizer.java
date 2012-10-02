package com.neodem.componentConnector.solver.optimizers.set;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.optimizer.BaseSetOptimizer;
import com.neodem.componentConnector.optimizer.SetOptimizer;
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
		shift(set);
		//invert(set);
		return set;
	}

	/**
	 * invert all componets one at a time looking for improvement then 2 at a
	 * time, etc.
	 * 
	 * @param set
	 */
	private void invert(ComponentSet set) {
		Collection<Component> allComponents = set.getAllComponents();
		int numComponents = allComponents.size();

		int best = 100000;
		Collection<Component> bestInversion = null;

		// for a given size (1/2 of the elements), get all the possilbe inversion strategies
		Collection<Collection<Component>> allInverts = getSets(allComponents, numComponents / 2);

		// try each strategy
		for (Collection<Component> inverts : allInverts) {
			int size = getSizeWithInversions(inverts, set);
			if (size < best) {
				bestInversion = inverts;
				best = size;
			}
		}

	}

	/**
	 * invert the given components and return the set size
	 * 
	 * @param inverts
	 * @param set
	 * @return
	 */
	private int getSizeWithInversions(Collection<Component> inverts, ComponentSet set) {
		for(Component c : inverts) {
			set.invertConnectable(c);
		}
		
		int size = set.getTotalSize();
		
		// reset
		for(Component c : inverts) {
			set.invertConnectable(c);
		}
		
		return size;
	}

	private Collection<Collection<Component>> getSets(Collection<Component> allComponents, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	private ComponentSet shift(ComponentSet set) {
		ConnectionOptimizer z = new SuperShifter();

		List<Connection> largestList = ComponentSet.getAllConnectionsSortedByLargest(set);

		for (Connection c : largestList) {
			z.optimize(c, set);
		}

		return set;
	}
}
