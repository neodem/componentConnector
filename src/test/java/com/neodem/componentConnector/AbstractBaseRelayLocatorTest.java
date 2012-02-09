package com.neodem.componentConnector;

import java.util.Arrays;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.model.component.RelayFactory;
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
}
