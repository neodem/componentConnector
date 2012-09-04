package com.neodem.componentConnector.model;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.component.Component;

/**
 * 
 * @author vfumo
 * 
 */
public class ConnectionTest extends AbstractBaseRelayLocatorTest {

	@Test
	public void sameRowAdjacentShouldHaveVariousValuesDependingOnRotation() {
		Component r1 = relayFactory.make("1", 0, 0);
		Component r2 = relayFactory.make("2", 1, 0);

		
		Connection c = makeConnection(r1, Left, r2, Left);
		assertThat(calc.calculateDistance(c), is(1));

		c = makeConnection(r1, Left, r2, Right);
		assertThat(calc.calculateDistance(c), is(2));

		c = makeConnection(r1, Right, r2, Right);
		assertThat(calc.calculateDistance(c), is(1));

		c = makeConnection(r1, Right, r2, Left);
		assertThat(calc.calculateDistance(c), is(0));
	}

	@Test
	public void sameColumnAdjacentShouldHaveVariousValuesDependingOnRotation() {
		Component r1 = relayFactory.make("1", 0, 0);
		Component r2 = relayFactory.make("2", 0, 1);
		
		Connection c = makeConnection(r1, Left, r2, Left);
		assertThat(calc.calculateDistance(c), is(0));

		c = makeConnection(r1, Left, r2, Right);
		assertThat(calc.calculateDistance(c), is(1));

		c = makeConnection(r1, Right, r2, Right);
		assertThat(calc.calculateDistance(c), is(0));

		c = makeConnection(r1, Right, r2, Left);
		assertThat(calc.calculateDistance(c), is(1));
	}

	@Test
	public void adjacentHAndAdjacentVShouldHaveTheSameValues() {
		Component r1 = relayFactory.make("1", 0, 0);
		Component r2 = relayFactory.make("2", 1, 0);
		Component r3 = relayFactory.make("3", 0, 1);

		Connection c1 = makeConnection(r1, Right, r2, Left);
		Connection c2 = makeConnection(r1, Right, r3, Right);
		
		int c1D = calc.calculateDistance(c1);
		int c2D = calc.calculateDistance(c2);

		assertThat(c1D, equalTo(c2D));
	}
}
