package com.neodem.componentConnector.graphics;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.io.FileConnector;
import com.neodem.componentConnector.io.PrettyPrintOutputFileConnector;
import com.neodem.componentConnector.main.FullRunRandom;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class TestFullSet {

	private Display d = new CrudeConsoleDisplay();
	private ComponentSet set;

	@Before
	public void setUp() throws Exception {
		ClassLoader classLoader = FullRunRandom.class.getClassLoader();
		
		URL url = classLoader.getResource("All-Connectables.xml");
		File defs = new File(url.getPath());

		url = classLoader.getResource("All.xml");
		File file = new File(url.getPath());

		FileConnector c = new PrettyPrintOutputFileConnector(defs);
		set = c.readIntoComponentSet(file);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		System.out.println(d.asString(set));
	}

}
