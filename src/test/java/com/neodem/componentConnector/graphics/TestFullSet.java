package com.neodem.componentConnector.graphics;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.graphics.CrudeConsoleDisplay;
import com.neodem.componentConnector.graphics.Display;
import com.neodem.componentConnector.io.DefaultFileConnector;
import com.neodem.componentConnector.io.FileConnector;
import com.neodem.componentConnector.main.FullRun;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class TestFullSet {

	private Display d = new CrudeConsoleDisplay();
	private ComponentSet set;

	@Before
	public void setUp() throws Exception {
		URL url = FullRun.class.getClassLoader().getResource("4inMulti.xml");
		File testSet = new File(url.getPath());

		url = FullRun.class.getClassLoader().getResource("relay.xml");
		File defs = new File(url.getPath());

		FileConnector c = new DefaultFileConnector();
		set = c.read(defs, testSet);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		d.displaySet(set);
	}

}
