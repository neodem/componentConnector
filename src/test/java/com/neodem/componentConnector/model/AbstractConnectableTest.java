package com.neodem.componentConnector.model;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AbstractConnectableTest {

	private class AC extends AbstractConnectable {
		public AC(String name, int pinCount) {
			super(name, pinCount);
		}

		@Override
		public boolean isValid() {
			return false;
		}
	}

	private AbstractConnectable ac;

	@Before
	public void before() {
		ac = new AC("testC", 12);
	}

	@After
	public void after() {
		ac = null;
	}

	@Test
	public void determineSideIndexShouldWorkOnAllPins() {
		assertThat(ac.determineSideIndex(false, new Pin(1, "")), equalTo(5));
		assertThat(ac.determineSideIndex(false, new Pin(2, "")), equalTo(4));
		assertThat(ac.determineSideIndex(false, new Pin(3, "")), equalTo(3));
		assertThat(ac.determineSideIndex(false, new Pin(4, "")), equalTo(2));
		assertThat(ac.determineSideIndex(false, new Pin(5, "")), equalTo(1));
		assertThat(ac.determineSideIndex(false, new Pin(6, "")), equalTo(0));
		assertThat(ac.determineSideIndex(false, new Pin(7, "")), equalTo(0));
		assertThat(ac.determineSideIndex(false, new Pin(8, "")), equalTo(1));
		assertThat(ac.determineSideIndex(false, new Pin(9, "")), equalTo(2));
		assertThat(ac.determineSideIndex(false, new Pin(10, "")), equalTo(3));
		assertThat(ac.determineSideIndex(false, new Pin(11, "")), equalTo(4));
		assertThat(ac.determineSideIndex(false, new Pin(12, "")), equalTo(5));
	}

	@Test
	public void getSideForPinShouldWorkOnAllPins() {
		assertThat(ac.getSideForPin(false, new Pin(1, "")), equalTo(Right));
		assertThat(ac.getSideForPin(false, new Pin(2, "")), equalTo(Right));
		assertThat(ac.getSideForPin(false, new Pin(3, "")), equalTo(Right));
		assertThat(ac.getSideForPin(false, new Pin(4, "")), equalTo(Right));
		assertThat(ac.getSideForPin(false, new Pin(5, "")), equalTo(Right));
		assertThat(ac.getSideForPin(false, new Pin(6, "")), equalTo(Right));
		assertThat(ac.getSideForPin(false, new Pin(7, "")), equalTo(Left));
		assertThat(ac.getSideForPin(false, new Pin(8, "")), equalTo(Left));
		assertThat(ac.getSideForPin(false, new Pin(9, "")), equalTo(Left));
		assertThat(ac.getSideForPin(false, new Pin(10, "")), equalTo(Left));
		assertThat(ac.getSideForPin(false, new Pin(11, "")), equalTo(Left));
		assertThat(ac.getSideForPin(false, new Pin(12, "")), equalTo(Left));
	}

	@Test
	public void determineSideIndexShouldWorkOnAllPinsInverted() {
		assertThat(ac.determineSideIndex(true, new Pin(1, "")), equalTo(0));
		assertThat(ac.determineSideIndex(true, new Pin(2, "")), equalTo(1));
		assertThat(ac.determineSideIndex(true, new Pin(3, "")), equalTo(2));
		assertThat(ac.determineSideIndex(true, new Pin(4, "")), equalTo(3));
		assertThat(ac.determineSideIndex(true, new Pin(5, "")), equalTo(4));
		assertThat(ac.determineSideIndex(true, new Pin(6, "")), equalTo(5));
		assertThat(ac.determineSideIndex(true, new Pin(7, "")), equalTo(5));
		assertThat(ac.determineSideIndex(true, new Pin(8, "")), equalTo(4));
		assertThat(ac.determineSideIndex(true, new Pin(9, "")), equalTo(3));
		assertThat(ac.determineSideIndex(true, new Pin(10, "")), equalTo(2));
		assertThat(ac.determineSideIndex(true, new Pin(11, "")), equalTo(1));
		assertThat(ac.determineSideIndex(true, new Pin(12, "")), equalTo(0));
	}

	@Test
	public void getSideForPinShouldWorkOnAllPinsInverted() {
		assertThat(ac.getSideForPin(true, new Pin(1, "")), equalTo(Left));
		assertThat(ac.getSideForPin(true, new Pin(2, "")), equalTo(Left));
		assertThat(ac.getSideForPin(true, new Pin(3, "")), equalTo(Left));
		assertThat(ac.getSideForPin(true, new Pin(4, "")), equalTo(Left));
		assertThat(ac.getSideForPin(true, new Pin(5, "")), equalTo(Left));
		assertThat(ac.getSideForPin(true, new Pin(6, "")), equalTo(Left));
		assertThat(ac.getSideForPin(true, new Pin(7, "")), equalTo(Right));
		assertThat(ac.getSideForPin(true, new Pin(8, "")), equalTo(Right));
		assertThat(ac.getSideForPin(true, new Pin(9, "")), equalTo(Right));
		assertThat(ac.getSideForPin(true, new Pin(10, "")), equalTo(Right));
		assertThat(ac.getSideForPin(true, new Pin(11, "")), equalTo(Right));
		assertThat(ac.getSideForPin(true, new Pin(12, "")), equalTo(Right));
	}
}
