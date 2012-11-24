package com.neodem.componentConnector.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

import com.neodem.componentConnector.model.factory.ConnectableDefinition;

public class ConnectableHelper {
	public static Collection<ConnectableDefinition> loadConnectableDefs(File connectableDefs) {
		Collection<ConnectableDefinition> definitionCollection = new ArrayList<ConnectableDefinition>();
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
