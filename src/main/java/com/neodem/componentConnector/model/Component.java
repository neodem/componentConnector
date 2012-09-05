package com.neodem.componentConnector.model;



/**
 * all components are done vertically with pin numbers from the top to the
 * bottom on each side. (ie. there can be a 1R and a 1L)
 * 
 * @author vfumo
 * 
 */
public class Component extends AbstractLocatable implements Connectable, Locatable, Comparable<Component> {

	public Component(String name, String id, int pinCount) {
		super(name, id, pinCount, 0, 0);
	}

	public Component(String name, String id, int pinCount, int x, int y) {
		super(name, id, pinCount, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Component [name=" + getName() + ", inverted=" + isInverted() + ", moveable=" + isMoveable() + ", location="
				+ getLocation() + "]";
	}
}
