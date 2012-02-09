package com.neodem.componentConnector.model.component;

import java.util.Collection;
import java.util.HashSet;

import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.Pin;

/**
 * all components are done vertically with pin numbers from the top to the
 * bottom on each side. (ie. there can be a 1R and a 1L)
 * 
 * @author vfumo
 * 
 */
public class Component implements Comparable<Component> {

	private String type;

	private String name;

	/**
	 * this means that the sides are reversed
	 */
	private boolean inverted = false;

	/**
	 * flag for movability.
	 */
	private boolean moveable = true;

	private Location location;

	private int pinCount;

	private Collection<Pin> pins = new HashSet<Pin>();

	public Component(String type, String name, int pinCount) {
		this(type, name, pinCount, 0, 0);
	}

	public Component(String type, String name, int pinCount, int x, int y) {
		location = new Location(x, y);
		inverted = false;
		moveable = true;
		this.type = type;
		this.name = name;
		this.pinCount = pinCount;
	}

	/**
	 * return all pins that match this label
	 * 
	 * @param pinLabel
	 * @return empty collection if none match
	 */
	public Collection<Pin> getPins(String pinLabel) {
		Collection<Pin> returnPins = new HashSet<Pin>(pinCount);
		
		for (Pin p : pins) {
			if (p.getLabel().equals(pinLabel)) {
				returnPins.add(p);
			}
		}
		return returnPins;
	}

	public int compareTo(Component other) {
		return name.compareTo(other.getName());
	}

	/**
	 * make one rotation of the relay
	 */
	public void invert() {
		if (inverted) {
			inverted = false;
		} else {
			inverted = true;
		}
	}

	public void setLocation(Location location) {
		this.location = location;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (moveable ? 1231 : 1237);
		result = prime * result + (inverted ? 1231 : 1237);
		result = prime * result + location.hashCode();
		return result;
	}

	/**
	 * only checks for the same name.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Component)) {
			return false;
		}
		Component other = (Component) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}

		return true;
	}

	public boolean isValid() {
		// TODO also maybe check to make sure that the connections aren't
		// duplicated, etc.
		return true;
	}

	/**
	 * @return the rotated
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * @return the xLoc
	 */
	public int getxLoc() {
		return location.getX();
	}

	/**
	 * @param xLoc
	 *            the xLoc to set
	 */
	public void setxLoc(int xLoc) {
		int oldY = location.getY();
		location = new Location(xLoc, oldY);
	}

	/**
	 * @return the yLoc
	 */
	public int getyLoc() {
		return location.getY();
	}

	/**
	 * @param yLoc
	 *            the yLoc to set
	 */
	public void setyLoc(int yLoc) {
		int oldX = location.getX();
		location = new Location(oldX, yLoc);
	}

	/**
	 * @param moveable
	 *            the moveable to set
	 */
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	/**
	 * @return the moveable
	 */
	public boolean isMoveable() {
		return moveable;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	/**
	 * 
	 * @param pinNumber
	 * @param componentSide
	 * @param label
	 * @return
	 */
	public Collection<Pin> addPin(int pinNumber, String label) {
		return addPin(new Pin(pinNumber, label));
	}

	/**
	 * 
	 * @param pinNumber
	 * @param componentSide
	 * @param label
	 * @return
	 */
	public Collection<Pin> addPin(int pinNumber) {
		return addPin(pinNumber, "");
	}

	/**
	 * 
	 * @param pin
	 * @return
	 */
	public Collection<Pin> addPin(Pin pin) {
		pins.add(pin);
		return pins;
	}

	/**
	 * @return the pins
	 */
	public Collection<Pin> getPins() {
		return pins;
	}

	/**
	 * @param pins
	 *            the pins to set
	 */
	public void setPins(Collection<Pin> pins) {
		this.pins = pins;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	public int getNumberofPins() {
		return pinCount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Component [type=" + type + ", name=" + name + ", inverted=" + inverted + ", moveable=" + moveable
				+ ", location=" + location + "]";
	}
}
