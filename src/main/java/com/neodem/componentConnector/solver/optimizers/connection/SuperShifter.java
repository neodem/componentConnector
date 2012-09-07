package com.neodem.componentConnector.solver.optimizers.connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Locatable;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.tools.ConnectionTools;

/**
 * for a given connection, choose one side and move that comp. to every other
 * spot in the set and the one with the lowest score (total) is where we leave
 * it.
 * 
 * @author vfumo
 * 
 */
public class SuperShifter implements ConnectionOptimizer {

	private static final Log log = LogFactory.getLog(SuperShifter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neodem.componentConnector.solver.optimizers.connection.
	 * ConnectionOptimizer
	 * #optimize(com.neodem.componentConnector.model.Connection,
	 * com.neodem.componentConnector.model.sets.ComponentSet)
	 */
	public boolean optimize(Connection c, ComponentSet set) {
		int initialTotalDistance = set.getTotalSize();

		// -- check adjacency
		if (ConnectionTools.onSameRow(c) && ConnectionTools.nextToEachOtherHorizontally(c)) {
			// they are already adjacent horizontally so we can do nothing
			return false;
		}
		if (ConnectionTools.inSameColumn(c) && ConnectionTools.nextToEachOtherVeritcally(c)) {
			// they are already adjacent vertically so we can do nothing
			return false;
		}

		Connectable toCon = c.getTo();
		Connectable fromCon = c.getFrom();

		if ((toCon instanceof Locatable) && (fromCon instanceof Locatable)) {

			Locatable to = (Locatable) toCon;
			Locatable from = (Locatable) fromCon;

			if (!to.isMoveable() && !from.isMoveable()) {
				// we aren't allowed to move the components, so we can do
				// nothing
				return false;
			}

			// pointer to the component we're moving
			Locatable mov = to;

			// pointer to the fixed component
			Locatable fix = from;

			if (!to.isMoveable()) {
				// we usually want to move the 'to' relay. If we can't but we
				// can
				// move the 'from'
				// relay, we move that instead
				mov = from;
				fix = to;
			}

			int xFixed = fix.getxLoc();
			int yFixed = fix.getyLoc();

			int xMovableStart = mov.getxLoc();
			int yMovableStart = mov.getyLoc();

			int sizeX = set.getSizeX();
			int sizeY = set.getSizeY();

			int xBest = -1;
			int yBest = -1;

			int bestTotal = set.getTotalSize();

			for (int xi = 0; xi < sizeX; xi++) {
				for (int yi = 0; yi < sizeY; yi++) {

					// can't move into the fixed spot
					if (xi == xFixed && yi == yFixed)
						continue;

					// don't move into the movables original spot
					if (xi == xMovableStart && yi == yMovableStart)
						continue;

					// try in new pos
					set.moveTo(mov, xi, yi);
					int newTotalDistance = set.getTotalSize();

					// move back
					set.moveTo(mov, xMovableStart, yMovableStart);

					// if better, we store this data
					if (newTotalDistance < bestTotal) {
						bestTotal = newTotalDistance;
						xBest = xi;
						yBest = yi;
					}
				}
			}
			
			if(xBest != -1) {
				set.moveTo(mov, xBest, yBest);
				return true;
			}
		}

		return false;
	}

}
