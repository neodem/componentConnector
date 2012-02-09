package com.neodem.componentConnector.solver;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.SinglePassConnectionSolver;
import com.neodem.componentConnector.solver.Solver;
import com.neodem.componentConnector.solver.optimizers.ConnectionMover;
import com.neodem.componentConnector.solver.optimizers.ConnectionOptimizer;

/**
 * @author vfumo
 * 
 */
public class SinglePassWithMoverTest extends AbstractBaseRelayLocatorTest {

	private Solver solver;

	@Before
	public void setUp() throws Exception {
		ConnectionOptimizer o = new ConnectionMover();
		solver = new SinglePassConnectionSolver(Arrays.asList(o));
	}

	@After
	public void tearDown() throws Exception {
		solver = null;
	}

	@Test
	public void solverShouldMakeNoChangesSinceRelaysAreAdjacentVertially() {
		ComponentSet set = new ComponentSet(2, 2);

		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 0, 1);
		set.addComponent(from);
		set.addComponent(to);

		set.addConnection(makeConnection(from, Right, to, Left));

		int hashBefore = set.hashCode();

		solver.solve(set);

		int hashAfter = set.hashCode();

		assertThat(hashAfter, equalTo(hashBefore));
	}

	@Test
	public void solverShouldMakeNoChangesSinceRelaysAreAdjacentHorizontally() {
		ComponentSet set = new ComponentSet(2, 2);

		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 0);
		set.addComponent(from);
		set.addComponent(to);

		set.addConnection(makeConnection(from, Right, to, Left));

		int hashBefore = set.hashCode();

		solver.solve(set);

		int hashAfter = set.hashCode();

		assertThat(hashAfter, equalTo(hashBefore));
	}

	@Test
	public void solverShouldBringToRelayAdjacentToOptimize() {
		ComponentSet set = new ComponentSet(12, 12);

		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 10, 0);
		set.addComponent(from);
		set.addComponent(to);

		set.addConnection(makeConnection(from, Right, to, Left));

		solver.solve(set);

		assertThat(from.getLocation(), equalTo(new Location(0, 0)));
		assertThat(to.getLocation(), equalTo(new Location(1, 0)));
	}

	@Test
	public void solverShouldBringToRelayAdjacentToOptimizeEvenIfWayApart() {
		ComponentSet set = new ComponentSet(12, 12);

		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 11, 11);
		set.addComponent(from);
		set.addComponent(to);

		set.addConnection(makeConnection(from, Right, to, Left));

		solver.solve(set);

		assertThat(from.getLocation(), equalTo(new Location(0, 0)));
		assertThat(to.getLocation(), equalTo(new Location(0, 1)));
	}
}
