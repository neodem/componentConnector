package com.neodem.componentConnector.model;


public interface Locatable extends Connectable {

	int getyLoc();

	int getxLoc();

	boolean isMoveable();
}
