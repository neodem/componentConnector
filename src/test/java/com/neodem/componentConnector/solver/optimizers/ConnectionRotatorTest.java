package com.neodem.componentConnector.solver.optimizers;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.optimizers.ConnectionOptimizer;
import com.neodem.componentConnector.solver.optimizers.ConnectionRotator;

/**
 * @author vfumo
 * 
 */
public class ConnectionRotatorTest extends AbstractBaseRelayLocatorTest {

	ConnectionOptimizer o;

	@Before
	public void setUp() throws Exception {
		o = new ConnectionRotator();
	}

	@After
	public void tearDown() throws Exception {
		o = null;
	}

	@Test
	public void TwoAdjacentOnSameRowShouldRotateTheTo() {
		ComponentSet set = new ComponentSet(2, 2);

		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 0);
		set.addComponent(from);
		set.addComponent(to);

		Connection c1 = set.addConnection(makeConnection(from, Left, to, Right));
		
		o.optimize(c1, set);

		assertThat(to.isInverted(), is(true));
		assertThat(from.isInverted(), is(false));
	}

	@Test
	public void ThreeRelaysOnSameRowButAChangeToTheMiddleWillMakeThingsWorseSoTheOptShouldRotateFrom() {
		ComponentSet set = new ComponentSet(3, 3);

		Component from = relayFactory.make("from", 0, 0);
		Component middle = relayFactory.make("to", 1, 0);
		Component to = relayFactory.make("r3", 2, 0);
		set.addComponent(from);
		set.addComponent(to);

		Connection c1 = set.addConnection(makeConnection(from, Left, middle, Left));
		set.addConnection(makeConnection(middle, Right, to, Left));

		// note that a rotate to the middle will make c2 and c1 grow
		// the correct opt. here is to move one of the ends. Since we're going
		// to
		// opt. c1, that would mean the from should rotate

		o.optimize(c1, set);

		assertThat(from.isInverted(), is(true));
		assertThat(middle.isInverted(), is(false));
		assertThat(to.isInverted(), is(false));
	}

}
