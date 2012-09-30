package com.neodem.componentConnector.model.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.neodem.componentConnector.model.Pin;

public class ConnectableDefinition {

	private String defId;

	/**
	 * the number of pins on the component
	 */
	private int pinSize;

	private String type;

	/**
	 * the pins <label, Collection<Pin>>
	 */
	private Map<String, Collection<Pin>> pins;

	public ConnectableDefinition(String defId, String type, int size) {
		this.defId = defId;
		this.type = type;
		this.pinSize = size;
		pins = new HashMap<String, Collection<Pin>>(pinSize);
	}

	public void addPin(int number, String name) {
		Pin p = new Pin(number, name);
		addPin(p);
	}

	/**
	 * @return the id
	 */
	public String getDefId() {
		return defId;
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

	protected void addPin(Pin p) {
		String label = p.getLabel();
		Collection<Pin> pinCollection = pins.get(label);

		if (pinCollection == null) {
			pinCollection = new HashSet<Pin>();
		}

		pinCollection.add(p);

		pins.put(label, pinCollection);
	}

	/**
	 * @return the pins
	 */
	public Collection<Pin> getAllPins() {
		Collection<Pin> allPins = new HashSet<Pin>();
		for (Collection<Pin> sub : pins.values()) {
			allPins.addAll(sub);
		}

		return allPins;
	}

	public Collection<Pin> getPinsForLabel(String label) {
		return pins.get(label);
	}

}
