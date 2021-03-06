package com.neodem.componentConnector.model.sets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Endpoint;
import com.neodem.componentConnector.model.Locatable;
import com.neodem.componentConnector.model.Location;
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
	private Set<Endpoint> endpoints = new HashSet<Endpoint>();

	private Collection<Connection> connections = new HashSet<Connection>();

	private Map<Location, Component> componentPositions = new HashMap<Location, Component>();

	private static Calculator calc = new DefaultCalculator();

	public ComponentSet(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	/**
	 * copy a set
	 * 
	 * @param input
	 * @return
	 */
	public static ComponentSet copy(ComponentSet input) {
		ComponentSet setCopy = new ComponentSet(input);

		for (Component component : input.components.values()) {
			setCopy.addComponent(new Component(component));
		}
		
		for(Endpoint endpoint : input.endpoints) {
			setCopy.addEndpoint(new Endpoint(endpoint));
		}
		
		for(Connection con : input.connections) {
			String fromName = con.getFrom().getName();
			String toName = con.getTo().getName();
			
			Component from = setCopy.getComponent(fromName);
			Component to = setCopy.getComponent(toName);
			setCopy.addConnection(new Connection(from, con.getFromPins(), to, con.getToPins()));
		}
		
		setCopy.recalculate();

		return setCopy;
	}

	public void addEndpoint(Endpoint endpoint) {
		endpoints.add(endpoint);
	}

	public Set<Endpoint> getEndpoints() {
		return endpoints;
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

	public Component getComponent(String id) {
		return components.get(id);
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
	 * return a row of the set, ordered based on positions
	 * with the left most being col = 0 and the right most
	 * being col = sizeX-1
	 * 
	 * @param rowNum
	 * @return
	 */
	public List<Component> getRow(int rowNum) {
		List<Component> row = new ArrayList<Component>(sizeX);

		if (rowNum >= 0 && rowNum < sizeY) {
			for(int colIndex = 0; colIndex < sizeX; colIndex++) {
				Location loc = new Location(colIndex, rowNum);
				row.add(componentPositions.get(loc));
			}
		}

		return row;
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

	/**
	 * shift the target component one to the left (if we can)
	 * 
	 * @param target
	 */
	public void shiftOneLeft(Locatable target) {
		if (target == null) {
			return;
		}
		int xLoc = target.getxLoc();

		if (xLoc == 0) {
			return; // since we are all the way left
		}

		int xLocNew = xLoc - 1;
		moveComponentX(target, xLoc, xLocNew);
	}

	/**
	 * shift the target component one to the right (if we can)
	 * 
	 * @param target
	 */
	public void shiftOneRight(Locatable target) {
		if (target == null) {
			return;
		}
		int xLoc = target.getxLoc();

		if (xLoc == (sizeX - 1)) {
			return; // since we are all the way right
		}

		int xLocNew = xLoc + 1;
		moveComponentX(target, xLoc, xLocNew);
	}

	/**
	 * shift the target component one down (if we can)
	 * 
	 * @param target
	 */
	public void shiftOneDown(Locatable target) {
		if (target == null) {
			return;
		}
		int yLoc = target.getyLoc();

		if (yLoc == (sizeY - 1)) {
			return; // since we are all the way at the bottom already
		}

		int yLocNew = yLoc + 1;
		moveComponentY(target, yLoc, yLocNew);
	}

	/**
	 * shift the target component one up (if we can)
	 * 
	 * @param target
	 */
	public void shiftOneUp(Locatable target) {
		if (target == null) {
			return;
		}
		int yLoc = target.getyLoc();

		if (yLoc == 0) {
			return; // since we are all the way at the top already
		}

		int yLocNew = yLoc - 1;
		moveComponentY(target, yLoc, yLocNew);
	}
	
	/**
	 * move the target component to the spot given. If the spot is 
	 * occupied, we swap them.
	 * 
	 * @param target
	 * @param toX
	 * @param toY
	 */
	public void moveTo(Locatable target, int toX, int toY) {
		int fromX = target.getxLoc();
		int fromY = target.getyLoc();
		
		Locatable other = componentPositions.get(new Location(toX, toY));
		if(other != null) {
			other.setxLoc(fromX);
			other.setyLoc(fromY);
		}
		
		target.setxLoc(toX);
		target.setyLoc(toY);
		
		resetComponentPositions();
		recalculate();
	}
	

	/**
	 * move the target component on the Y axis from one spot to another and if
	 * that spot is occupied swap them.
	 * 
	 * @param target
	 * @param fromY
	 * @param toY
	 */
	private void moveComponentY(Locatable target, int fromY, int toY) {
		target.setyLoc(toY);
		int xLoc = target.getxLoc();

		Component other = componentPositions.get(new Location(xLoc, toY));
		if (other != null) {
			// we move the other into the old spot
			other.setyLoc(fromY);
		}
		resetComponentPositions();
		recalculate();
	}

	/**
	 * move the target component on the X axis from one spot to another and if
	 * that spot is occupied swap them.
	 * 
	 * @param target
	 * @param fromY
	 * @param toY
	 */
	private void moveComponentX(Locatable target, int fromX, int toX) {
		target.setxLoc(toX);
		int yLoc = target.getyLoc();

		Component other = componentPositions.get(new Location(toX, yLoc));
		if (other != null) {
			// we move the other into the old spot
			other.setxLoc(fromX);
		}
		resetComponentPositions();
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

	private void resetComponentPositions() {
		// update positions
		componentPositions.clear();
		for (Component r : components.values()) {
			int x = r.getxLoc();
			int y = r.getyLoc();
			componentPositions.put(new Location(x, y), r);
		}
	}

	public int recalculate() {
		totalSize = calc.calculateSetSize(this);
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
			}
		});
		return conList;
	}

	public Map<Location, Component> getComponentPositions() {
		return componentPositions;
	}

	public Map<String, Component> getComponents() {
		return components;
	}

	public int getNumColumns() {
		return sizeX;
	}
}
