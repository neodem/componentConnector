package com.neodem.componentConnector.solver.optimizers;

import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.tools.ConnectionTools;

/**
 * 
 * moves the 'to' side of the connection one component closer and tests to see
 * if that made things better
 * 
 * @author vfumo
 * 
 */
public class ConnectionMover implements ConnectionOptimizer {

	public int optimize(Connection c, ComponentSet set) {
		int initialTotalDistance = set.getTotalSize();

		// -- check adjacency
		if (ConnectionTools.onSameRow(c) && ConnectionTools.nextToEachOtherHorizontally(c)) {
			// they are already adjacent horizontally so we can do nothing
			return initialTotalDistance;
		}
		if (ConnectionTools.inSameColumn(c) && ConnectionTools.nextToEachOtherVeritcally(c)) {
			// they are already adjacent vertically so we can do nothing
			return initialTotalDistance;
		}

		Connectable toCon = c.getTo();
		Connectable fromCon = c.getFrom();

		if ((toCon instanceof Component) && (fromCon instanceof Component)) {
			
			Component to = (Component)toCon;
			Component from = (Component)fromCon;

			if (!to.isMoveable() && !from.isMoveable()) {
				// we aren't allowed to move the relays, so we can do nothing
				return initialTotalDistance;
			}

			// pointer to the relay we're moving
			Component mov = to;

			// pointer to the fixed relay
			Component fix = from;

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

			int xMovable = mov.getxLoc();
			int yMovable = mov.getyLoc();

			// ------------------ horizontal shifting

			// check to see if we are not aligned already (can happen in diff
			// rows)
			if (!ConnectionTools.inSameColumn(c)) {

				// Move 'mov' one closer horizontally
				// to 'fix' and see what happens. If we succeed, we return. If
				// not
				// we revert and try horizontal shift

				// we are going to shift the 'mov' to the 'fix'. determine
				// direction
				if (xFixed < xMovable) {
					// this means 'fix' is to the left of 'mov' so we shift
					// 'mov'
					// one to
					// the left
					set.shiftOneLeft(mov);
				} else {
					// this means 'fix' is on the right of 'mov' so we shift
					// 'mov'
					// one to
					// the right
					set.shiftOneRight(mov);
				}

				// did we improve?
				int newTotalDistance = set.getTotalSize();
				if (newTotalDistance < initialTotalDistance) {
					// yes!
					return newTotalDistance;
				} else {
					// no :(
					// we revert
					if (xFixed < xMovable) {
						set.shiftOneRight(mov);
					} else {
						set.shiftOneLeft(mov);
					}
				}
			}

			// ------------------ vertical shifting

			// make sure we can benefit from vertical shift (ie. on diff rows)
			if (!ConnectionTools.onSameRow(c)) {

				// Lets move 'mov' one closer vertically
				// to 'fix' and see what happens. If we succeed, we return. If
				// not
				// we revert and give up

				// we are going to shift the 'mov' to the 'fix'. determine
				// direction
				if (yFixed < yMovable) {
					// this means 'fix' is above 'mov' so we shift 'mov' up one
					set.shiftOneUp(mov);
				} else {
					// this means 'fix' is below 'mov' so we shift 'mov' down
					// one
					set.shiftOneDown(mov);
				}

				// did we improve?
				int newTotalDistance = set.getTotalSize();
				if (newTotalDistance < initialTotalDistance) {
					// yes!
					return newTotalDistance;
				} else {
					// no :(
					// we revert
					if (yFixed < yMovable) {
						set.shiftOneDown(mov);
					} else {
						set.shiftOneUp(mov);
					}
				}
			}
		}

		return initialTotalDistance;
	}
}
