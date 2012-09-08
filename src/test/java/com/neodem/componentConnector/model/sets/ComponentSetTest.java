package com.neodem.componentConnector.model.sets;

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
import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.AutoAddComponentSet;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 * 
 */
public class ComponentSetTest extends AbstractBaseRelayLocatorTest {
	
//	@Test
//	public void copyShouldCorrectlyCopyASet() {
//		AutoAddComponentSet set1 = new AutoAddComponentSet(20, 20);
//		AutoAddComponentSet set2 = new AutoAddComponentSet(20, 20);
//
//		Component r1 = relayFactory.make("r1", 0, 0);
//		Component r2 = relayFactory.make("r2", 10, 0);
//		Component r3 = relayFactory.make("r3", 10, 10);
//
//		set1.addConnection(makeConnection(r1, Right, r2, Right));
//		set1.addConnection(makeConnection(r1, Right, r3, Right));
//		
//		Component r4 = relayFactory.make("r1", 1, 1);
//		Component r5 = relayFactory.make("r2", 5, 7);
//		Component r6 = relayFactory.make("r3", 3, 2);
//		
//		set2.addConnection(makeConnection(r4, Right, r5, Right));
//		set2.addConnection(makeConnection(r4, Right, r6, Right));
//		
//		int size2 = set2.getTotalSize();
//		
//		assertThat(set1.getTotalSize(), not(equalTo(size2)));
//		
//		set1.copyFrom(set2);
//		
//		assertThat(set1.getTotalSize(), equalTo(size2));
//	}
	
	@Test
	public void emptySetShouldAddComponentAndHaveItNotChangeSize() {
		ComponentSet set = new ComponentSet(10, 10);
		
		assertThat(set.getTotalSize(), equalTo(0));
		
		Component r1 = relayFactory.make("r1", 0, 0);
		
		set.addComponent(r1);
		assertThat(set.getTotalSize(), equalTo(0));
	}
	
	@Test
	public void setShouldUpdateItsSizeWhenWeAddAConnection() {
		ComponentSet set = new ComponentSet(10, 10);
		assertThat(set.getTotalSize(), equalTo(0));
		
		Component r1 = relayFactory.make("r1", 0, 0);
		Component r2 = relayFactory.make("r2", 10, 0);

		set.addConnection(makeConnection(r1, Right, r2, Right));
		assertThat(set.getTotalSize(), equalTo(10));
	}
	
	@Test
	public void setShouldUpdateItsSizeWhenWeAddMultipleConnections() {
		ComponentSet set = new ComponentSet(10, 10);
		assertThat(set.getTotalSize(), equalTo(0));
		
		Component r1 = relayFactory.make("r1", 0, 0);
		Component r2 = relayFactory.make("r2", 10, 0);
		
		set.addConnection(makeConnection(r1, Right, r2, Right));
		assertThat(set.getTotalSize(), equalTo(10));
		
		Component r3 = relayFactory.make("r3", 10, 10);
		set.addConnection(makeConnection(r1, Right, r3, Right));
		assertThat(set.getTotalSize(), equalTo(31));
	}
	
	@Test
	public void recalculateShouldReturnSameAsTotalSizeIfNoChanges() {
		ComponentSet set = new ComponentSet(10, 10);
		assertThat(set.getTotalSize(), equalTo(0));
		
		Component r1 = relayFactory.make("r1", 0, 0);
		Component r2 = relayFactory.make("r2", 10, 0);
		
		set.addConnection(makeConnection(r1, Right, r2, Right));
		assertThat(set.getTotalSize(), equalTo(10));
		
		set.recalculate();
		assertThat(set.getTotalSize(), equalTo(10));
	}
	
	@Test
	public void recalculateShouldReturnSameAsTotalSizeIfAConnectionAdded() {
		ComponentSet set = new ComponentSet(10, 10);
		assertThat(set.getTotalSize(), equalTo(0));
		
		Component r1 = relayFactory.make("r1", 0, 0);
		Component r2 = relayFactory.make("r2", 10, 0);
		
		set.addConnection(makeConnection(r1, Right, r2, Right));
		assertThat(set.getTotalSize(), equalTo(10));
		
		Component r3 = relayFactory.make("r3", 10, 10);
		set.addConnection(makeConnection(r1, Right, r3, Right));
		assertThat(set.getTotalSize(), equalTo(31));
		
		set.recalculate();
		assertThat(set.getTotalSize(), equalTo(31));
	}

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
}
