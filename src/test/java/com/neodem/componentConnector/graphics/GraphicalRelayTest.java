package com.neodem.componentConnector.graphics;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;

import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.graphics.GraphicalComponent;
import com.neodem.componentConnector.model.component.Component;

public class GraphicalRelayTest extends AbstractBaseRelayLocatorTest {
	
	@Test
	public void test() {
		Component r = relayFactory.make("abcd", 0, 0);
		r.setInverted(false);
		r.setMoveable(false);
		
		Component o = relayFactory.make("other", 0,0);
		
		GraphicalComponent gr = new GraphicalComponent(r);
		
		gr.addRelatedConnection(makeConnection(r,Left,o, Left));
		gr.addRelatedConnection(makeConnection(r,Right,o, Left));
		gr.addRelatedConnection(makeConnection(o,Left,r, Left));
		gr.addRelatedConnection(makeConnection(o,Right,r, Right));

		System.out.println(gr.getAsString());
	}

}
