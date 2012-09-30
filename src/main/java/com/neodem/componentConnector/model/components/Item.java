package com.neodem.componentConnector.model.components;

import java.util.Collection;
import java.util.HashSet;

import com.neodem.componentConnector.model.Connection;

public class Item extends AbstractConnectable {

	private Collection<Connection> connectsTo;
	
	public Item(String id, String type, int pinCount) {
		super(id, type, pinCount);
		connectsTo = new HashSet<Connection>();
	}
	
	public Collection<Connection> removeConnection(Connection c) {
		connectsTo.remove(c);
		return connectsTo;
	}
	public Collection<Connection> addConnection(Connection c) {
		connectsTo.add(c);
		return connectsTo;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	public Collection<Connection> getConnections() {
		return new HashSet<Connection>(connectsTo);
	}
}
