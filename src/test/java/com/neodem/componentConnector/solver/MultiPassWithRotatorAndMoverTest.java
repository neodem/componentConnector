package com.neodem.componentConnector.solver;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.MultiPassConnectionSolver;
import com.neodem.componentConnector.solver.Solver;
import com.neodem.componentConnector.solver.optimizers.ConnectionMover;
import com.neodem.componentConnector.solver.optimizers.ConnectionOptimizer;
import com.neodem.componentConnector.solver.optimizers.ConnectionRotator;

/**
 * @author vfumo
 * 
 */
public class MultiPassWithRotatorAndMoverTest extends AbstractBaseRelayLocatorTest  {

	private Solver solver;

	@Before
	public void setUp() throws Exception {
		ConnectionOptimizer r = new ConnectionRotator();
		ConnectionOptimizer m = new ConnectionMover();
		solver = new MultiPassConnectionSolver(Arrays.asList(r,m));
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
		Component to = relayFactory.make("to", 1, 0);
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
		Component to = relayFactory.make("to", 1, 0);
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
		Component to = relayFactory.make("to", 1, 0);
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
		Component r2 = relayFactory.make("to", 1, 0);
		set.addComponent(r1);
		set.addComponent(r2);

		set.addConnection(makeConnection(r1, Left, r2, Right));

		solver.solve(set);
		
		assertThat(set.getComponent("from").isInverted(), is(true));
		assertThat(set.getComponent("to").isInverted(), is(true));
	}

	@Test
	public void solverShouldRotateAndMoveMultipleConnectionsToOptimize() {
		ComponentSet set = new ComponentSet(2, 2);

		Component r1 = relayFactory.make("from", 0, 0);
		Component r2 = relayFactory.make("to", 1, 1);
		Component r3 = relayFactory.make("r3", 0, 1);
		Component r4 = relayFactory.make("r4", 1, 0);
		set.addComponent(r1);
		set.addComponent(r2);
		set.addComponent(r3);
		set.addComponent(r4);
		
		set.addConnection(makeConnection(r1, Left, r2, Right));
		set.addConnection(makeConnection(r3, Right, r4, Right));

		assertThat(set.getTotalSize(), is(7));
		solver.solve(set);
		assertThat(set.getTotalSize(), is(0));
		
		assertThat(set.getComponent("from").isInverted(), is(false));
		assertThat(set.getComponent("to").isInverted(), is(true));
		assertThat(set.getComponent("r3").isInverted(), is(false));
		assertThat(set.getComponent("r4").isInverted(), is(false));
		
		assertThat(set.getComponent("from").getLocation(), equalTo(new Location(0,0)));
		assertThat(set.getComponent("to").getLocation(), equalTo(new Location(0,1)));
		assertThat(set.getComponent("r3").getLocation(), equalTo(new Location(1,1)));
		assertThat(set.getComponent("r4").getLocation(), equalTo(new Location(1,0)));
	}

	@Test
	public void solverShouldRotateAndMoveMultipleConnectionsToOptimizeWithAllRotating() {
		ComponentSet set = new ComponentSet(2, 2);

		Component r1 = relayFactory.make("from", 0, 0);
		Component r2 = relayFactory.make("to", 1, 1);
		Component r3 = relayFactory.make("r3", 0, 1);
		Component r4 = relayFactory.make("r4", 1, 0);
		set.addComponent(r1);
		set.addComponent(r2);
		set.addComponent(r3);
		set.addComponent(r4);

		set.addConnection(makeConnection(r1, Left, r2, Right));
		set.addConnection(makeConnection(r3, Left, r4, Right));
		
		solver.solve(set);
		
		assertThat(set.getTotalSize(), is(0));
	}
}
