package com.neodem.componentConnector.model.sets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.tools.Calculator;
import com.neodem.componentConnector.tools.DefaultCalculator;

/**
 * @author vfumo
 * 
 */
public class ComponentSet {

	protected int sizeX;
	protected int sizeY;

	private int totalSize;

	private Map<String, Component> components = new HashMap<String, Component>();

	private Map<Location, Component> componentPositions = new HashMap<Location, Component>();

	private Collection<Connection> connections = new HashSet<Connection>();
	
	private static Calculator calc = new DefaultCalculator();

	public ComponentSet(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	/**
	 * not a copy constructor. This simply makes a new set of the same size as
	 * the given one.
	 * 
	 * @param set
	 */
	public ComponentSet(ComponentSet set) {
		this.sizeX = set.sizeX;
		this.sizeY = set.sizeY;
	}

	public Component getComponent(String id) {
		return components.get(id);
	}

	public List<List<Component>> getAllRows() {
		List<List<Component>> rows = new ArrayList<List<Component>>(sizeY);
		for (int rowIndex = 0; rowIndex < sizeY; rowIndex++) {
			rows.add(getRow(rowIndex));
		}

		return rows;
	}

	public Collection<Connection> getConnectionsForComponent(Component c) {
		Collection<Connection> result = new HashSet<Connection>();

		for (Connection con : connections) {
			if (con.uses(c)) {
				result.add(con);
			}
		}

		return result;
	}

	/**
	 * a very strange operation.. this assumes that 'other' has the same # of
	 * relays with the same names. It will copy the location and rotation of the
	 * relays from 'other' into this one
	 * 
	 * @param other
	 */
	public void copyRelayStates(ComponentSet other) {
		Collection<Component> others = other.getAllComponents();
		for (Component otherRelay : others) {
			Component ourRelay = components.get(otherRelay.getName());
			ourRelay.setLocation(otherRelay.getLocation());
			ourRelay.setInverted(otherRelay.isInverted());
		}
		recalculate();
	}

	/**
	 * return a row of the relay set, ordered
	 * 
	 * @param rowNum
	 * @return
	 */
	public List<Component> getRow(int rowNum) {
		List<Component> row = new ArrayList<Component>(sizeX);

		if (rowNum >= 0 && rowNum < sizeY) {
			for (Location l : componentPositions.keySet()) {
				if (l.getY() == rowNum) {
					row.add(componentPositions.get(l));
				}
			}

			Collections.sort(row, new Comparator<Component>() {
				public int compare(Component r1, Component r2) {
					return r1.getxLoc() - r2.getxLoc();
				}
			});
		}

		return row;
	}

	public void addComponent(Component c) {
		if (c.isValid()) {

			int xLoc = c.getxLoc();
			if (xLoc >= sizeX) {
				throw new IllegalArgumentException("component X location is out of bounds");
			}

			int yLoc = c.getyLoc();
			if (yLoc >= sizeY) {
				throw new IllegalArgumentException("component Y location is out of bounds");
			}

			Location location = c.getLocation();
			Component curr = componentPositions.get(location);

			if (curr != null) {
				throw new IllegalArgumentException("component " + curr.getName() + " already in that location");
			}

			String id = c.getName();
			if (components.containsKey(id)) {
				throw new IllegalArgumentException("component has a duplicate key");
			}

			components.put(id, c);
			componentPositions.put(location, c);
			recalculate();
		}
	}

	public Connection addConnection(Connection c) {
		if (c.isValid()) {
			connections.add(c);
			recalculate();
		}
		return c;
	}

	public String displayAll() {
		StringBuffer b = new StringBuffer();

		b.append("Size = ");
		b.append(totalSize);
		b.append('\n');

		List<Component> cList = new ArrayList<Component>(components.values());
		Collections.sort(cList);

		for (Component c : cList) {
			b.append(c.toString());
			b.append('\n');
		}

		for (Connection c : connections) {
			addConnectionToBuffer(b, c);
		}

		return b.toString();
	}

	private void addConnectionToBuffer(StringBuffer b, Connection c) {
		b.append(c);
		b.append(" = ");
		b.append(calc.calculateDistance(c));
		b.append('\n');
	}

	public String displayConnections() {
		StringBuffer b = new StringBuffer();

		b.append("Size = ");
		b.append(totalSize);
		b.append('\n');

		List<Connection> conList = getAllConnectionsSortedByLargest(this);
		
		for (Connection c : conList) {
			addConnectionToBuffer(b, c);
		}

		return b.toString();
	}

	public void shiftOneLeft(Component target) {
		if (target == null) {
			return;
		}
		int xLoc = target.getxLoc();

		if (xLoc == 0) {
			return; // since we are all the way left
		}

		int xLocOther = xLoc - 1;
		moveComponentX(target, xLoc, xLocOther);
	}

	public void shiftOneRight(Component target) {
		if (target == null) {
			return;
		}
		int xLoc = target.getxLoc();

		if (xLoc == (sizeX - 1)) {
			return; // since we are all the way right
		}

		int xLocOther = xLoc + 1;
		moveComponentX(target, xLoc, xLocOther);
	}

	public void shiftOneDown(Component target) {
		if (target == null) {
			return;
		}
		int yLoc = target.getyLoc();

		if (yLoc == (sizeY - 1)) {
			return; // since we are all the way at the bottom already
		}

		int yLocOther = yLoc + 1;
		moveComponentY(target, yLoc, yLocOther);
	}

	public void shiftOneUp(Component target) {
		if (target == null) {
			return;
		}
		int yLoc = target.getyLoc();

		if (yLoc == 0) {
			return; // since we are all the way at the top already
		}

		int yLocOther = yLoc - 1;
		moveComponentY(target, yLoc, yLocOther);
	}

	private void moveComponentY(Component target, int fromY, int toY) {
		target.setyLoc(toY);
		int xLoc = target.getxLoc();

		Component other = componentPositions.get(new Location(xLoc, toY));
		if (other != null) {
			// we move the other into the old spot
			other.setyLoc(fromY);
		}
		recalculate();
	}

	private void moveComponentX(Component target, int fromX, int toX) {
		target.setxLoc(toX);
		int yLoc = target.getyLoc();

		Component other = componentPositions.get(new Location(toX, yLoc));
		if (other != null) {
			// we move the other into the old spot
			other.setxLoc(fromX);
		}
		recalculate();
	}

	public Collection<Connection> getAllConnections() {
		return connections;
	}

	public Collection<Component> getAllComponents() {
		return components.values();
	}

	public void clear() {
		components.clear();
		componentPositions.clear();
	}

	public int recalculate() {

		// update positions
		componentPositions.clear();
		for (Component r : components.values()) {
			int x = r.getxLoc();
			int y = r.getyLoc();
			componentPositions.put(new Location(x, y), r);
		}

		// update total size
		totalSize = calculateTotalDistance(this);
		return totalSize;
	}

	public int getTotalSize() {
		return totalSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connections == null) ? 0 : connections.hashCode());
		result = prime * result + ((components == null) ? 0 : components.hashCode());
		result = prime * result + sizeX;
		result = prime * result + sizeY;
		result = prime * result + totalSize;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ComponentSet)) {
			return false;
		}
		ComponentSet other = (ComponentSet) obj;
		if (connections == null) {
			if (other.connections != null) {
				return false;
			}
		} else if (!connections.equals(other.connections)) {
			return false;
		}
		if (components == null) {
			if (other.components != null) {
				return false;
			}
		} else if (!components.equals(other.components)) {
			return false;
		}
		if (sizeX != other.sizeX) {
			return false;
		}
		if (sizeY != other.sizeY) {
			return false;
		}
		if (totalSize != other.totalSize) {
			return false;
		}
		return true;
	}

	/**
	 * @return the sizeX
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * @return the sizeY
	 */
	public int getSizeY() {
		return sizeY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neodem.relayLocator.tools.RelaySetTools#calculateTotalDistance(com
	 * .neodem.relayLocator.model.RelaySet)
	 */
	public static int calculateTotalDistance(ComponentSet set) {
		Collection<Connection> cons = set.getAllConnections();
		int distance = 0;
		
		for (Connection c : cons) {
			distance += calc.calculateDistance(c);
		}

		return distance;
	}

	public static Connection findTheLargestConnection(ComponentSet set) {
		Collection<Connection> cons = set.getAllConnections();
		Connection largest = null;

		int maxDistance = Integer.MIN_VALUE;

		for (Connection c : cons) {
			int distance = calc.calculateDistance(c);
			if (distance > maxDistance) {
				maxDistance = distance;
				largest = c;
			}
		}

		return largest;
	}

	public static List<Connection> getAllConnectionsSortedByLargest(ComponentSet set) {
		Collection<Connection> cons = set.getAllConnections();
		List<Connection> conList = new ArrayList<Connection>(cons);
		Collections.sort(conList, new Comparator<Connection>() {
			public int compare(Connection c1, Connection c2) {
				return calc.calculateDistance(c2) - calc.calculateDistance(c1);
			}});
		return conList;
	}
}
