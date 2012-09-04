package com.neodem.componentConnector.model.component;

import com.neodem.componentConnector.model.AbstractLocatable;
import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Locatable;


/**
 * all components are done vertically with pin numbers from the top to the
 * bottom on each side. (ie. there can be a 1R and a 1L)
 * 
 * @author vfumo
 * 
 */
public class Component extends AbstractLocatable implements Connectable, Locatable, Comparable<Component> {

	public Component(String name, int pinCount) {
		super(name, pinCount, 0, 0);
	}

	public Component(String name, int pinCount, int x, int y) {
		super(name, pinCount, x, y);
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
