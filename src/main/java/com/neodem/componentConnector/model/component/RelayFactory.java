package com.neodem.componentConnector.model.component;

import java.util.Collection;
import java.util.HashSet;

public class RelayFactory extends ComponentFactory {
	
	private static Collection<ComponentDefinition> defs;
	static {
		defs = new HashSet<ComponentDefinition>(1);
		defs.add(new RelayDefinition());
	}
	
	public RelayFactory() {
		super(defs);
	}
	
	public Component make(String name, int x, int y) {
		Component c = super.make("relay", name);
		c.setxLoc(x);
		c.setyLoc(y);
		return c;
	}

}
