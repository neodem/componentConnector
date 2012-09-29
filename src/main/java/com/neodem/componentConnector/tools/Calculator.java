package com.neodem.componentConnector.tools;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;

import java.util.Collection;
import java.util.Map;

import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Item;
import com.neodem.componentConnector.model.Locatable;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.model.sets.SetItem;

/**
 * TODO add strategy pattern to plug in diff calc strategies
 * 
 * @author vfumo
 * 
 */
public class Calculator {

	// public static int calculateDistance(Connection c) {
	// int absDistance = calculateAbsoluteDistance(c);
	// int rotateDistance = calculateRotationalDistance(c);
	//
	// return absDistance + rotateDistance;
	// }

	/**
	 * for a given set, calcuate a size (non sensical metric)
	 * 
	 * @param set
	 * @return
	 */
	public static int calculateSetSize(ComponentSet set) {
		Map<String, SetItem> items = set.getItems();

		int size = 0;
		for (SetItem item : items.values()) {
			size += calculateItemSize(item, set);
		}

		return size;
	}

	public static int calculateItemSize(SetItem setItem, ComponentSet set) {
		Item item = setItem.getItem();
		Location itemLocation = setItem.getItemLocation();
		Boolean inverted = setItem.getInverted();
		Collection<Connection> connections = item.getConnections();

		int size = 0;
		for (Connection c : connections) {
			size += calculateItemConnectionSize(itemLocation, inverted, c, set);
		}

		return size;
	}

	private static int calculateItemConnectionSize(Location itemLocation, Boolean inverted, Connection c,
			ComponentSet set) {
		Map<String, SetItem> items = set.getItems();

		String toName = c.getToName();
		SetItem other = items.get(toName);

		Item otherItem = other.getItem();
		Location otherItemLoc = other.getItemLocation();
		Boolean otherItemInv = other.getInverted();

		int abs = calculateAbsoluteDistance(itemLocation, otherItemLoc);
		int rot = calculateRotationalDistance(inverted, otherItemInv, c.getFromPin(), c.getToPin());

		return abs + rot;
	}

	private static int calculateRotationalDistance(Boolean inverted, Boolean otherItemInv, Pin fromPin, Pin toPin) {

		Side fromSide = ComponentTools.getSideForPin(from, fromPin);
		if (inverted) {
			fromSide = fromSide.other();
		}

		Side toSide = ComponentTools.getSideForPin(to, toPin);
		if (otherItemInv) {
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
	 * give the distance between relays. We give a -1 for being on same row or
	 * column
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	private static int calculateAbsoluteDistance(Location itemLocation, Location otherItemLoc) {
		int fromY = itemLocation.getRow();
		int toY = otherItemLoc.getRow();

		int fromX = itemLocation.getCol();
		int toX = otherItemLoc.getCol();

		int absoluteHDistance = distance(toX, fromX);
		int absoluteVDistance = distance(toY, fromY);

		int offset = 0;

		if (fromY == toY) {
			offset--;
		}

		if (fromX == toX) {
			offset--;
		}

		return absoluteHDistance + absoluteVDistance + offset;

	}

	private static int distance(int loc1, int loc2) {

		if (loc1 == loc2) {
			return 0;
		}

		if (loc1 > loc2) {
			return loc1 - loc2;
		}

		return loc2 - loc1;
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
}
