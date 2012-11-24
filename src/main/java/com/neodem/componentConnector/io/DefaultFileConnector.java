package com.neodem.componentConnector.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Serializer;

import com.neodem.componentConnector.model.factory.ComponentFactory;
import com.neodem.componentConnector.model.factory.ConnectableDefinition;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.tools.ConnectableHelper;

public class DefaultFileConnector implements FileConnector {
	private ComponentFactory factory;

	public DefaultFileConnector(File factoryDef) {
		Collection<ConnectableDefinition> defs = ConnectableHelper.loadConnectableDefs(factoryDef);
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
}
