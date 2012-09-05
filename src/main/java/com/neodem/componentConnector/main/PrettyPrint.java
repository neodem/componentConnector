package com.neodem.componentConnector.main;

import java.io.File;
import java.net.URL;

import com.neodem.componentConnector.io.FileConnector;
import com.neodem.componentConnector.io.PrettyPrintOutputFileConnector;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 * 
 */
public class PrettyPrint {
	
	public PrettyPrint() {
		ClassLoader classLoader = FullRunRandom.class.getClassLoader();
		
		URL url = classLoader.getResource("Full-Located-components.xml");
		File componentsFile = new File(url.getPath());

		url = classLoader.getResource("Full-connectables.xml");
		File connectablesFile = new File(url.getPath());

		url = classLoader.getResource("Full-connections.xml");
		File connectionsFile = new File(url.getPath());

		FileConnector c = new PrettyPrintOutputFileConnector();
		ComponentSet set = c.read(componentsFile, connectablesFile, connectionsFile);

		File out = new File("pretty.out");
		c.writeToFile(out, set);
	}

	public static void main(String[] args) {
		new PrettyPrint();
	}
}
