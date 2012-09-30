package com.neodem.componentConnector.model;

import java.util.Collection;

public interface Connectable extends Nameable {

	int getNumberofPins();

	Collection<Pin> getPins(String pinLabel);

	public Side getSideForPin(Boolean inverted, Pin fromPin);

	/**
	 * for a given pin, determine the 'index' (From top to bottom) where the pin
	 * connects
	 * 
	 * @param fromPin
	 * @return
	 */
	public int determineSideIndex(Boolean inverted, Pin fromPin);
}
