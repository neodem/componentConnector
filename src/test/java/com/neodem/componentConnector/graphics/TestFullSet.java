package com.neodem.componentConnector.graphics;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.io.FileConnector;
import com.neodem.componentConnector.io.PrettyPrintOutputFileConnector;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class TestFullSet {

	private Display d = new CrudeConsoleDisplay();
	private ComponentSet set;

	@Before
	public void setUp() throws Exception {
		ClassLoader classLoader = TestFullSet.class.getClassLoader();
		
		URL url = classLoader.getResource("Full-components.xml");
		File componentsFile = new File(url.getPath());

		url = classLoader.getResource("Full-connectables.xml");
		File connectablesFile = new File(url.getPath());

		url = classLoader.getResource("Full-connections.xml");
		File connectionsFile = new File(url.getPath());

		FileConnector c = new PrettyPrintOutputFileConnector();
		set = c.read(componentsFile, connectablesFile, connectionsFile);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		d.displaySet(set);
	}

}
