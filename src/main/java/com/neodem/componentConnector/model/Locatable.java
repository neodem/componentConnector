package com.neodem.componentConnector.model;


public interface Locatable extends Connectable {

	int getyLoc();

	int getxLoc();
	
	public void setxLoc(int xLoc);
	
	public void setyLoc(int yLoc);

	boolean isMoveable();
}
