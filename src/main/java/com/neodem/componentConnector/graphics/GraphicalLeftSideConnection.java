package com.neodem.componentConnector.graphics;

import com.neodem.componentConnector.model.Connection;

public class GraphicalLeftSideConnection extends GraphicalConnection {

	public GraphicalLeftSideConnection(Connection c,  boolean inBoundConnection) {
		super(c, inBoundConnection);

		if (inBoundConnection) {
			// 'from' connections (ie. inbound)
			setLine(otherId + ":" + otherPin + "-");
		} else {
			// these are 'to' connections (ie. outbound)
			setLine(otherId + ":" + otherPin + "-");
		}
	}
}
