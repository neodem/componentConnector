package com.neodem.componentConnector.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.Serializer;

import org.apache.commons.io.FileUtils;

import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Endpoint;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.factory.ConnectableDefinition;
import com.neodem.componentConnector.model.factory.ConnectableFactory;
import com.neodem.componentConnector.model.sets.AutoAddComponentSet;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class DefaultFileConnector implements FileConnector {

	private ConnectableFactory factory;

	public ComponentSet read(File componentsDef, File connectablesDef, File connectionsDef) {
		loadConnectableFactory(connectablesDef);
		return loadSet(componentsDef, connectionsDef);
	}

	protected void loadConnectableFactory(File connectableDefs) {
		Collection<ConnectableDefinition> defs = loadConnectableDefs(connectableDefs);
		factory = new ConnectableFactory(defs);
	}

	protected ComponentSet loadSet(File componentsDef, File connectionsDef) {
		ComponentSet set = null;

		// for collecting all connectables
		Map<String, Connectable> connectables = new HashMap<String, Connectable>();

		try {
			// open the components.xml file
			Builder builder = new Builder();
			Document doc = builder.build(componentsDef);
			Element componentsRoot = doc.getRootElement();

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

			doc = builder.build(connectionsDef);
			Element connectionsRoot = doc.getRootElement();

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

	private Collection<ConnectableDefinition> loadConnectableDefs(File connectableDefs) {
		Collection<ConnectableDefinition> defs = new HashSet<ConnectableDefinition>();
		try {
			Builder parser = new Builder();
			Document doc = parser.build(connectableDefs);
			Element root = doc.getRootElement();

			Elements definitions = root.getChildElements();
			for (int i = 0; i < definitions.size(); i++) {
				Element definition = definitions.get(i);
				String id = definition.getAttributeValue("id");
				String pinCount = definition.getAttributeValue("pins");
				String type = definition.getAttributeValue("type");

				ConnectableDefinition d = new ConnectableDefinition(id, type, Integer.parseInt(pinCount));

				Elements pins = definition.getChildElements();
				for (int j = 0; j < pins.size(); j++) {
					Element pinElement = pins.get(j);
					String pinNumber = pinElement.getAttributeValue("number");
					String pinName = pinElement.getAttributeValue("name");
					d.addPin(Integer.parseInt(pinNumber), pinName);
				}

				defs.add(d);
			}
		} catch (ParsingException ex) {
			System.err.println("malformed XML file : " + ex.getMessage());
		} catch (IOException ex) {
			System.err.println("io error : " + ex.getMessage());
		}
		return defs;
	}

	public void writeToFile(File file, ComponentSet set) {

		// add Components
		Element componentRoot = new Element("components");
		componentRoot.addAttribute(new Attribute("autoLocate", "false"));
		componentRoot.addAttribute(new Attribute("rows", Integer.toString(set.getSizeY())));
		componentRoot.addAttribute(new Attribute("cols", Integer.toString(set.getSizeX())));

		Map<Location, Component> componentPositions = set.getComponentPositions();
		for (Location loc : componentPositions.keySet()) {
			Component c = componentPositions.get(loc);

			Element componentElement = new Element("component");
			componentElement.addAttribute(new Attribute("type", c.getId()));
			componentElement.addAttribute(new Attribute("name", c.getName()));
			componentElement.addAttribute(new Attribute("row", Integer.toString(loc.getY())));
			componentElement.addAttribute(new Attribute("col", Integer.toString(loc.getX())));
			componentElement.addAttribute(new Attribute("inv", Boolean.toString(c.isInverted())));

			componentRoot.appendChild(componentElement);
		}

		// add Endpoints
		Set<Endpoint> endpointSet = set.getEndpoints();
		for (Endpoint e : endpointSet) {

			Element endpointElement = new Element("endpoint");
			endpointElement.addAttribute(new Attribute("type", e.getId()));
			endpointElement.addAttribute(new Attribute("name", e.getName()));

			componentRoot.appendChild(endpointElement);
		}

		Document doc = new Document(componentRoot);

		try {
			OutputStream os = new FileOutputStream(file);
			Serializer serializer = new Serializer(os, "ISO-8859-1");
			serializer.setIndent(4);
			serializer.setMaxLength(120);
			serializer.write(doc);
		} catch (IOException e) {
			System.err.println("io error : " + e.getMessage());
		}

	}
}
