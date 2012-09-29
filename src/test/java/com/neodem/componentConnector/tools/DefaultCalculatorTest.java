package com.neodem.componentConnector.tools;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.tools.Calculator;

/**
 * @author vfumo
 * 
 */
public class DefaultCalculatorTest extends AbstractBaseRelayLocatorTest {

	Calculator calculator;

	@Before
	public void setUp() throws Exception {
		calculator = new Calculator();
	}

	@After
	public void tearDown() throws Exception {
		calculator = null;
	}

	// absolute distance is distance without considering rotation

	@Test
	public void OnSameRowAndAdjacentShouldHaveZeroAbsoluteDistance() {
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 0);

		Connection c = makeConnection(from, Left, to, Left);

		assertThat(calculator.calculateAbsoluteDistance(c), is(0));
	}

	@Test
	public void OnSameRowAndOneApartShouldHaveOneAbsoluteDistance() {
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 2, 0);

		Connection c1 = makeConnection(from, Left, to, Left);
		Connection c2 = makeConnection(to, Left, from, Left);

		assertThat(calculator.calculateAbsoluteDistance(c1), is(1));
		assertThat(calculator.calculateAbsoluteDistance(c2), is(1));
	}

	@Test
	public void OneRowApartButOnTopOfEachOtherShouldHaveZeorAbsoluteDistance() {
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 0, 1);

		Connection c1 = makeConnection(from, Left, to, Left);

		assertThat(calculator.calculateAbsoluteDistance(c1), is(0));
	}

	@Test
	public void TwoRowsApartButOnTopOfEachOtherShouldHaveOneAbsoluteDistance() {
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 0, 2);

		Connection c1 = makeConnection(from, Left, to, Left);

		assertThat(calculator.calculateAbsoluteDistance(c1), is(1));
	}

	@Test
	public void OneRowApartAndOneColApartShouldHaveTwoAbsoluteDistance() {
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 1);

		Connection c1 = makeConnection(from, Left, to, Left);

		assertThat(calculator.calculateAbsoluteDistance(c1), is(2));
	}

	@Test
	public void TwoRowsApartAndOneColApartShouldHaveThreeAbsoluteDistance() {
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 2);

		Connection c1 = makeConnection(from, Left, to, Left);

		assertThat(calculator.calculateAbsoluteDistance(c1), is(3));
	}

	@Test
	public void sameRowAdjacentShouldHaveVariousValuesDependingOnRotation() {
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 1, 0);

		Connection c = makeConnection(from, Left, to, Left);
		assertThat(calculator.calculateRotationalDistance(c), is(1));

		c = makeConnection(from, Left, to, Right);
		assertThat(calculator.calculateRotationalDistance(c), is(2));

		c = makeConnection(from, Right, to, Right);
		assertThat(calculator.calculateRotationalDistance(c), is(1));

		c = makeConnection(from, Right, to, Left);
		assertThat(calculator.calculateRotationalDistance(c), is(0));
	}

	@Test
	public void sameColumnAdjacentShouldHaveVariousValuesDependingOnRotation() {
		Component from = relayFactory.make("from", 0, 0);
		Component to = relayFactory.make("to", 0, 1);

		Connection c = makeConnection(from, Left, to, Left);
		assertThat(calculator.calculateRotationalDistance(c), is(0));

		c = makeConnection(from, Left, to, Right);
		assertThat(calculator.calculateRotationalDistance(c), is(1));

		c = makeConnection(from, Right, to, Right);
		assertThat(calculator.calculateRotationalDistance(c), is(0));

		c = makeConnection(from, Right, to, Left);
		assertThat(calculator.calculateRotationalDistance(c), is(1));
	}
}
