package com.neodem.componentConnector.graphics;

import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 * 
 */
public interface Display {
	public void displaySet(ComponentSet set);
	public String asString(ComponentSet set);
}
