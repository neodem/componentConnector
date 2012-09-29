package com.neodem.componentConnector.model.sets;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.neodem.componentConnector.model.Item;
import com.neodem.componentConnector.model.Location;

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

	public void addItem(Item i, Location loc, Boolean inverted) {
		SetItem si = new SetItem(i, loc, inverted);
		items.put(i.getName(), si);
	}

	public void moveItem(Item i, Location newLocation) {
		SetItem si = items.get(i.getName());
		if (si != null) {
			items.put(i.getName(), new SetItem(si.getItem(), newLocation, si.getInverted()));
		}
	}

	public void invertItem(Item i) {
		SetItem si = items.get(i.getName());
		if (si != null) {
			items.put(i.getName(), new SetItem(si.getItem(), si.getItemLocation(), true));
		}
	}

	public void unInvertItem(Item i) {
		SetItem si = items.get(i.getName());
		if (si != null) {
			items.put(i.getName(), new SetItem(si.getItem(), si.getItemLocation(), false));
		}
	}

	public boolean isInverted(Item i) {
		SetItem si = items.get(i.getName());
		return si.getInverted();
	}
	
	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Map<String, SetItem> getItems() {
		Map<String, SetItem> copy = new HashMap<String, SetItem>(items.size());
		for(String key : copy.keySet()) {
			copy.put(key, items.get(key));
		}
		return copy;
	}

}