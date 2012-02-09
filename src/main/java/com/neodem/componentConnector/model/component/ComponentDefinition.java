package com.neodem.componentConnector.model.component;

import java.util.Collection;
import java.util.HashSet;

import com.neodem.componentConnector.model.Pin;

public class ComponentDefinition {

	private String typeName;
	
	/**
	 * the number of pins on the component
	 */
	private int pinSize;
	
	/**
	 * the pins 
	 */
	private Collection<Pin> pins;

	public ComponentDefinition(String typeName, int size) {
		this.typeName = typeName;
		this.pinSize = size;
		pins = new HashSet<Pin>(size);
	}

	public void addPin(int number, String name) {
		Pin p = new Pin(number, name);
		pins.add(p);
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName
	 *            the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return the pinSize
	 */
	public int getPinSize() {
		return pinSize;
	}

	/**
	 * @param pinSize
	 *            the pinSize to set
	 */
	public void setPinSize(int pinSize) {
		this.pinSize = pinSize;
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
}
