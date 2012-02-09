package com.neodem.componentConnector.tools;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.component.Component;

import static com.neodem.componentConnector.model.Side.*;

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

	/**
	 * give the distance between relays. We give a -1 for being on same row or
	 * column
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	protected int calculateAbsoluteDistance(Connection c) {
		int absoluteHDistance = distance(c.getTo().getxLoc(), c.getFrom().getxLoc());
		int absoluteVDistance = distance(c.getTo().getyLoc(), c.getFrom().getyLoc());

		int offset = 0;

		if (ConnectionTools.onSameRow(c)) {
			offset--;
		}

		if (ConnectionTools.inSameColumn(c)) {
			offset--;
		}

		return absoluteHDistance + absoluteVDistance + offset;
	}

	protected int calculateRotationalDistance(Connection c) {
		Component from = c.getFrom();
		Component to = c.getTo();

		Pin fromPin = c.getFromPin();
		Pin toPin = c.getToPin();

		Side fromSide = ComponentTools.getSideForPin(from, fromPin);
		if (from.isInverted()) {
			fromSide = fromSide.other();
		}

		Side toSide = ComponentTools.getSideForPin(to, toPin);
		if (to.isInverted()) {
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
