package com.neodem.componentConnector.solver;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.SinglePassConnectionSolver;
import com.neodem.componentConnector.solver.Solver;
import com.neodem.componentConnector.solver.optimizers.ConnectionOptimizer;
import com.neodem.componentConnector.solver.optimizers.ConnectionRotator;

/**
 * @author vfumo
 * 
 */
public class SinglePassWithRotatorTest extends AbstractBaseRelayLocatorTest {

	private Solver solver;

	@Before
	public void setUp() throws Exception {
		ConnectionOptimizer o = new ConnectionRotator();
		solver = new SinglePassConnectionSolver(Arrays.asList(o));
	}

	@After
	public void tearDown() throws Exception {
		solver = null;
	}

	@Test
	public void solverShouldMakeNoChanges() {
		ComponentSet set = new ComponentSet(2, 2);
		
		// relays start off unrotated
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 1);
		set.addComponent(from);
		set.addComponent(to);
		
		set.addConnection(makeConnection(from, Right, to, Left));

		solver.solve(set);
		
		assertThat(set.getComponent("from").isInverted(), is(false));
		assertThat(set.getComponent("to").isInverted(), is(false));
	}

	@Test
	public void solverShouldRotateToRelayToOptimize() {
		ComponentSet set = new ComponentSet(2, 2);
		
		// relays start off unrotated
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 1);
		set.addComponent(from);
		set.addComponent(to);
		
		set.addConnection(makeConnection(from, Right, to, Right));
		
		solver.solve(set);
		
		assertThat(set.getComponent("from").isInverted(), is(false));
		assertThat(set.getComponent("to").isInverted(), is(true));
	}
	
	@Test
	public void solverShouldRotateFromRelayToOptimize() {
		ComponentSet set = new ComponentSet(2, 2);
		
		// relays start off unrotated
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 1);
		set.addComponent(from);
		set.addComponent(to);
		
		set.addConnection(makeConnection(from, Left, to, Left));
		
		solver.solve(set);
		
		assertThat(set.getComponent("from").isInverted(), is(true));
		assertThat(set.getComponent("to").isInverted(), is(false));
	}
	
	@Test
	public void solverShouldRotateBothToOptimize() {
		ComponentSet set = new ComponentSet(2, 2);

		// relays start off unrotated
		Component r1 = relayFactory.make("from", 0, 0);
		Component r2 = relayFactory.make("to", 1, 1);
		set.addComponent(r1);
		set.addComponent(r2);

		set.addConnection(makeConnection(r1, Left, r2, Right));
		
		solver.solve(set);
		
		assertThat(set.getComponent("from").isInverted(), is(true));
		assertThat(set.getComponent("to").isInverted(), is(true));
	}

}
