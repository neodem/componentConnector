package com.neodem.componentConnector.solver.optimizers;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 *
 */
public class ConnectionAlternatePinTrier implements ConnectionOptimizer {

	private static final Log log = LogFactory.getLog(ConnectionAlternatePinTrier.class);
	
	/* (non-Javadoc)
	 * @see com.neodem.relayLocator.solver.optimizers.ConnectionOptimizer#optimize(com.neodem.relayLocator.model.Connection, com.neodem.relayLocator.model.sets.ComponentSet)
	 */
	public int optimize(Connection c, ComponentSet set) {
		int initialTotalDistance = set.getTotalSize();
		
		Collection<Pin> fromPins = c.getFromPins();
		int best = tryAltPins(c, set, initialTotalDistance, fromPins, "from");
		
		Collection<Pin> toPins = c.getToPins();
		best = tryAltPins(c, set, best, toPins, "to");
		
		return best;
	}

	private int tryAltPins(Connection c, ComponentSet set, int initialTotalDistance, Collection<Pin> altPins, String id) {
		int best = initialTotalDistance;
		if(altPins.size() > 1) {
			
			// there are alternate pins.. lets try them all to find the best one (or keep things the same)
			Pin current = c.getPin(id);
			for(Pin altPin : altPins) {
				if(altPin.equals(current)) {
					continue;
				}
				
				c.setPin(id, altPin);
				int setSize = set.recalculate();
				if (setSize >= best) {
					// rollback
					c.setPin(id, current);
				} else {
					log.debug("switching from " + current + " to " + altPin + " since size = " + setSize);
					current = altPin;
					best = setSize; 
				}
			}
		}
		return best;
	}

}
