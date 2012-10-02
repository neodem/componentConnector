package com.neodem.componentConnector.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.componentConnector.model.factory.ComponentFactory;
import com.neodem.componentConnector.model.sets.AutoAddComponentSet;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class SetReader {

	public static ComponentSet readSetFromFile(File setDef, ComponentFactory factory) {
		ComponentSet set = null;

		try {
			// open the file
			Builder builder = new Builder();
			Document doc = builder.build(setDef);
			Element fileRoot = doc.getRootElement();
			Element setRoot = fileRoot.getFirstChildElement("set");

			int rows = Integer.parseInt(setRoot.getAttributeValue("rows"));
			int cols = Integer.parseInt(setRoot.getAttributeValue("cols"));
			boolean autoLocate = Boolean.parseBoolean(setRoot.getAttributeValue("autoLocate"));

			Elements componentElements = setRoot.getChildElements();
			if (autoLocate) {
				set = addComponentsWithAutoLocate(rows, cols, componentElements, factory);
			} else {
				set = addComponents(rows, cols, componentElements, factory);
			}

		} catch (ParsingException ex) {
			System.err.println("malformed XML file : " + ex.getMessage());
		} catch (IOException ex) {
			System.err.println("io error : " + ex.getMessage());
		}
		return set;
	}

	// <component type="relay" id="xor2a" row="2" col="2" inv="false">
	private static ComponentSet addComponents(int rows, int cols, Elements componentElements, ComponentFactory factory) {
		ComponentSet set;
		set = new ComponentSet(rows, cols);
		for (int i = 0; i < componentElements.size(); i++) {
			Element componentElement = componentElements.get(i);
			String type = componentElement.getAttributeValue("type");
			String id = componentElement.getAttributeValue("id");
			String row = componentElement.getAttributeValue("row");
			String col = componentElement.getAttributeValue("col");
			String inv = componentElement.getAttributeValue("inv");

			BaseComponent component = factory.make(type, id);
			if (component != null) {
				Elements connections = componentElement.getChildElements();
				if (connections != null) {
					addConnectionsToComponent(connections, component, factory);
				}
				Location loc = new Location(Integer.valueOf(row), Integer.valueOf(col));
				set.addItem(component, loc, Boolean.valueOf(inv));
			}
		}
		return set;
	}

	// // <component type="relay" id="xor2a">
	private static ComponentSet addComponentsWithAutoLocate(int rows, int cols, Elements componentElements,
			ComponentFactory factory) {
		AutoAddComponentSet set;
		set = new AutoAddComponentSet(rows, cols);
		for (int i = 0; i < componentElements.size(); i++) {
			Element componentElement = componentElements.get(i);
			String type = componentElement.getAttributeValue("type");
			String id = componentElement.getAttributeValue("id");

			BaseComponent component = factory.make(type, id);
			if (component != null) {
				Elements connections = componentElement.getChildElements();
				addConnectionsToComponent(connections, component, factory);
				set.addItem(component);
			}
		}
		return set;
	}

	// <connection fromPin="ON" to="xor2b" toType="relay" toPin="OFF" />
	private static void addConnectionsToComponent(Elements connections, BaseComponent component,
			ComponentFactory factory) {
		for (int i = 0; i < connections.size(); i++) {
			Element connectionElement = connections.get(i);
			String fromPinLabel = connectionElement.getAttributeValue("fromPin");
			String toId = connectionElement.getAttributeValue("to");
			String toType = connectionElement.getAttributeValue("toType");
			String toPinLabel = connectionElement.getAttributeValue("toPin");

			Collection<Pin> fromPins = factory.getPinsForTypeAndLabel(component.getType(), fromPinLabel);
			Collection<Pin> toPins = factory.getPinsForTypeAndLabel(toType, toPinLabel);

			Connection connection = new Connection(fromPins, toId, toPins);
			component.addConnection(connection);
		}
	}
}
