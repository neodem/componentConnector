package com.neodem.componentConnector.graphics;

import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connection;

public class GraphicalRightSideConnection extends GraphicalConnection {
	
	public GraphicalRightSideConnection(Connection c, Component parent) {
		super(c, parent);
		
		if (from) {
			// 'from' connections (ie. inbound)
			setLine("-" + other.getName() + ":" + otherPin);
		} else {
			// these are 'to' connections (ie. outbound)
			setLine("-" + other.getName() + ":" + otherPin);
		}
		
	}

}
