package com.neodem.componentConnector.model;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class AbstractConnectable extends AbstractNameable implements Connectable {

	private String id;

	private int pinCount;

	private Collection<Pin> pins = new HashSet<Pin>();

	/**
	 * this means that the sides are reversed
	 */
	private boolean inverted = false;

	public AbstractConnectable(String name, String id, int pinCount) {
		super(name);
		this.id = id;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 59).appendSuper(super.hashCode()).append(inverted).append(id).append(pinCount)
				.append(pins).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		AbstractConnectable rhs = (AbstractConnectable) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(inverted, rhs.inverted).append(id, rhs.id)
				.append(pinCount, rhs.pinCount).append(pins, rhs.pins).isEquals();
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

	public int getNumberofPins() {
		return pinCount;
	}

	public String getId() {
		return id;
	}

	public int getPinCount() {
		return pinCount;
	}
}
