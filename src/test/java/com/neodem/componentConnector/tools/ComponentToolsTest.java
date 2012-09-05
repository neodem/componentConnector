package com.neodem.componentConnector.tools;


import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Pin;

public class ComponentToolsTest extends AbstractBaseRelayLocatorTest {

	@Test
	public void determineSideIndexShouldWorkOnAllPins() {
		
		// we will use a relay here
		Component r = relayFactory.make("abcd", 0, 0);
		
		assertThat(ComponentTools.determineSideIndex(r, new Pin(1, "")), equalTo(5));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(2, "")), equalTo(4));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(3, "")), equalTo(3));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(4, "")), equalTo(2));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(5, "")), equalTo(1));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(6, "")), equalTo(0));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(7, "")), equalTo(0));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(8, "")), equalTo(1));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(9, "")), equalTo(2));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(10, "")), equalTo(3));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(11, "")), equalTo(4));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(12, "")), equalTo(5));
	}
	
	@Test
	public void getSideForPinShouldWorkOnAllPins() {
		
		// we will use a relay here
		Component r = relayFactory.make("abcd", 0, 0);
		
		assertThat(ComponentTools.getSideForPin(r, new Pin(1, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(2, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(3, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(4, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(5, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(6, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(7, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(8, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(9, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(10, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(11, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(12, "")), equalTo(Left));
	}
	
	@Test
	public void determineSideIndexShouldWorkOnAllPinsInverted() {
		
		// we will use a relay here
		Component r = relayFactory.make("abcd", 0, 0);
		r.setInverted(true);
		
		assertThat(ComponentTools.determineSideIndex(r, new Pin(1, "")), equalTo(0));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(2, "")), equalTo(1));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(3, "")), equalTo(2));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(4, "")), equalTo(3));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(5, "")), equalTo(4));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(6, "")), equalTo(5));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(7, "")), equalTo(5));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(8, "")), equalTo(4));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(9, "")), equalTo(3));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(10, "")), equalTo(2));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(11, "")), equalTo(1));
		assertThat(ComponentTools.determineSideIndex(r, new Pin(12, "")), equalTo(0));
	}
	
	@Test
	public void getSideForPinShouldWorkOnAllPinsInverted() {
		
		// we will use a relay here
		Component r = relayFactory.make("abcd", 0, 0);
		r.setInverted(true);
		
		assertThat(ComponentTools.getSideForPin(r, new Pin(1, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(2, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(3, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(4, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(5, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(6, "")), equalTo(Left));
		assertThat(ComponentTools.getSideForPin(r, new Pin(7, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(8, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(9, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(10, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(11, "")), equalTo(Right));
		assertThat(ComponentTools.getSideForPin(r, new Pin(12, "")), equalTo(Right));
	}
}
