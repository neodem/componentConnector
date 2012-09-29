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
		
		URL url = classLoader.getResource("All-Connectables.xml");
		File defs = new File(url.getPath());

		File connectablesFile = new File("best.xml");

		FileConnector c = new PrettyPrintOutputFileConnector(defs);
		ComponentSet set = c.readIntoComponentSet(connectablesFile);

		File out = new File("pretty.out");
		c.writeComponentSetToFile(set, out);
	}

	public static void main(String[] args) {
		new PrettyPrint();
	}
}
