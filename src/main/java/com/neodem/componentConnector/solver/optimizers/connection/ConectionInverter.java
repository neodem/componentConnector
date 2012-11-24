package com.neodem.componentConnector.solver.optimizers.connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.tools.Calculator;

/**
 * Will try to rotate the 'to' Connectable and see if that improved the world, else
 * will try to rotate the 'from' Connectable and see
 * 
 * @author vfumo
 * 
 */
public class ConectionInverter implements ConnectionOptimizer {
	private static final Log log = LogFactory.getLog(ConectionInverter.class);
	
	protected Calculator calculator = new Calculator();

	/**
	 * will try to rotate From, To and then Both. Will modify and return once we
	 * shorten the path;
	 * 
	 * @param c
	 *            the connection to attempt to optimize
	 * @param set
	 * @return the final size
	 */
	public boolean optimize(Connection c, ComponentSet set) {
		int initialTotalDistance = set.getTotalSize();

		Connectable to = c.getTo();
		Connectable from = c.getFrom();

		// -- try to fix the largest by rotating one or the other

		// try rotating the TO Connectable
		to.invert();
		int newTotal = set.recalculate();
		if (newTotal >= initialTotalDistance) {
			// rollback
			to.invert();
		} else {
			log.debug("inverted " + to +  " improved size from " + initialTotalDistance + " to " + newTotal);
			return true;
		}

		// try rotating the FROM Connectable
		from.invert();
		newTotal = set.recalculate();
		if (newTotal >= initialTotalDistance) {
			// rollback
			from.invert();
		} else {
			log.debug("inverted " + from +  " improved size from " + initialTotalDistance + " to " + newTotal);
			return true;
		}

		// no changes
		set.recalculate();
		
		return false;
	}

}
