package com.neodem.componentConnector.model.component;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.model.sets.AutoAddComponentSet;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 * 
 */
public class ComponentSetTest extends AbstractBaseRelayLocatorTest {

	@Test
	public void testGetAllConnections() {
		AutoAddComponentSet set = new AutoAddComponentSet(20, 20);

		Component r1 = relayFactory.make("r1", 0, 0);
		Component r2 = relayFactory.make("r2", 10, 0);
		Component r3 = relayFactory.make("r3", 10, 10);

		Connection c1 = set.addConnection(makeConnection(r1, Right, r2, Right));
		Connection c2 = set.addConnection(makeConnection(r1, Right, r3, Right));

		List<Connection> cons = ComponentSet.getAllConnectionsSortedByLargest(set);

		assertThat(cons.get(0), equalTo(c2));
		assertThat(cons.get(1), equalTo(c1));
	}

	@Test
	public void aRelaySetWithTheSameRelayPositionsButWithOneOrMoreDifferencesInRelayStateShouldHaveADifferentHashCode() {
		ComponentSet set = new ComponentSet(2, 2);

		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 0);
		set.addComponent(from);
		set.addComponent(to);

		set.addConnection(makeConnection(from, Right, to, Left));

		int hashBefore = set.hashCode();

		set.getComponent("from").invert();

		int hashAfter = set.hashCode();

		assertThat(hashAfter, not(equalTo(hashBefore)));
	}

	@Test
	public void aRealyShouldNotBeInsertedIntoTheSameSpot() {
		ComponentSet set = new ComponentSet(2, 2);

		Component r1 = relayFactory.make("r1", 0, 0);
		set.addComponent(r1);

		Component r2 = relayFactory.make("r2", 0, 0);
		try {
			set.addComponent(r2);
			fail("should not insert in same spot");
		} catch (IllegalArgumentException e) {
			// noop
		}
	}

	@Test
	public void shiftOneLeftShouldShiftIntoEmptySpot() {
		ComponentSet set = new ComponentSet(3, 3);

		Component r1 = relayFactory.make("r1", 0, 0);
		set.addComponent(r1);
		Component r2 = relayFactory.make("r2", 2, 0);
		set.addComponent(r2);

		set.shiftOneLeft(r2);

		assertThat(r2.getxLoc(), equalTo(1));
	}

	@Test
	public void shiftOneLeftShouldSwap() {
		ComponentSet set = new ComponentSet(3, 3);

		Component r1 = relayFactory.make("1", 0, 0);
		set.addComponent(r1);
		Component r2 = relayFactory.make("2", 1, 0);
		set.addComponent(r2);
		Component r3 = relayFactory.make("3", 2, 0);
		set.addComponent(r3);

		set.shiftOneLeft(r3);

		assertThat(r2.getxLoc(), equalTo(2));
		assertThat(r3.getxLoc(), equalTo(1));
	}

	@Test
	public void shiftOneRightShouldShiftIntoEmptySpot() {
		ComponentSet set = new ComponentSet(3, 3);

		Component r1 = relayFactory.make("1", 0, 0);
		set.addComponent(r1);
		Component r2 = relayFactory.make("2", 2, 0);
		set.addComponent(r2);

		set.shiftOneRight(r1);

		assertThat(r1.getxLoc(), equalTo(1));
	}

	@Test
	public void shiftOneRightShouldSwap() {
		ComponentSet set = new ComponentSet(3, 3);

		Component r1 = relayFactory.make("1", 0, 0);
		set.addComponent(r1);
		Component r2 = relayFactory.make("2", 1, 0);
		set.addComponent(r2);
		Component r3 = relayFactory.make("3", 2, 0);
		set.addComponent(r3);

		set.shiftOneRight(r1);

		assertThat(r1.getxLoc(), equalTo(1));
		assertThat(r2.getxLoc(), equalTo(0));
	}

	@Test
	public void getRowShouldWorkCorrectly() {
		ComponentSet set = new ComponentSet(3, 3);
		Component r1 = relayFactory.make("1", 1, 2);
		set.addComponent(r1);
		Component r2 = relayFactory.make("2", 2, 1);
		set.addComponent(r2);
		Component r3 = relayFactory.make("3", 1, 1);
		set.addComponent(r3);
		Component r4 = relayFactory.make("4", 1, 0);
		set.addComponent(r4);

		List<Component> result = set.getRow(1);
		assertThat(result.size(), is(2));
		assertThat((Component) result.get(0), equalTo(r3));
		assertThat((Component) result.get(1), equalTo(r2));
	}

	@Test
	public void getAllRowsShouldWorkCorrectly() {
		ComponentSet set = new ComponentSet(3, 3);
		Component r1 = relayFactory.make("1", 1, 2);
		set.addComponent(r1);
		Component r2 = relayFactory.make("2", 2, 1);
		set.addComponent(r2);
		Component r3 = relayFactory.make("3", 1, 1);
		set.addComponent(r3);
		Component r4 = relayFactory.make("4", 1, 0);
		set.addComponent(r4);

		List<List<Component>> result = set.getAllRows();
		assertThat(result.size(), is(3));

		List<Component> row0 = result.get(0);
		List<Component> row1 = result.get(1);
		List<Component> row2 = result.get(2);

		assertThat(row0.size(), is(1));
		assertThat(row1.size(), is(2));
		assertThat(row2.size(), is(1));

		assertThat((Component) row0.get(0), equalTo(r4));
		assertThat((Component) row1.get(0), equalTo(r3));
		assertThat((Component) row1.get(1), equalTo(r2));
		assertThat((Component) row2.get(0), equalTo(r1));
	}

}
