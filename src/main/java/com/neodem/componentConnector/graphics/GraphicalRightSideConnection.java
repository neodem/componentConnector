package com.neodem.componentConnector.graphics;

import com.neodem.componentConnector.model.Connection;

public class GraphicalRightSideConnection extends GraphicalConnection {
	
	public GraphicalRightSideConnection(Connection c,  boolean inBoundConnection) {
		super(c, inBoundConnection);
		
		if (inBoundConnection) {
			// 'from' connections (ie. inbound)
			setLine("-" + otherId + ":" + otherPin);
		} else {
			// these are 'to' connections (ie. outbound)
			setLine("-" + otherId + ":" + otherPin);
		}
	}

}
