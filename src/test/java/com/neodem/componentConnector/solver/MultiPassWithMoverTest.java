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
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.MultiPassConnectionSolver;
import com.neodem.componentConnector.solver.Solver;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionMover;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;
import com.neodem.componentConnector.tools.LocationTools;

/**
 * @author vfumo
 * 
 */
public class MultiPassWithMoverTest extends AbstractBaseRelayLocatorTest  {

	private Solver solver;

	@Before
	public void setUp() throws Exception {
		ConnectionOptimizer m = new ConnectionMover();
		solver = new MultiPassConnectionSolver(Arrays.asList(m));
	}

	@After
	public void tearDown() throws Exception {
		solver = null;
	}

	@Test
	public void solverShouldMakeNoChanges() {
		ComponentSet set = new ComponentSet(2, 2);

		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 0);
		set.addComponent(from);
		set.addComponent(to);

		set.addConnection(makeConnection(from, Right, to, Left));
		
		solver.solve(set);
		
		assertThat(from.getLocation(), equalTo(new Location(0,0)));
		assertThat(to.getLocation(), equalTo(new Location(0,1)));
	}
	
	@Test
	public void solverShouldGetBothPairsAdjacent() {
		ComponentSet set = new ComponentSet(2, 2);
		
		Component r1 = relayFactory.make("from", 0, 0);
		Component r2 = relayFactory.make("to", 1, 1);
		Component r3 = relayFactory.make("r3", 0, 1);
		Component r4 = relayFactory.make("r4", 1, 0);
		set.addComponent(r1);
		set.addComponent(r2);
		set.addComponent(r3);
		set.addComponent(r4);
		
		Connection c1 = set.addConnection(makeConnection(r1, Left, r2, Right));
		Connection c2 = set.addConnection(makeConnection(r3, Right, r4, Right));
		
		solver.solve(set);
		
		assertThat(LocationTools.adjacent(c1), is(true));
		assertThat(LocationTools.adjacent(c2), is(true));
	}
}
