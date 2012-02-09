package com.neodem.componentConnector.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import com.neodem.componentConnector.graphics.CrudeConsoleDisplay;
import com.neodem.componentConnector.graphics.Display;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.model.component.ComponentDefinition;
import com.neodem.componentConnector.model.component.ComponentFactory;
import com.neodem.componentConnector.model.sets.AutoAddComponentSet;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class DefaultFileConnector implements FileConnector {

	private ComponentFactory factory;

	/**
	 * this only reads in files with relays with no locations attached
	 */
	public ComponentSet read(File componentDefs, File setFile) {
		loadFactory(componentDefs);
		return loadSet(setFile);
	}

	protected void loadFactory(File componentDefs) {
		Collection<ComponentDefinition> defs = loadComponentDefs(componentDefs);
		factory = new ComponentFactory(defs);
	}

	protected AutoAddComponentSet loadSet(File setFile) {
		AutoAddComponentSet set = null;
		try {
			Builder parser = new Builder();
			Document doc = parser.build(setFile);
			Element root = doc.getRootElement();

			// init the set
			int rows = Integer.parseInt(root.getAttributeValue("rows"));
			int cols = Integer.parseInt(root.getAttributeValue("cols"));
			set = new AutoAddComponentSet(cols, rows);

			// add components
			Element relayParent = root.getFirstChildElement("components");
			Elements components = relayParent.getChildElements();
			for (int i = 0; i < components.size(); i++) {
				Element componentElement = components.get(i);
				String type = componentElement.getAttributeValue("type");
				String name = componentElement.getAttributeValue("name");

				Component component = factory.make(type, name);
				if (component != null) {
					set.addComponentAtRandomLocation(component);
				}
			}

			// add connections
			Element cParent = root.getFirstChildElement("connections");
			Elements connections = cParent.getChildElements();
			for (int i = 0; i < connections.size(); i++) {
				Element c = connections.get(i);
				String from = c.getAttributeValue("from");
				String to = c.getAttributeValue("to");
				String fromPinLabel = c.getAttributeValue("fromPin");
				String toPinLabel = c.getAttributeValue("toPin");

				Component fromComp = set.getComponent(from);
				Component toComp = set.getComponent(to);

				Collection<Pin> fromPins = fromComp.getPins(fromPinLabel);
				Collection<Pin> toPins = toComp.getPins(toPinLabel);

				Connection con = new Connection(fromComp, fromPins, toComp, toPins);
				set.addConnection(con);
			}

		} catch (ParsingException ex) {
			System.err.println("Cafe con Leche is malformed today. How embarrassing!");
		} catch (IOException ex) {
			System.err.println("Could not connect to Cafe con Leche. The site may be down.");
		}
		return set;
	}

	private Collection<ComponentDefinition> loadComponentDefs(File componentDefs) {
		Collection<ComponentDefinition> defs = new HashSet<ComponentDefinition>();
		try {
			Builder parser = new Builder();
			Document doc = parser.build(componentDefs);
			Element root = doc.getRootElement();

			Elements definitions = root.getChildElements();
			for (int i = 0; i < definitions.size(); i++) {
				Element definition = definitions.get(i);
				String name = definition.getAttributeValue("name");
				String pinCount = definition.getAttributeValue("pins");

				ComponentDefinition d = new ComponentDefinition(name, Integer.parseInt(pinCount));

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
			System.err.println("Cafe con Leche is malformed today. How embarrassing!");
		} catch (IOException ex) {
			System.err.println("Could not connect to Cafe con Leche. The site may be down.");
		}
		return defs;
	}

	public void writeToFile(File file, ComponentSet set) {
		Display d = new CrudeConsoleDisplay();
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(d.asString(set));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
