package com.neodem.componentConnector.tools;

import java.util.Collection;
import java.util.Map;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.model.sets.SetItem;

/**
 * 
 * @author vfumo
 * 
 */
public class Calculator {

	private CalculationStrategy s;

	public Calculator() {
		s = new DefaultCalculationStrategy();
	}

	public Calculator(CalculationStrategy s) {
		this.s = s;
	}

	/**
	 * for a given set, calcuate a score
	 * 
	 * @param set
	 * @return
	 */
	public int calculateSetScore(ComponentSet set) {
		Map<String, SetItem> items = set.getItems();

		int score = 0;
		for (SetItem item : items.values()) {
			score += calculateItemScore(item, set);
		}

		return score;
	}

	/**
	 * for a given setItem, we calculate its score (of all of its connections)
	 * 
	 * @param setItem
	 * @param set
	 * @return
	 */
	public int calculateItemScore(SetItem setItem, ComponentSet set) {
		BaseComponent item = setItem.getItem();
		Collection<Connection> connections = item.getConnections();

		int score = 0;
		for (Connection c : connections) {
			SetItem toItem = set.getItemFromId(c.getToId());
			if (toItem != null) {
				score += calculateItemConnectionScore(setItem, toItem, c);
			}
		}

		return score;
	}

	/**
	 * for a given connection we calculate its score.
	 * 
	 * @param from
	 *            the item we're connecting from
	 * @param to
	 *            the item we're connecting to
	 * @param c
	 *            the connection on the item we are scoring
	 * @return the score
	 */
	public int calculateItemConnectionScore(SetItem from, SetItem to, Connection c) {
		BaseComponent fromItem = from.getItem();
		Location fromLoc = from.getItemLocation();
		Boolean fromInv = from.getInverted();

		BaseComponent toItem = to.getItem();
		Location toLoc = to.getItemLocation();
		Boolean toInv = to.getInverted();

		int abs = calculateAbsoluteScore(fromLoc, toLoc);
		int rot = calculateRotationalScore(fromItem, fromLoc, fromInv, c.getFromPin(), toItem, toLoc, toInv,
				c.getToPin());

		return abs + rot;
	}

	/**
	 * give the distance between relays. We give a -1 for being on same row or
	 * column
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	protected int calculateAbsoluteScore(Location itemLocation, Location otherItemLoc) {
		int fromY = itemLocation.getRow();
		int toY = otherItemLoc.getRow();

		int fromX = itemLocation.getCol();
		int toX = otherItemLoc.getCol();

		int absoluteHDistance = s.relativeDistance(toX, fromX);
		int absoluteVDistance = s.relativeDistance(toY, fromY);

		int offset = 0;

		if (fromY == toY) {
			offset--;
		}

		if (fromX == toX) {
			offset--;
		}

		return absoluteHDistance + absoluteVDistance + offset;
	}

	protected int calculateRotationalScore(BaseComponent from, Location fromLoc, Boolean fromInverted, Pin fromPin,
			BaseComponent to, Location toLoc, Boolean toInverted, Pin toPin) {

		Side fromSide = from.getSideForPin(fromInverted, fromPin);
		Side toSide = to.getSideForPin(toInverted, toPin);

		if (LocationTools.toLeftOf(fromLoc, toLoc)) {
			return s.toLeftOfFrom(fromSide, toSide);
		} else if (LocationTools.toRightOf(fromLoc, toLoc)) {
			return s.toRightOfFrom(fromSide, toSide);
		}

		// we are horizontally aligned
		return s.aligned(fromSide, toSide);
	}

}
