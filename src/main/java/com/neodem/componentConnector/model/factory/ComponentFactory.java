package com.neodem.componentConnector.model.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.components.BaseComponent;

public class ComponentFactory {

	// type, def
	private Map<String, ConnectableDefinition> defMap = new HashMap<String, ConnectableDefinition>();

	public ComponentFactory(Collection<ConnectableDefinition> defs) {
		for (ConnectableDefinition def : defs) {
			defMap.put(def.getDefId(), def);
		}
	}
	
	/**
	 * get the collection of pins for the given connectable type
	 * @param type
	 * @param label
	 * @return
	 */
	public Collection<Pin> getPinsForTypeAndLabel(String type, String label) {
		final ConnectableDefinition cd = defMap.get(type);
		if (cd != null) {
			return cd.getPinsForLabel(label);
		}
		return null;
	}

	/**
	 * make a Connectable of the given id with the given name
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public BaseComponent make(String type, String itemId) {
		final ConnectableDefinition cd = defMap.get(type);

		if (cd != null) {
			BaseComponent e = new BaseComponent(itemId, type, cd.getPinSize());
			e.setPins(cd.getAllPins());
			return e;
		}

		return null;
	}
}
