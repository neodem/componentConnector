package com.neodem.componentConnector.tools;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.component.Component;

/**
 * @author vfumo
 * 
 */
public class ConnectionTools {

	public static boolean onSameRow(Connection c) {
		Component from = c.getFrom();
		Component to = c.getTo();

		int fromY = from.getyLoc();
		int toY = to.getyLoc();

		// same row?
		if (fromY == toY) {
			return true;
		}
		return false;
	}

	public static boolean inSameColumn(Connection c) {
		Component from = c.getFrom();
		Component to = c.getTo();

		int fromX = from.getxLoc();
		int toX = to.getxLoc();

		// same column?
		if (fromX == toX) {
			return true;
		}
		return false;
	}

	public static boolean nextToEachOtherHorizontally(Connection c) {
		Component from = c.getFrom();
		Component to = c.getTo();

		int fromX = from.getxLoc();
		int toX = to.getxLoc();

		if ((fromX - toX == 1) || (toX - fromX == 1)) {
			return true;
		}
		return false;
	}

	public static boolean nextToEachOtherVeritcally(Connection c) {
		Component from = c.getFrom();
		Component to = c.getTo();

		int fromY = from.getyLoc();
		int toY = to.getyLoc();

		if ((fromY - toY == 1) || (toY - fromY == 1)) {
			return true;
		}
		return false;
	}

	public static boolean adjacent(Connection c) {
		return nextToEachOtherHorizontally(c) || nextToEachOtherVeritcally(c);
	}

	public static boolean toRightOfFrom(Connection c) {
		return calcToXMinusFromX(c) > 0;
	}

	public static boolean toLeftOfFrom(Connection c) {
		return calcToXMinusFromX(c) < 0;
	}

	private static int calcToXMinusFromX(Connection c) {
		Component from = c.getFrom();
		Component to = c.getTo();

		int fromX = from.getxLoc();
		int toX = to.getxLoc();

		return toX - fromX;
	}

}
