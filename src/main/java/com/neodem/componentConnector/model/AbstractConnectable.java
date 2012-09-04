package com.neodem.componentConnector.model;

import java.util.Collection;
import java.util.HashSet;

import com.neodem.componentConnector.model.component.Component;


public abstract class AbstractConnectable implements Connectable {

	private String name;

	private int pinCount;

	private Collection<Pin> pins = new HashSet<Pin>();

	/**
	 * this means that the sides are reversed
	 */
	private boolean inverted = false;
	
	public AbstractConnectable(String name, int pinCount) {
		this.name = name;
		this.pinCount = pinCount;
		this.inverted = false;
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
	
	/**
	 * @return the rotated
	 */
	public boolean isInverted() {
		return inverted;
	}
	
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 17;
		int result = 1;
		result = prime * result + (inverted ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof Connectable)) {
			return false;
		}
		Connectable other = (Connectable) obj;
		if (name == null) {
			if (other.getName() != null) {
				return false;
			}
		} else if (!name.equals(other.getName())) {
			return false;
		}

		return true;
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

	public int getNumberofPins() {
		return pinCount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Connectable [name=" + name + "]";
	}
}
