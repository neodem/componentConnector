package com.neodem.componentConnector;

import java.util.Arrays;
import java.util.Collection;

import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.factory.RelayFactory;
import com.neodem.componentConnector.tools.Calculator;
import com.neodem.componentConnector.tools.ComponentTools;
import com.neodem.componentConnector.tools.DefaultCalculator;

public abstract class AbstractBaseRelayLocatorTest {
	
	protected RelayFactory relayFactory = new RelayFactory();
	
	protected static final Calculator calc = new DefaultCalculator();
	
	protected Connection makeConnection(Component from, Side fromSide, Component to, Side toSide) {
		Pin fromPin = ComponentTools.getPinOnSide(from, fromSide);
		Pin toPin = ComponentTools.getPinOnSide(to, toSide);
		return new Connection(from, Arrays.asList(fromPin), to, Arrays.asList(toPin));
	}

	protected Connection makeConnection(Component from, String fromPinLabel, Component to, String toPinLabel) {
		Collection<Pin> fromPins = from.getPins(fromPinLabel);
		Collection<Pin> toPins = to.getPins(toPinLabel);

		Connection con = new Connection(from, fromPins, to, toPins);
		
		return con;
	}
}
