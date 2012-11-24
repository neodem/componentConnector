package com.neodem.componentConnector;

import static com.neodem.componentConnector.model.Side.Right;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.junit.BeforeClass;

import com.neodem.componentConnector.graphics.TestFullSet;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.componentConnector.model.factory.ComponentFactory;
import com.neodem.componentConnector.model.factory.ConnectableDefinition;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.tools.Calculator;
import com.neodem.componentConnector.tools.ConnectableHelper;

public abstract class AbstractBaseRelayLocatorTest {
	
	@BeforeClass
	public static void beforeClass() {
		ClassLoader classLoader = TestFullSet.class.getClassLoader();

		URL url = classLoader.getResource("All-Connectables.xml");
		File factoryDef = new File(url.getPath());

		Collection<ConnectableDefinition> defs = ConnectableHelper.loadConnectableDefs(factoryDef);
		relayFactory = new ComponentFactory(defs);
	}
	
	protected static ComponentFactory relayFactory;
	
	protected static final Calculator calc = new Calculator();
	
	protected Connection makeConnection(BaseComponent from, Side fromSide, BaseComponent to, Side toSide) {
		Pin fromPin = getPinOnSide(from, fromSide);
		Pin toPin = getPinOnSide(to, toSide);
		return new Connection(Arrays.asList(fromPin), to.getId(), Arrays.asList(toPin));
	}

	protected Connection makeConnection(BaseComponent from, String fromPinLabel, BaseComponent to, String toPinLabel) {
		Collection<Pin> fromPins = from.getPins(fromPinLabel);
		Collection<Pin> toPins = to.getPins(toPinLabel);

		Connection con = new Connection(fromPins, to.getId(), toPins);
		
		return con;
	}
	
	protected BaseComponent addToSet(ComponentSet set, String id, int row, int col) {
		BaseComponent r = relayFactory.make("relay", id);
		set.addItem(r, new Location(row,col), false);
		return r;
	}
	
	/**
	 * return some pin on the given side of the component
	 * 
	 * @param c
	 * @param side
	 * @return
	 */
	public Pin getPinOnSide(BaseComponent c, Side side) {
		// right side pin guaranteed to be pin #1
		if (side == Right) {
			return new Pin(1, "RightPin");
		}
		
		// left side pin guaranteed to be pinMax
		return new Pin(c.getNumberofPins(), "LeftPin");
	}
}
