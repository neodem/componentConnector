package com.neodem.componentConnector.graphics;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.graphics.text.model.GraphicsObject;
import com.neodem.graphics.text.model.SingleLineGraphicsObject;

public abstract class GraphicalConnection extends SingleLineGraphicsObject implements GraphicsObject, Comparable<GraphicalConnection> {

	/**
	 * this is the Pin where the connection is coming from (or going to)
	 */
	protected Pin otherPin;

	/**
	 * set to true if we are coming into this component from someowhere else
	 * (eg. inbound connection)
	 */
	protected boolean inBoundConnection = false;

	protected String otherId;
	
	/**
	 * 
	 * @param c
	 *            the connection we are modeling
	 * @param parent
	 *            the parent component we are attaching this item to
	 */
	public GraphicalConnection(Connection c, boolean inBoundConnection) {
		otherId = c.getToId();
		otherPin = c.getToPin();
		this.inBoundConnection = inBoundConnection;
		setLine("");
	}
	
	public int compareTo(GraphicalConnection other) {
		return otherPin.getPinNumber() - other.otherPin.getPinNumber();
	}

	public boolean isValid() {
		return true;
	}

	/**
	 * invert the 'polarity' of the connection.. ie. if it was a from, make it to, etc.
	 */
	public void invert() {
		if (inBoundConnection == true) {
			inBoundConnection = false;
		} else {
			inBoundConnection = true;
		}

	}
}
