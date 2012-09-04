package com.neodem.componentConnector.solver.optimizers;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.optimizers.ConnectionMover;
import com.neodem.componentConnector.solver.optimizers.ConnectionOptimizer;

/**
 * @author vfumo
 * 
 */
public class ConnectionMoverTest extends AbstractBaseRelayLocatorTest {

	ConnectionOptimizer o;

	@Before
	public void setUp() throws Exception {
		o = new ConnectionMover();
	}

	@After
	public void tearDown() throws Exception {
		o = null;
	}

	@Test
	public void whenTwoRelaysAreOnTheSameRowAndNextToEachOtherNoChangeShouldHappen() {
		ComponentSet set = new ComponentSet(2, 2);

		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 0);
		set.addComponent(from);
		set.addComponent(to);

		Connection c1 = set.addConnection(makeConnection(from, Right, to, Left));

		int hashBefore = set.hashCode();

		o.optimize(c1, set);

		int hashAfter = set.hashCode();

		assertThat(hashAfter, equalTo(hashBefore));
	}

	@Test
	public void whenTwoRelaysAreOnTheSameColumnAndAboveToEachOtherNoChangeShouldHappen() {
		ComponentSet set = new ComponentSet(2, 2);

		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 0, 1);
		set.addComponent(from);
		set.addComponent(to);

		Connection c1 = set.addConnection(makeConnection(from, Right, to, Left));

		int hashBefore = set.hashCode();

		o.optimize(c1, set);

		int hashAfter = set.hashCode();

		assertThat(hashAfter, equalTo(hashBefore));
	}

	@Test
	public void veritcalShiftShouldWorkUpWhenFromAboveTo() {
		ComponentSet set = new ComponentSet(2, 10);

		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 0, 9);
		set.addComponent(from);
		set.addComponent(to);

		Connection c1 = set.addConnection(makeConnection(from, Right, to, Left));

		o.optimize(c1, set);

		assertThat(to.getyLoc(), equalTo(8));
	}

	@Test
	public void veritcalShiftShouldWorkDownWhenToAboveFrom() {
		ComponentSet set = new ComponentSet(2, 10);

		Component from = relayFactory.make("from", 0, 9);
		Component to = relayFactory.make("to", 0, 0);
		set.addComponent(from);
		set.addComponent(to);

		Connection c1 = set.addConnection(makeConnection(from, Right, to, Left));

		o.optimize(c1, set);

		assertThat(to.getyLoc(), equalTo(1));
	}

	@Test
	public void whenBothAreOnSameRowAndFromIsFarLeftAndToIsFarRightTheToOneSwapsOneCloserToToTheLeft() {
		ComponentSet set = new ComponentSet(3, 1);

		Component from = relayFactory.make("from", 0, 0);
		Component middle = relayFactory.make("middle", 1, 0);
		Component to = relayFactory.make("to", 2, 0);
		set.addComponent(from);
		set.addComponent(middle);
		set.addComponent(to);

		Connection c1 = set.addConnection(makeConnection(from, Right, to, Left));

		o.optimize(c1, set);

		assertThat(set.getComponent("from").getxLoc(), equalTo(0));
		assertThat(set.getComponent("middle").getxLoc(), equalTo(2));
		assertThat(set.getComponent("to").getxLoc(), equalTo(1));
	}

	@Test
	public void whenBothAreOnSameColAndFromIsAtTheTopAndToIsAtTheBottomTheToOneSwapsOneCloserUp() {
		ComponentSet set = new ComponentSet(3, 3);

		Component from = relayFactory.make("from", 0, 0);
		Component middle = relayFactory.make("middle", 0, 1);
		Component to = relayFactory.make("to", 0, 2);
		set.addComponent(from);
		set.addComponent(middle);
		set.addComponent(to);

		Connection c1 = set.addConnection(makeConnection(from, Right, to, Left));

		o.optimize(c1, set);

		assertThat(set.getComponent("from").getyLoc(), equalTo(0));
		assertThat(set.getComponent("middle").getyLoc(), equalTo(2));
		assertThat(set.getComponent("to").getyLoc(), equalTo(1));
	}

	@Test
	public void whenBothAreOnSameRowAndFromIsFarRightAndToIsFarLeftTheToOneSwapsOneCloserToTheRight() {
		ComponentSet set = new ComponentSet(3, 1);

		Component from = relayFactory.make("from", 2, 0);
		Component middle = relayFactory.make("middle", 1, 0);
		Component to = relayFactory.make("to", 0, 0);
		set.addComponent(from);
		set.addComponent(middle);
		set.addComponent(to);

		Connection c1 = set.addConnection(makeConnection(from, Right, to, Left));

		o.optimize(c1, set);

		assertThat(set.getComponent("from").getxLoc(), equalTo(2));
		assertThat(set.getComponent("middle").getxLoc(), equalTo(0));
		assertThat(set.getComponent("to").getxLoc(), equalTo(1));
	}

	@Test
	public void whenMovingOneDoesntMakeADifferenceNoChangesShouldOccur() {
		ComponentSet set = new ComponentSet(3, 1);

		Component from = relayFactory.make("from", 2, 0);
		Component middle = relayFactory.make("middle", 1, 0);
		Component to = relayFactory.make("to", 0, 0);
		set.addComponent(from);
		set.addComponent(middle);
		set.addComponent(to);

		Connection c1 = set.addConnection(makeConnection(from, Right, to, Left));
		set.addConnection(makeConnection(from, Right, middle, Left));

		int hashBefore = set.hashCode();
		o.optimize(c1, set);
		int hashAfter = set.hashCode();

		assertThat(hashAfter, equalTo(hashBefore));
	}

}
