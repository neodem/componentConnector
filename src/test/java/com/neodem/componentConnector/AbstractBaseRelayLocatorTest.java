package com.neodem.componentConnector;

import static com.neodem.componentConnector.model.Side.Right;

import java.util.Arrays;
import java.util.Collection;

import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.factory.RelayFactory;
import com.neodem.componentConnector.tools.Calculator;
import com.neodem.componentConnector.tools.Calculator;

public abstract class AbstractBaseRelayLocatorTest {
	
	protected RelayFactory relayFactory = new RelayFactory();
	
	protected static final Calculator calc = new Calculator();
	
	protected Connection makeConnection(Component from, Side fromSide, Component to, Side toSide) {
		Pin fromPin = getPinOnSide(from, fromSide);
		Pin toPin = getPinOnSide(to, toSide);
		return new Connection(from, Arrays.asList(fromPin), to, Arrays.asList(toPin));
	}

	protected Connection makeConnection(Component from, String fromPinLabel, Component to, String toPinLabel) {
		Collection<Pin> fromPins = from.getPins(fromPinLabel);
		Collection<Pin> toPins = to.getPins(toPinLabel);

		Connection con = new Connection(from, fromPins, to, toPins);
		
		return con;
	}
	
	/**
	 * return some pin on the given side of the component
	 * 
	 * @param c
	 * @param side
	 * @return
	 */
	public Pin getPinOnSide(Connectable c, Side side) {
		// right side pin guaranteed to be pin #1
		if (side == Right) {
			return new Pin(1, "RightPin");
		}
		
		// left side pin guaranteed to be pinMax
		return new Pin(c.getNumberofPins(), "LeftPin");
	}
}
