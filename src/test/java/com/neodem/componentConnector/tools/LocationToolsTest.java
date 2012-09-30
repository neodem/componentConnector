package com.neodem.componentConnector.tools;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.neodem.componentConnector.model.Location;

/**
 * @author vfumo
 * 
 */
public class LocationToolsTest {

	/**
	 * Test method for
	 * {@link com.neodem.relayLocator.tools.DefaultConnectionTools#toRightOfFrom(com.neodem.componentConnector.model.Connection)}
	 * .
	 */
	@Test
	public void toRightOfhouldWork() {
		Location fixed = new Location(0,0);
		Location test  = new Location(0,1);
		
		assertThat(LocationTools.toRightOf(fixed, test), is(true));
	}

	/**
	 * Test method for
	 * {@link com.neodem.relayLocator.tools.DefaultConnectionTools#toLeftOfFrom(com.neodem.componentConnector.model.Connection)}
	 * .
	 */
	@Test
	public void testToLeftOfShouldWork() {
		Location fixed = new Location(0,1);
		Location test  = new Location(0,0);
		
		assertThat(LocationTools.toLeftOf(fixed, test), is(true));
	}

}
