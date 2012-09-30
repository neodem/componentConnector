package com.neodem.componentConnector.tools;

import com.neodem.componentConnector.model.Location;

/**
 * @author vfumo
 * 
 */
public class LocationTools {

	public static boolean onSameRow(Location a, Location b) {
		return a.getRow() == b.getRow();
	}

	public static boolean inSameColumn(Location a, Location b) {
		return a.getCol() == b.getCol();
	}

	public static boolean nextToEachOtherHorizontally(Location a, Location b) {
		return (a.getCol() - b.getCol() == 1) || (b.getCol() - a.getCol() == 1);
	}

	public static boolean nextToEachOtherVeritcally(Location a, Location b) {
		return (a.getRow() - b.getRow() == 1) || (b.getRow() - a.getRow() == 1);
	}

	public static boolean adjacent(Location a, Location b) {
		return nextToEachOtherHorizontally(a, b) || nextToEachOtherVeritcally(a, b);
	}

	/**
	 * return true if other is to the right of from
	 * 
	 * @param fixed
	 * @param test
	 * @return
	 */
	public static boolean toRightOf(Location fixed, Location test) {
		return (test.getCol() - fixed.getCol()) > 0;
	}

	/**
	 * return true if other is to the left of from
	 * 
	 * @param fixed
	 * @param test
	 * @return
	 */
	public static boolean toLeftOf(Location fixed, Location test) {
		return (test.getCol() - fixed.getCol()) < 0;
	}
}
