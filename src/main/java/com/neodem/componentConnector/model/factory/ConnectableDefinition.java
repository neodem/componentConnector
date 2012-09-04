package com.neodem.componentConnector.model.factory;

import java.util.Collection;
import java.util.HashSet;

import com.neodem.componentConnector.model.Pin;


public class ConnectableDefinition {

	private String id;
	
	/**
	 * the number of pins on the component
	 */
	private int pinSize;
	
	private String type;
	
	/**
	 * the pins 
	 */
	private Collection<Pin> pins;

	public ConnectableDefinition(String id, String type, int size) {
		this.id = id;
		this.type = type;
		this.pinSize = size;
		pins = new HashSet<Pin>(size);
	}

	public void addPin(int number, String name) {
		Pin p = new Pin(number, name);
		pins.add(p);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the pinSize
	 */
	public int getPinSize() {
		return pinSize;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the pins
	 */
	public Collection<Pin> getPins() {
		return pins;
	}

	/**
	 * @param pins the pins to set
	 */
	public void setPins(Collection<Pin> pins) {
		this.pins = pins;
	}

}
