package com.neodem.componentConnector.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

import com.neodem.componentConnector.graphics.CrudeConsoleDisplay;
import com.neodem.componentConnector.graphics.Display;
import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.model.factory.ConnectableDefinition;
import com.neodem.componentConnector.model.factory.ConnectableFactory;
import com.neodem.componentConnector.model.sets.AutoAddComponentSet;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class DefaultFileConnector implements FileConnector {

	private ConnectableFactory factory;

	/**
	 * this only reads in files with relays with no locations attached
	 */
	public ComponentSet read(File defs, File setFile) {
		loadFactory(defs);
		return loadSet(setFile);
	}

	protected void loadFactory(File connectableDefs) {
		Collection<ConnectableDefinition> defs = loadConnectableDefs(connectableDefs);
		factory = new ConnectableFactory(defs);
	}

	protected ComponentSet loadSet(File setFile) {
		ComponentSet set = null;
		try {
			Builder parser = new Builder();
			Document doc = parser.build(setFile);
			Element root = doc.getRootElement();

			// init the set
			int rows = Integer.parseInt(root.getAttributeValue("rows"));
			int cols = Integer.parseInt(root.getAttributeValue("cols"));
			

			// for collecting all connectables
			Map<String, Connectable> cons = new HashMap<String, Connectable>();

			// add components
			Element parent = root.getFirstChildElement("components");
			String autoLocateString = parent.getAttributeValue("autoLocate");
			boolean autoLocate = Boolean.parseBoolean(autoLocateString);
			
			Elements components = parent.getChildElements();
			if(autoLocate) {
				set = new AutoAddComponentSet(cols, rows);
				for (int i = 0; i < components.size(); i++) {
					Element componentElement = components.get(i);
					String type = componentElement.getAttributeValue("type");
					String name = componentElement.getAttributeValue("name");

					Component component = (Component) factory.make(type, name);
					if (component != null) {
						((AutoAddComponentSet) set).addComponentAtRandomLocation(component);
						cons.put(name, component);
					}
				}
			} else {
				set = new ComponentSet(cols, rows);
				for (int i = 0; i < components.size(); i++) {
					Element componentElement = components.get(i);
					String type = componentElement.getAttributeValue("type");
					String name = componentElement.getAttributeValue("name");
					int row = Integer.parseInt(componentElement.getAttributeValue("row"));
					int col = Integer.parseInt(componentElement.getAttributeValue("col"));

					Component component = (Component) factory.make(type, name);
					if (component != null) {
						component.setxLoc(col);
						component.setyLoc(row);
						set.addComponent(component);
						cons.put(name, component);
					}
				}
			}
			
			// add connectables
			parent = root.getFirstChildElement("connectables");
			Elements connectables = parent.getChildElements();
			for (int i = 0; i < connectables.size(); i++) {
				Element componentElement = connectables.get(i);
				String type = componentElement.getAttributeValue("type");
				String name = componentElement.getAttributeValue("name");

				Connectable con = factory.make(type, name);
				cons.put(name, con);
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

				Connectable fromComp = cons.get(from);
				Connectable toComp = cons.get(to);

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
