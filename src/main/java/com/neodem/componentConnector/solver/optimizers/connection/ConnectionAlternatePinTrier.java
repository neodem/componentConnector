package com.neodem.componentConnector.solver.optimizers.connection;

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
	public boolean optimize(Connection c, ComponentSet set) {
		int size = set.getTotalSize();
		
		Collection<Pin> fromPins = c.getFromPins();
		boolean changed = tryAltPins(c, set, size, fromPins, "from", false);
		
		if(changed) {
			size = set.getTotalSize();
		}
		
		Collection<Pin> toPins = c.getToPins();
		changed = tryAltPins(c, set, size, toPins, "to", changed);
		
		return changed;
	}

	private boolean tryAltPins(Connection c, ComponentSet set, int initialTotalDistance, Collection<Pin> altPins, String id, boolean changed) {
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
					set.recalculate();
					return changed;
				} else {
					log.debug("switching from " + current + " to " + altPin + " since size = " + setSize);
					current = altPin;
					best = setSize; 
					return true;
				}
			}
		}
		return changed;
	}
}
