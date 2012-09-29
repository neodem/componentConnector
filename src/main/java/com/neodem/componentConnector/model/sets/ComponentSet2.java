package com.neodem.componentConnector.model.sets;

import java.util.HashMap;
import java.util.Map;

import com.neodem.componentConnector.model.Item;
import com.neodem.componentConnector.model.Location;

public class ComponentSet2 {

	int rows;
	int cols;
	private Map<String, Item> items = new HashMap<String, Item>();
	private Map<Item, Location> itemLocations = new HashMap<Item, Location>();

	// true if item inverted
	private Map<Item, Boolean> itemInversions = new HashMap<Item, Boolean>();

	public ComponentSet2(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	public void addItem(Item i, Location loc, Boolean inverted) {
		items.put(i.getName(), i);
		itemLocations.put(i, loc);
		itemInversions.put(i, inverted);
	}

	public void moveItem(Item i, Location newLocation) {
		itemLocations.put(i, newLocation);
	}

	public void invertItem(Item i) {
		itemInversions.put(i, true);
	}

	public void unInvertItem(Item i) {
		itemInversions.put(i, false);
	}

	/**
	 * @return the rotated
	 */
	public boolean isInverted(Item i) {
		return itemInversions.get(i);
	}
}
