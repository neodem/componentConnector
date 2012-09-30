package com.neodem.componentConnector.model.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.componentConnector.model.components.Item;

public class ComponentFactory {

	// defId, def
	private Map<String, ConnectableDefinition> defMap = new HashMap<String, ConnectableDefinition>();

	public ComponentFactory(Collection<ConnectableDefinition> defs) {
		for (ConnectableDefinition def : defs) {
			defMap.put(def.getDefId(), def);
		}
	}

	/**
	 * make a Connectable of the given id with the given name
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public BaseComponent make(String defId, String itemId) {
		final ConnectableDefinition cd = defMap.get(defId);

		if (cd != null) {
			Item e = new Item(itemId, cd.getType(), cd.getPinSize());
			e.setPins(cd.getPins());
			return e;
		}

		return null;
	}
}
