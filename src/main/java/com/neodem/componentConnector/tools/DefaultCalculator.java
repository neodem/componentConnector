package com.neodem.componentConnector.tools;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;

import java.util.Collection;

import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Locatable;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 * 
 */
public class DefaultCalculator implements Calculator {

	public int calculateDistance(Connection c) {
		int absDistance = calculateAbsoluteDistance(c);
		int rotateDistance = calculateRotationalDistance(c);

		return absDistance + rotateDistance;
	}
	
	public int calculateSetSize(ComponentSet set) {
		Collection<Connection> cons = set.getAllConnections();
		int distance = 0;

		for (Connection c : cons) {
			distance += calculateDistance(c);
		}

		return distance;
	}

	/**
	 * give the distance between relays. We give a -1 for being on same row or
	 * column
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	protected int calculateAbsoluteDistance(Connection c) {
		Connectable from = c.getFrom();
		Connectable to = c.getTo();

		if ((from instanceof Locatable) && (to instanceof Locatable)) {
			int fromY = ((Locatable) from).getyLoc();
			int toY = ((Locatable) to).getyLoc();

			int fromX = ((Locatable) from).getxLoc();
			int toX = ((Locatable) to).getxLoc();

			int absoluteHDistance = distance(toX, fromX);
			int absoluteVDistance = distance(toY, fromY);

			int offset = 0;

			if (ConnectionTools.onSameRow(c)) {
				offset--;
			}

			if (ConnectionTools.inSameColumn(c)) {
				offset--;
			}

			return absoluteHDistance + absoluteVDistance + offset;
		}

		return 0;
	}

	protected int calculateRotationalDistance(Connection c) {
		Connectable from = c.getFrom();
		Connectable to = c.getTo();

		Pin fromPin = c.getFromPin();
		Pin toPin = c.getToPin();

		if ((from instanceof Locatable) && (to instanceof Locatable)) {

			Side fromSide = ComponentTools.getSideForPin(from, fromPin);
			if (((Locatable) from).isInverted()) {
				fromSide = fromSide.other();
			}

			Side toSide = ComponentTools.getSideForPin(to, toPin);
			if (((Locatable) to).isInverted()) {
				toSide = toSide.other();
			}

			if (ConnectionTools.toLeftOfFrom(c)) {
				return toLeftOfFrom(fromSide, toSide);
			} else if (ConnectionTools.toRightOfFrom(c)) {
				return toRightOfFrom(fromSide, toSide);
			}

			// we are horizontally aligned
			return aligned(fromSide, toSide);
		}
		
		return 0;
	}

	/**
	 * if we are aligned on top of each other, a connection on the same side is
	 * ideal so that gets a 0. On opposite sides we have a 1
	 * 
	 * @param fromSide
	 * @param toSide
	 * @return
	 */
	private int aligned(Side fromSide, Side toSide) {
		if ((fromSide == Right && toSide == Right) || (fromSide == Left && toSide == Left))
			return 0;
		return 1;
	}

	private int toRightOfFrom(Side fromSide, Side toSide) {
		if (fromSide == Right) {
			if (toSide == Left)
				return 0;
			if (toSide == Right)
				return 1;
		}

		// from side is left
		if (toSide == Left)
			return 1;

		return 2;
	}

	private int toLeftOfFrom(Side fromSide, Side toSide) {
		if (fromSide == Left) {
			if (toSide == Left)
				return 1;
			if (toSide == Right)
				return 0;
		}

		// from side is right
		if (toSide == Right)
			return 1;

		return 2;
	}

	private int distance(int loc1, int loc2) {

		if (loc1 == loc2) {
			return 0;
		}

		if (loc1 > loc2) {
			return loc1 - loc2;
		}

		return loc2 - loc1;
	}
}
