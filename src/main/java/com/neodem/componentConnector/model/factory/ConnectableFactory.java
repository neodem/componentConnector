package com.neodem.componentConnector.model.factory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Endpoint;
import com.neodem.componentConnector.model.component.Component;


public class ConnectableFactory {

	private Map<String, ConnectableDefinition> defMap = new HashMap<String, ConnectableDefinition>();

	public ConnectableFactory(Collection<ConnectableDefinition> defs) {
		for (ConnectableDefinition def : defs) {
			defMap.put(def.getId(), def);
		}
	}

	/**
	 * make a Connectable of the given id with the given name
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public Connectable make(String id, String name) {
		final ConnectableDefinition cd = defMap.get(id);
		Connectable c = null;

		if (cd != null) {
			String type = cd.getType();
			if ("component".equals(type)) {
				return makeComponent(name, cd);
			}
			if ("endpoint".equals(type)) {
				return makeEndpoint(name, cd);
			}
		}

		return c;
	}

	private Connectable makeEndpoint(String name, ConnectableDefinition cd) {
		Endpoint e = new Endpoint(name,  cd.getPinSize());
		e.setPins(cd.getPins());
		return e;
	}

	private Connectable makeComponent(String name, ConnectableDefinition cd) {
		Component c = null;
		c = new Component(name, cd.getPinSize());
		c.setPins(cd.getPins());

		return c;
	}

}
