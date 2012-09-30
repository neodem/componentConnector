package com.neodem.componentConnector.model.sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.neodem.common.utility.Collections;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.components.BaseComponent;

/**
 * holds a collection of Items and keeps track of their locations and if they
 * are inverted.
 * 
 * @author vfumo
 * 
 */
public class ComponentSet {

	int rows;
	int cols;

	/**
	 */
	private Map<String, SetItem> items = new HashMap<String, SetItem>();

	private Set<Location> usedLocations = new HashSet<Location>();

	public ComponentSet(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	/**
	 * 
	 * @param set
	 */
	public ComponentSet(ComponentSet set) {
		this.rows = set.rows;
		this.cols = set.cols;
	}

	public void addItem(BaseComponent i, Location loc, Boolean inverted) {
		if (i.isValid()) {

			int col = loc.getCol();
			if (col >= cols) {
				throw new IllegalArgumentException("Item Column is out of bounds");
			}

			int row = loc.getRow();
			if (row >= row) {
				throw new IllegalArgumentException("Item Row is out of bounds");
			}

			if (usedLocations.contains(loc)) {
				throw new IllegalArgumentException("There is an item already in that location");
			}
			usedLocations.add(loc);

			String id = i.getId();
			if (items.containsKey(id)) {
				throw new IllegalArgumentException("Item has a duplicate key");
			}

			SetItem si = new SetItem(i, loc, inverted);
			items.put(i.getId(), si);
		} else {
			// TODO log
		}
	}

	public void moveItem(BaseComponent i, Location newLocation) {
		SetItem si = items.get(i.getId());
		if (si != null) {
			items.put(i.getId(), new SetItem(si.getItem(), newLocation, si.getInverted()));
		}
	}

	public void invertItem(BaseComponent i) {
		SetItem si = items.get(i.getId());
		if (si != null) {
			items.put(i.getId(), new SetItem(si.getItem(), si.getItemLocation(), true));
		}
	}

	public void unInvertItem(BaseComponent i) {
		SetItem si = items.get(i.getId());
		if (si != null) {
			items.put(i.getId(), new SetItem(si.getItem(), si.getItemLocation(), false));
		}
	}

	public boolean isInverted(BaseComponent i) {
		SetItem si = items.get(i.getId());
		return si.getInverted();
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Map<String, SetItem> getItems() {
		return Collections.copyMap(items);
	}

}