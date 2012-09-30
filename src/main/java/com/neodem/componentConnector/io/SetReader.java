package com.neodem.componentConnector.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.components.Connectable;
import com.neodem.componentConnector.model.factory.ConnectableDefinition;
import com.neodem.componentConnector.model.factory.ComponentFactory;
import com.neodem.componentConnector.model.sets.AutoAddComponentSet;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class SetReader {

	public static ComponentSet readSetFromFile(File setDef, ComponentFactory factory) {
		ComponentSet set = null;
		// for collecting all connectables
		Map<String, Connectable> connectables = new HashMap<String, Connectable>();

		try {
			// open the file
			Builder builder = new Builder();
			Document doc = builder.build(setDef);
			Element fileRoot = doc.getRootElement();
			Element componentsRoot = fileRoot.getFirstChildElement("components");

			int rows = Integer.parseInt(componentsRoot.getAttributeValue("rows"));
			int cols = Integer.parseInt(componentsRoot.getAttributeValue("cols"));
			boolean autoLocate = Boolean.parseBoolean(componentsRoot.getAttributeValue("autoLocate"));

			// add connectables (components need to be located in the set,
			// endpoints are not so they are just added to the connectables map
			Elements componentElements = componentsRoot.getChildElements();
			if (autoLocate) {
				set = addComponentsWithAutoLocate(connectables, rows, cols, componentElements);
			} else {
				set = addComponents(connectables, rows, cols, componentElements);
			}

			Element connectionsRoot = fileRoot.getFirstChildElement("connections");

			// add connections
			Elements connections = connectionsRoot.getChildElements();
			for (int i = 0; i < connections.size(); i++) {
				Element c = connections.get(i);
				String from = c.getAttributeValue("from");
				String to = c.getAttributeValue("to");
				String fromPinLabel = c.getAttributeValue("fromPin");
				String toPinLabel = c.getAttributeValue("toPin");

				Connectable fromComp = connectables.get(from);
				Connectable toComp = connectables.get(to);

				Collection<Pin> fromPins = fromComp.getPins(fromPinLabel);
				Collection<Pin> toPins = toComp.getPins(toPinLabel);

				Connection con = new Connection(fromComp, fromPins, toComp, toPins);
				set.addConnection(con);
			}

		} catch (ParsingException ex) {
			System.err.println("malformed XML file : " + ex.getMessage());
		} catch (IOException ex) {
			System.err.println("io error : " + ex.getMessage());
		}
		return set;
	}

	private ComponentSet addComponents(Map<String, Connectable> connectables, int rows, int cols,
			Elements componentElements) {
		ComponentSet set;
		set = new ComponentSet(cols, rows);
		for (int i = 0; i < componentElements.size(); i++) {
			Element componentElement = componentElements.get(i);
			String type = componentElement.getAttributeValue("type");
			String name = componentElement.getAttributeValue("name");

			Connectable connectable = factory.make(type, name);
			if (connectable != null) {
				connectables.put(name, connectable);
				if (connectable instanceof Component) {
					Component component = (Component) connectable;
					component.setxLoc(Integer.parseInt(componentElement.getAttributeValue("col")));
					component.setyLoc(Integer.parseInt(componentElement.getAttributeValue("row")));
					component.setInverted(Boolean.parseBoolean(componentElement.getAttributeValue("inv")));
					set.addComponent(component);
				} else if (connectable instanceof Endpoint) {
					set.addEndpoint((Endpoint) connectable);
				}
			}
		}
		return set;
	}

	private ComponentSet addComponentsWithAutoLocate(Map<String, Connectable> connectables, int rows, int cols,
			Elements componentElements) {
		ComponentSet set;
		set = new AutoAddComponentSet(cols, rows);
		for (int i = 0; i < componentElements.size(); i++) {
			Element componentElement = componentElements.get(i);
			String type = componentElement.getAttributeValue("type");
			String name = componentElement.getAttributeValue("name");

			Connectable connectable = factory.make(type, name);
			if (connectable != null) {
				connectables.put(name, connectable);
				if (connectable instanceof Component) {
					((AutoAddComponentSet) set).addComponent((Component) connectable);
				} else if (connectable instanceof Endpoint) {
					set.addEndpoint((Endpoint) connectable);
				}
			}
		}
		return set;
	}



}
