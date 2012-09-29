package com.neodem.componentConnector.model;

public class Endpoint extends AbstractConnectable {
	
	public Endpoint(String name, int pinCount) {
		super(name, pinCount);
	}

	public Endpoint(Endpoint e) {
		super(e.getName(), e.getPinCount());
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return true;
	}
}
