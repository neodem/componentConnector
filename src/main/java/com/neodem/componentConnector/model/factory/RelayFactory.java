package com.neodem.componentConnector.model.factory;

import java.util.Collection;
import java.util.HashSet;

import com.neodem.componentConnector.model.Component;

public class RelayFactory extends ConnectableFactory {
	
	private static Collection<ConnectableDefinition> defs;
	static {
		defs = new HashSet<ConnectableDefinition>(1);
		defs.add(new RelayDefinition());
	}
	
	public RelayFactory() {
		super(defs);
	}
	
	public Component make(String name, int col, int row) {
		Component c = (Component) super.make("relay", name);
		c.setxLoc(col);
		c.setyLoc(row);
		return c;
	}

}
