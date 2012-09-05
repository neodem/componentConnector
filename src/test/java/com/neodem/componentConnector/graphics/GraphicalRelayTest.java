package com.neodem.componentConnector.graphics;

import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GraphicalRelayTest extends AbstractBaseRelayLocatorTest {

	@Test
	public void outputShouldBeOnCorrectPins() {
		Component r = relayFactory.make("abcd", 0, 0);

		Component o = relayFactory.make("other", 0, 0);

		GraphicalComponent gr = new GraphicalComponent(r);

		gr.addRelatedConnection(makeConnection(r, "VCC", o, "VCC"));
		gr.addRelatedConnection(makeConnection(r, "IN", o, "IN"));
		gr.addRelatedConnection(makeConnection(r, "ON", o, "ON"));
		gr.addRelatedConnection(makeConnection(r, "OFF", o, "OFF"));
		gr.addRelatedConnection(makeConnection(r, "GND", o, "GND"));

		assertThat(gr.makeLine(0), equalTo("               --------------               "));
		assertThat(gr.makeLine(1), equalTo("  other:OFF(7)-|7          6|-other:ON(6)   "));
		assertThat(gr.makeLine(2), equalTo("  other:GND(8)-|8   abcd   5|-other:VCC(5)  "));
		assertThat(gr.makeLine(3), equalTo("               |9          4|               "));
		assertThat(gr.makeLine(4), equalTo("               |10         3|               "));
		assertThat(gr.makeLine(5), equalTo("               |11         2|               "));
		assertThat(gr.makeLine(6), equalTo("               |12         1|-other:IN(1)   "));
		assertThat(gr.makeLine(7), equalTo("               --------------               "));
	}
	
	@Test
	public void makeLeftContentShouldBeCorrectForNonInverted() {
		Component r = relayFactory.make("abcd", 0, 0);
		Component o = relayFactory.make("other", 0, 0);

		GraphicalComponent gr = new GraphicalComponent(r);

		gr.addRelatedConnection(makeConnection(r, "VCC", o, "VCC"));
		gr.addRelatedConnection(makeConnection(r, "IN", o, "IN"));
		gr.addRelatedConnection(makeConnection(r, "ON", o, "ON"));
		gr.addRelatedConnection(makeConnection(r, "OFF", o, "OFF"));
		gr.addRelatedConnection(makeConnection(r, "GND", o, "GND"));

		assertThat(gr.makeLeftContent(0), equalTo("  other:OFF(7)-"));
	}

	@Test
	public void makeRightContentShouldBeCorrectForNonInverted() {
		Component r = relayFactory.make("abcd", 0, 0);
		Component o = relayFactory.make("other", 0, 0);

		GraphicalComponent gr = new GraphicalComponent(r);

		gr.addRelatedConnection(makeConnection(r, "VCC", o, "VCC"));
		gr.addRelatedConnection(makeConnection(r, "IN", o, "IN"));
		gr.addRelatedConnection(makeConnection(r, "ON", o, "ON"));
		gr.addRelatedConnection(makeConnection(r, "OFF", o, "OFF"));
		gr.addRelatedConnection(makeConnection(r, "GND", o, "GND"));

		assertThat(gr.makeRightContent(0), equalTo("-other:ON(6)   "));
	}

	@Test
	public void makeMiddleContentShouldBeCorrectForNonInverted() {
		Component r = relayFactory.make("abcd", 0, 0);
		Component o = relayFactory.make("other", 0, 0);

		GraphicalComponent gr = new GraphicalComponent(r);

		gr.addRelatedConnection(makeConnection(r, "VCC", o, "VCC"));
		gr.addRelatedConnection(makeConnection(r, "IN", o, "IN"));
		gr.addRelatedConnection(makeConnection(r, "ON", o, "ON"));
		gr.addRelatedConnection(makeConnection(r, "OFF", o, "OFF"));
		gr.addRelatedConnection(makeConnection(r, "GND", o, "GND"));

		assertThat(gr.makeMiddleContent(0, ""), equalTo("|7          6|"));
	}
	
	@Test
	public void outputShouldBeOnCorrectPinsInverted() {
		Component r = relayFactory.make("abcd", 0, 0);
		r.setInverted(true);
		Component o = relayFactory.make("other", 0, 0);
		
		GraphicalComponent gr = new GraphicalComponent(r);
		
		gr.addRelatedConnection(makeConnection(r, "VCC", o, "VCC"));
		gr.addRelatedConnection(makeConnection(r, "IN", o, "IN"));
		gr.addRelatedConnection(makeConnection(r, "ON", o, "ON"));
		gr.addRelatedConnection(makeConnection(r, "OFF", o, "OFF"));
		gr.addRelatedConnection(makeConnection(r, "GND", o, "GND"));
		
		assertThat(gr.makeLine(0), equalTo("               --------------               "));
		assertThat(gr.makeLine(1), equalTo("   other:IN(1)-|1         12|               "));
		assertThat(gr.makeLine(2), equalTo("               |2  abcd   11|               "));
		assertThat(gr.makeLine(3), equalTo("               |3    I    10|               "));
		assertThat(gr.makeLine(4), equalTo("               |4          9|               "));
		assertThat(gr.makeLine(5), equalTo("  other:VCC(5)-|5          8|-other:GND(8)  "));
		assertThat(gr.makeLine(6), equalTo("   other:ON(6)-|6          7|-other:OFF(7)  "));
		assertThat(gr.makeLine(7), equalTo("               --------------               "));
	}
	
	@Test
	public void makeLeftContentShouldBeCorrectForInverted() {
		Component r = relayFactory.make("abcd", 0, 0);
		r.setInverted(true);
		Component o = relayFactory.make("other", 0, 0);

		GraphicalComponent gr = new GraphicalComponent(r);

		gr.addRelatedConnection(makeConnection(r, "VCC", o, "VCC"));
		gr.addRelatedConnection(makeConnection(r, "IN", o, "IN"));
		gr.addRelatedConnection(makeConnection(r, "ON", o, "ON"));
		gr.addRelatedConnection(makeConnection(r, "OFF", o, "OFF"));
		gr.addRelatedConnection(makeConnection(r, "GND", o, "GND"));

		assertThat(gr.makeLeftContent(0), equalTo("   other:IN(1)-"));
		assertThat(gr.makeLeftContent(4), equalTo("  other:VCC(5)-"));
		assertThat(gr.makeLeftContent(5), equalTo("   other:ON(6)-"));
	}

	@Test
	public void makeRightContentShouldBeCorrectForInverted() {
		Component r = relayFactory.make("abcd", 0, 0);
		r.setInverted(true);
		Component o = relayFactory.make("other", 0, 0);

		GraphicalComponent gr = new GraphicalComponent(r);

		gr.addRelatedConnection(makeConnection(r, "VCC", o, "VCC"));
		gr.addRelatedConnection(makeConnection(r, "IN", o, "IN"));
		gr.addRelatedConnection(makeConnection(r, "ON", o, "ON"));
		gr.addRelatedConnection(makeConnection(r, "OFF", o, "OFF"));
		gr.addRelatedConnection(makeConnection(r, "GND", o, "GND"));

		assertThat(gr.makeRightContent(4), equalTo("-other:GND(8)  "));
	}

	@Test
	public void makeMiddleContentShouldBeCorrectForInverted() {
		Component r = relayFactory.make("abcd", 0, 0);
		r.setInverted(true);
		Component o = relayFactory.make("other", 0, 0);

		GraphicalComponent gr = new GraphicalComponent(r);

		gr.addRelatedConnection(makeConnection(r, "VCC", o, "VCC"));
		gr.addRelatedConnection(makeConnection(r, "IN", o, "IN"));
		gr.addRelatedConnection(makeConnection(r, "ON", o, "ON"));
		gr.addRelatedConnection(makeConnection(r, "OFF", o, "OFF"));
		gr.addRelatedConnection(makeConnection(r, "GND", o, "GND"));

		assertThat(gr.makeMiddleContent(0, ""), equalTo("|1         12|"));
	}

}