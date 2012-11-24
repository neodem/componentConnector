package com.neodem.componentConnector.graphics;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.components.BaseComponent;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GraphicalRelayTest extends AbstractBaseRelayLocatorTest {

	@Test
	public void outputShouldBeOnCorrectPins() {
		BaseComponent r = relayFactory.make("relay", "abcd");
		BaseComponent o = relayFactory.make("relay", "other");

		Collection<Connection> cons = new ArrayList<Connection>();

		cons.add(makeConnection(r, "Vcc", o, "Vcc"));
		cons.add(makeConnection(r, "IN", o, "IN"));
		cons.add(makeConnection(r, "ON", o, "ON"));
		cons.add(makeConnection(r, "OFF", o, "OFF"));
		cons.add(makeConnection(r, "GND", o, "GND"));

		GraphicalComponent gr = new GraphicalComponent("abcd", new Location(0, 0), r, false, cons);

		assertThat(gr.makeLine(0), equalTo("               --------------               "));
		assertThat(gr.makeLine(1), equalTo("  other:OFF(7)-|7          6|-other:ON(6)   "));
		assertThat(gr.makeLine(2), equalTo("  other:GND(8)-|8   abcd   5|-other:Vcc(5)  "));
		assertThat(gr.makeLine(3), equalTo("               |9          4|               "));
		assertThat(gr.makeLine(4), equalTo("               |10  [0,0]  3|               "));
		assertThat(gr.makeLine(5), equalTo("               |11         2|               "));
		assertThat(gr.makeLine(6), equalTo("               |12         1|-other:IN(1)   "));
		assertThat(gr.makeLine(7), equalTo("               --------------               "));
	}

	@Test
	public void makeLeftContentShouldBeCorrectForNonInverted() {
		BaseComponent r = relayFactory.make("relay", "abcd");
		BaseComponent o = relayFactory.make("relay", "other");
		
		Collection<Connection> cons = new ArrayList<Connection>();
		cons.add(makeConnection(r, "Vcc", o, "Vcc"));
		cons.add(makeConnection(r, "IN", o, "IN"));
		cons.add(makeConnection(r, "ON", o, "ON"));
		cons.add(makeConnection(r, "OFF", o, "OFF"));
		cons.add(makeConnection(r, "GND", o, "GND"));

		GraphicalComponent gr = new GraphicalComponent("abcd", new Location(0, 0), r, false, cons);

		assertThat(gr.makeLeftContent(0), equalTo("  other:OFF(7)-"));
	}

	@Test
	public void makeRightContentShouldBeCorrectForNonInverted() {
		BaseComponent r = relayFactory.make("relay", "abcd");
		BaseComponent o = relayFactory.make("relay", "other");
		
		Collection<Connection> cons = new ArrayList<Connection>();
		cons.add(makeConnection(r, "Vcc", o, "Vcc"));
		cons.add(makeConnection(r, "IN", o, "IN"));
		cons.add(makeConnection(r, "ON", o, "ON"));
		cons.add(makeConnection(r, "OFF", o, "OFF"));
		cons.add(makeConnection(r, "GND", o, "GND"));

		GraphicalComponent gr = new GraphicalComponent("abcd", new Location(0, 0), r, false, cons);

		assertThat(gr.makeRightContent(0), equalTo("-other:ON(6)   "));
	}

	@Test
	public void makeMiddleContentShouldBeCorrectForNonInverted() {
		BaseComponent r = relayFactory.make("relay", "abcd");
		BaseComponent o = relayFactory.make("relay", "other");
		
		Collection<Connection> cons = new ArrayList<Connection>();
		cons.add(makeConnection(r, "Vcc", o, "Vcc"));
		cons.add(makeConnection(r, "IN", o, "IN"));
		cons.add(makeConnection(r, "ON", o, "ON"));
		cons.add(makeConnection(r, "OFF", o, "OFF"));
		cons.add(makeConnection(r, "GND", o, "GND"));

		GraphicalComponent gr = new GraphicalComponent("abcd", new Location(0, 0), r, false, cons);

		assertThat(gr.makeMiddleContent(0, ""), equalTo("|7          6|"));
	}

	@Test
	public void outputShouldBeOnCorrectPinsInverted() {
		BaseComponent r = relayFactory.make("relay", "abcd");
		BaseComponent o = relayFactory.make("relay", "other");
		
		Collection<Connection> cons = new ArrayList<Connection>();
		cons.add(makeConnection(r, "Vcc", o, "Vcc"));
		cons.add(makeConnection(r, "IN", o, "IN"));
		cons.add(makeConnection(r, "ON", o, "ON"));
		cons.add(makeConnection(r, "OFF", o, "OFF"));
		cons.add(makeConnection(r, "GND", o, "GND"));

		GraphicalComponent gr = new GraphicalComponent("abcd", new Location(0, 0), r, true, cons);

		assertThat(gr.makeLine(0), equalTo("               --------------               "));
		assertThat(gr.makeLine(1), equalTo("   other:IN(1)-|1         12|               "));
		assertThat(gr.makeLine(2), equalTo("               |2  abcd   11|               "));
		assertThat(gr.makeLine(3), equalTo("               |3    I    10|               "));
		assertThat(gr.makeLine(4), equalTo("               |4  [0,0]   9|               "));
		assertThat(gr.makeLine(5), equalTo("  other:Vcc(5)-|5          8|-other:GND(8)  "));
		assertThat(gr.makeLine(6), equalTo("   other:ON(6)-|6          7|-other:OFF(7)  "));
		assertThat(gr.makeLine(7), equalTo("               --------------               "));
	}

	@Test
	public void makeLeftContentShouldBeCorrectForInverted() {
		BaseComponent r = relayFactory.make("relay", "abcd");
		BaseComponent o = relayFactory.make("relay", "other");
		
		Collection<Connection> cons = new ArrayList<Connection>();
		cons.add(makeConnection(r, "Vcc", o, "Vcc"));
		cons.add(makeConnection(r, "IN", o, "IN"));
		cons.add(makeConnection(r, "ON", o, "ON"));
		cons.add(makeConnection(r, "OFF", o, "OFF"));
		cons.add(makeConnection(r, "GND", o, "GND"));

		GraphicalComponent gr = new GraphicalComponent("abcd", new Location(0, 0), r, true, cons);

		assertThat(gr.makeLeftContent(0), equalTo("   other:IN(1)-"));
		assertThat(gr.makeLeftContent(4), equalTo("  other:Vcc(5)-"));
		assertThat(gr.makeLeftContent(5), equalTo("   other:ON(6)-"));
	}

	@Test
	public void makeRightContentShouldBeCorrectForInverted() {
		BaseComponent r = relayFactory.make("relay", "abcd");
		BaseComponent o = relayFactory.make("relay", "other");
		
		Collection<Connection> cons = new ArrayList<Connection>();
		cons.add(makeConnection(r, "Vcc", o, "Vcc"));
		cons.add(makeConnection(r, "IN", o, "IN"));
		cons.add(makeConnection(r, "ON", o, "ON"));
		cons.add(makeConnection(r, "OFF", o, "OFF"));
		cons.add(makeConnection(r, "GND", o, "GND"));

		GraphicalComponent gr = new GraphicalComponent("abcd", new Location(0, 0), r, true, cons);

		assertThat(gr.makeRightContent(4), equalTo("-other:GND(8)  "));
	}

	@Test
	public void makeMiddleContentShouldBeCorrectForInverted() {
		BaseComponent r = relayFactory.make("relay", "abcd");
		BaseComponent o = relayFactory.make("relay", "other");
		
		Collection<Connection> cons = new ArrayList<Connection>();
		cons.add(makeConnection(r, "Vcc", o, "Vcc"));
		cons.add(makeConnection(r, "IN", o, "IN"));
		cons.add(makeConnection(r, "ON", o, "ON"));
		cons.add(makeConnection(r, "OFF", o, "OFF"));
		cons.add(makeConnection(r, "GND", o, "GND"));

		GraphicalComponent gr = new GraphicalComponent("abcd", new Location(0, 0), r, true, cons);

		assertThat(gr.makeMiddleContent(0, ""), equalTo("|1         12|"));
	}

}