package com.neodem.componentConnector.solver.optimizers;

import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.tools.Calculator;
import com.neodem.componentConnector.tools.DefaultCalculator;

/**
 * Will try to rotate the 'to' relay and see if that improved the world, else
 * will try to rotate the 'from' relay and see
 * 
 * @author vfumo
 * 
 */
public class ConnectionRotator implements ConnectionOptimizer {

	protected Calculator calculator = new DefaultCalculator();

	/**
	 * will try to rotate From, To and then Both. Will modify and return once we
	 * shorten the path;
	 * 
	 * @param largest
	 * @param set
	 * @return the final size
	 */
	public int optimize(Connection c, ComponentSet set) {
		int initialTotalDistance = set.getTotalSize();

		Connectable toCon = c.getTo();
		Connectable fromCon = c.getFrom();

		if ((toCon instanceof Component) && (fromCon instanceof Component)) {
			
			Component to = (Component)toCon;
			Component from = (Component)fromCon;

			// -- try to fix the largest by rotating one or the other

			// try rotating the to relay
			to.invert();
			int newTotal = set.recalculate();
			if (newTotal >= initialTotalDistance) {
				// rollback
				to.invert();
			} else {
				return newTotal;
			}

			// try rotating the from relay
			from.invert();
			newTotal = set.recalculate();
			if (newTotal >= initialTotalDistance) {
				// rollback
				from.invert();
			} else {
				return newTotal;
			}

			// no changes
			set.recalculate();
		}
		return initialTotalDistance;
	}

}
