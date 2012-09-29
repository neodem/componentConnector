package com.neodem.componentConnector.model;

import java.util.Collection;

public interface Connectable extends Nameable {

	int getNumberofPins();

	Collection<Pin> getPins(String pinLabel);

	boolean isInverted();

	void invert();
}
