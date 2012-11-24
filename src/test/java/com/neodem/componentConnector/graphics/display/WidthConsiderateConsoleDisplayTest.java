package com.neodem.componentConnector.graphics.display;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.graphics.display.WidthConsiderateConsoleDisplay;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class WidthConsiderateConsoleDisplayTest extends AbstractBaseRelayLocatorTest {

	private WidthConsiderateConsoleDisplay c;
	
	@Before
	public void before() {
		c = new WidthConsiderateConsoleDisplay(2);
	}
	
	@After
	public void after() {
		c = null;
	}
	
	@Test
	public void asStringShouldWorkAsIntended() {
		
		// 3 cols, one row
		ComponentSet set = new ComponentSet(2, 3);
		addToSet(set, "1", 0,0);
		addToSet(set, "2", 0,1);
		addToSet(set, "3", 0,2);
		addToSet(set, "4", 1,0);
		addToSet(set, "5", 1,1);
		addToSet(set, "6", 1,2);
		
		System.out.println(c.asString(set));
	}
}
