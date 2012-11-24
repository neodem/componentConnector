package com.neodem.componentConnector.io;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.main.FullRunRandom;
import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class DefaultFileConnectorTest extends AbstractBaseRelayLocatorTest {
	
	private DefaultFileConnector d;
	
	@Before
	public void before() {
		d = (DefaultFileConnector) initFileConnector();
	}
	
	@After
	public void after() {
		d = null;
	}

	@Test
	public void loadAndUnloadShouldNotChangeTheSet() {
		ComponentSet set = new ComponentSet(5,5);
		
		BaseComponent from = addToSet(set, "from", 0,0);
		BaseComponent to = addToSet(set, "to", 4,0);
		
		File testFile = new File("test.xml");
		
		d.writeComponentSetToFile(set, testFile);
		
		ComponentSet set2 = d.readIntoComponentSet(testFile);
		
		set2.getAllComponents();
		//TODO make sure both are there and in correct positions
		
	}
	
	private FileConnector initFileConnector() {
		ClassLoader classLoader = FullRunRandom.class.getClassLoader();

		URL url = classLoader.getResource("All-Connectables.xml");
		File defs = new File(url.getPath());
		
		return new DefaultFileConnector(defs);
	}

}
