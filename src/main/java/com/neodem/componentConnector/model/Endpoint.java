package com.neodem.componentConnector.model;

public class Endpoint extends AbstractConnectable implements Connectable {
	public Endpoint(String name,String id, int pinCount) {
		super(name, id, pinCount);
	}

	public Endpoint(Endpoint e) {
		super(e.getName(), e.getId(), e.getPinCount());
	}
}
