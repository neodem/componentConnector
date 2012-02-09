package com.neodem.componentConnector.graphics;

import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 * 
 */
public class ConsoleInfoDisplay implements Display {

	public void displaySet(ComponentSet set) {
		System.out.println(asString(set));
	}

	public String asString(ComponentSet set) {
		return set.displayAll();
	}
}
