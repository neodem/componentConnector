package com.neodem.componentConnector.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.Serializer;

import com.neodem.componentConnector.model.factory.ConnectableDefinition;
import com.neodem.componentConnector.model.factory.ComponentFactory;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class DefaultFileConnector implements FileConnector {
	private ComponentFactory factory;

	public DefaultFileConnector(File factoryDef) {
		Collection<ConnectableDefinition> defs = loadConnectableDefs(factoryDef);
		factory = new ComponentFactory(defs);
	}

	public void writeComponentSetToFile(ComponentSet set, File file) {

		Element root = new Element("ComponentConnectorDef");
		root.appendChild(SetBuilder.makeComponentsElement(set));

		Document doc = new Document(root);

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

	public ComponentSet readIntoComponentSet(File setDef) {
		return SetReader.readSetFromFile(setDef, factory);
	}
	
	private Collection<ConnectableDefinition> loadConnectableDefs(File connectableDefs) {
		Collection<ConnectableDefinition> definitionCollection = new HashSet<ConnectableDefinition>();
		try {
			Builder parser = new Builder();
			Document doc = parser.build(connectableDefs);
			Element defs = doc.getRootElement();
			Elements definitions = defs.getChildElements();
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

				definitionCollection.add(d);
			}
		} catch (ParsingException ex) {
			System.err.println("malformed XML file : " + ex.getMessage());
		} catch (IOException ex) {
			System.err.println("io error : " + ex.getMessage());
		}
		return definitionCollection;
	}
}
