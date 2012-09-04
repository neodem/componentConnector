package com.neodem.componentConnector.model.factory;

import java.util.Collection;
import java.util.HashSet;

import com.neodem.componentConnector.model.Pin;

public class RelayDefinition extends ConnectableDefinition {

	private static Collection<Pin> pins;
	static {
		pins = new HashSet<Pin>(5);
		pins.add(new Pin(1, "IN"));
		pins.add(new Pin(5, "VCC"));
		pins.add(new Pin(6, "ON"));
		pins.add(new Pin(7, "OFF"));
		pins.add(new Pin(8, "GND"));
		pins.add(new Pin(12, "IN"));
	}
	
	public RelayDefinition() {
		super("relay", "component", 12);
		setPins(pins);
	}
}
