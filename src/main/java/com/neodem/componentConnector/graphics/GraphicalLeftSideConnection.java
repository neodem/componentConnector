package com.neodem.componentConnector.graphics;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.component.Component;

public class GraphicalLeftSideConnection extends GraphicalConnection {

	public GraphicalLeftSideConnection(Connection c, Component parent) {
		super(c, parent);

		if (from) {
			// 'from' connections (ie. inbound)
			setLine("-" + other.getName() + ":" + otherPin + "->");
		} else {
			// these are 'to' connections (ie. outbound)
			setLine("<-" + other.getName() + ":" + otherPin + "-");
		}
	}
}
