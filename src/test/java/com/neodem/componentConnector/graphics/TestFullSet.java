package com.neodem.componentConnector.graphics;

import java.io.File;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.graphics.display.Display;
import com.neodem.componentConnector.graphics.display.GraphicalConsoleDisplay;
import com.neodem.componentConnector.io.DefaultFileConnector;
import com.neodem.componentConnector.io.FileConnector;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class TestFullSet {

	private Display d = new GraphicalConsoleDisplay();
	private ComponentSet set;

	@Before
	public void setUp() throws Exception {
		
		ClassLoader classLoader = TestFullSet.class.getClassLoader();

		URL url = classLoader.getResource("All-Connectables.xml");
		File defs = new File(url.getPath());

		FileConnector fc = new DefaultFileConnector(defs);
		
		url = classLoader.getResource("All.xml");
		File componentSetFile = new File(url.getPath());
		
		set = fc.readIntoComponentSet(componentSetFile);
	}

	@Test
	public void test() {
		System.out.println(d.asString(set));
	}
}
