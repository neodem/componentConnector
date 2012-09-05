package com.neodem.componentConnector.tools;

import static com.neodem.componentConnector.model.Side.Left;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.tools.ConnectionTools;

/**
 * @author vfumo
 * 
 */
public class ConnectionToolsTest extends AbstractBaseRelayLocatorTest {

	/**
	 * Test method for
	 * {@link com.neodem.relayLocator.tools.DefaultConnectionTools#toRightOfFrom(com.neodem.componentConnector.model.Connection)}
	 * .
	 */
	@Test
	public void toRightOfFromShouldWork() {
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 0);

		Connection c = makeConnection(from, Left, to, Left);
		
		assertThat(ConnectionTools.toRightOfFrom(c), is(true));
	}

	/**
	 * Test method for
	 * {@link com.neodem.relayLocator.tools.DefaultConnectionTools#toLeftOfFrom(com.neodem.componentConnector.model.Connection)}
	 * .
	 */
	@Test
	public void testToLeftOfFromShouldWork() {
		Component from = relayFactory.make("from", 1, 0);
		Component to = relayFactory.make("to", 0, 0);

		Connection c = makeConnection(from, Left, to, Left);
		assertThat(ConnectionTools.toLeftOfFrom(c), is(true));
	}

}
