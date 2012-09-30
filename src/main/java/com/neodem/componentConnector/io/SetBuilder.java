package com.neodem.componentConnector.io;

import java.util.Collection;
import java.util.Map;

import nu.xom.Attribute;
import nu.xom.Element;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.model.sets.SetItem;

public class SetBuilder {
	
	public static Element makeComponentsElement(ComponentSet set) {
		Element setRoot = new Element("set");
		setRoot.addAttribute(new Attribute("autoLocate", "false"));
		setRoot.addAttribute(new Attribute("rows", Integer.toString(set.getRows())));
		setRoot.addAttribute(new Attribute("cols", Integer.toString(set.getCols())));
		
		Map<String, SetItem> items = set.getItems();
		for(SetItem item : items.values()) {
			setRoot.appendChild(makeItemElement(item));
		}
		
		return setRoot;
	}
	
	// <component type="relay" id="xor2a" row="2" col="2" inv="false">
	private static Element makeItemElement(SetItem setItem) {
		Element component = new Element("component");

		BaseComponent item = setItem.getItem();
		Location itemLocation = setItem.getItemLocation();
		
		component.addAttribute(new Attribute("type", item.getType()));
		component.addAttribute(new Attribute("id", item.getId()));
		component.addAttribute(new Attribute("row", Integer.toString(itemLocation.getRow())));
		component.addAttribute(new Attribute("col", Integer.toString(itemLocation.getCol())));
		component.addAttribute(new Attribute("inv", Boolean.toString(setItem.getInverted())));
		
		Collection<Connection> connections = item.getConnections();
		for(Connection c : connections) {
			component.appendChild(makeConnectionElement(c));
		}
		
		return component;
	}
	
	/**
	 * <connection fromPin="VCC" to="or1a" toPin="IN" />
	 * @param c
	 * @return
	 */
	private static Element makeConnectionElement(Connection c) {
		Element connectionElement = new Element("connection");
		connectionElement.addAttribute(new Attribute("fromPin", c.getFromPin().getLabel()));
		connectionElement.addAttribute(new Attribute("to", c.getToId()));
		connectionElement.addAttribute(new Attribute("toPin", c.getToPin().getLabel()));
		return connectionElement;
	}
}
