package com.neodem.componentConnector.model.sets;

import java.util.Collection;
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
	 * <id, item>
	 */
	private Map<String, SetItem> items = new HashMap<String, SetItem>();

	private Set<Location> usedLocations = new HashSet<Location>();

	/**
	 * make a new (empty) component set
	 * 
	 * @param rows
	 * @param cols
	 */
	public ComponentSet(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	/**
	 * make a new component set with the size of the given set. (but empty)
	 * 
	 * @param set
	 */
	public ComponentSet(ComponentSet set) {
		this.rows = set.rows;
		this.cols = set.cols;
	}

	/**
	 * make a new componentSet as a copy of the given set.
	 * 
	 * @param input
	 * @return
	 */
	public static ComponentSet copy(final ComponentSet input) {
		ComponentSet copy = new ComponentSet(input);
		copy.items = Collections.copyMap(input.items);
		copy.usedLocations = new HashSet<Location>(input.usedLocations.size());
		copy.usedLocations.addAll(input.usedLocations);
		return copy;
	}

	/**
	 * add a component to the set.
	 * 
	 * @param i
	 * @param loc
	 * @param inverted
	 * @throws IllegalArgumentException
	 *             if the location is out of bounds or the component has a
	 *             duplicate id or there is a component already in the location
	 *             given
	 */
	public void addItem(BaseComponent i, Location loc, Boolean inverted) {
		if (i.isValid()) {

			int col = loc.getCol();
			if (col >= cols) {
				throw new IllegalArgumentException("Item Column is out of bounds");
			}

			int row = loc.getRow();
			if (row >= rows) {
				throw new IllegalArgumentException("Item Row is out of bounds");
			}

			String id = i.getId();
			if (items.containsKey(id)) {
				throw new IllegalArgumentException("Item has a duplicate key");
			}

			registerLocation(loc);

			registerItem(i, loc, inverted);
		} else {
			throw new IllegalArgumentException("Item is not valid");
		}
	}

	/**
	 * move the component to a new location. Note that the location must be
	 * empty.
	 * 
	 * @param i
	 * @param newLocation
	 */
	public void moveItem(BaseComponent i, Location newLocation) {
		String id = i.getId();
		SetItem si = items.get(id);
		if (si != null) {
			// put it into its new place (if allowable)
			registerLocation(newLocation);

			// remove from old location
			unregsisterLocation(si.getItemLocation());

			// update the item
			registerItem(si.getItem(), newLocation, si.getInverted());
		}
	}

	public void invertItem(BaseComponent i) {
		SetItem si = items.get(i.getId());
		if (si != null) {
			registerItem(si.getItem(), si.getItemLocation(), true);
		}
	}

	public void unInvertItem(BaseComponent i) {
		SetItem si = items.get(i.getId());
		if (si != null) {
			registerItem(si.getItem(), si.getItemLocation(), false);
		}
	}

	public boolean isInverted(BaseComponent i) {
		SetItem si = items.get(i.getId());
		return si.getInverted();
	}

	/**
	 * get the number of rows in the set
	 * 
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * get the number of columns in the set
	 * 
	 * @return
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * get a copy of all the SetItem in the set. A SetItem lists the component,
	 * the location and weather it is inverted.
	 * 
	 * @return a Map by Id of the SetItems
	 */
	public Map<String, SetItem> getItems() {
		return Collections.copyMap(items);
	}

	/**
	 * get a collection of all the components in the set
	 * 
	 * @return
	 */
	public Collection<BaseComponent> getAllComponents() {
		Collection<BaseComponent> comps = new HashSet<BaseComponent>(items.size());
		for (SetItem i : items.values()) {
			comps.add(i.getItem());
		}
		return comps;
	}

	/**
	 * if it exists, get a copy of a SetItem for a given id
	 * 
	 * @param toId
	 * @return
	 */
	public SetItem getItemFromId(String toId) {
		SetItem i = items.get(toId);
		if (i != null) {
			return new SetItem(i);
		}
		return null;
	}

	private void registerItem(BaseComponent i, Location loc, Boolean inverted) {
		SetItem si = new SetItem(i, loc, inverted);
		items.put(i.getId(), si);
	}

	private void registerLocation(Location loc) {
		if (usedLocations.contains(loc)) {
			throw new IllegalArgumentException("There is an item already in that location");
		}
		usedLocations.add(loc);
	}

	private void unregsisterLocation(Location loc) {
		usedLocations.remove(loc);
	}
}