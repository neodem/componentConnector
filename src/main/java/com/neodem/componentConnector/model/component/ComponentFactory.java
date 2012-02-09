package com.neodem.componentConnector.model.component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ComponentFactory {

	private Map<String, ComponentDefinition> defMap = new HashMap<String, ComponentDefinition>();

	public ComponentFactory(Collection<ComponentDefinition> defs) {
		for (ComponentDefinition def : defs) {
			defMap.put(def.getTypeName(), def);
		}
	}

	public Component make(String type, String name) {
		final ComponentDefinition cd = defMap.get(type);
		Component c = null;
		if (cd != null) {
			c = new Component(type, name, cd.getPinSize()); 
			c.setPins(cd.getPins());
		}
		
		return c;
	}
}
