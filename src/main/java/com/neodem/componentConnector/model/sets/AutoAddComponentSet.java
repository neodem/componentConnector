package com.neodem.componentConnector.model.sets;

import com.neodem.componentConnector.model.Component;

/**
 * gives the ability to add components at random locations
 * 
 * @author vfumo
 * 
 */
public class AutoAddComponentSet extends ComponentSet {

	protected int nextYLoc = 0;
	protected int nextXLoc = -1;

	protected boolean full = false;
	
	public AutoAddComponentSet(int sizeX, int sizeY) {
		super(sizeX, sizeY);
	}

	public AutoAddComponentSet(ComponentSet set) {
		super(set);
	}

	@Override
	public void addComponent(Component c) {
		advance();
		if (full) {
			throw new IndexOutOfBoundsException("you are full!");
		}
		c.setxLoc(nextXLoc);
		c.setyLoc(nextYLoc);
		super.addComponent(c);
	}

	protected void advance() {
		nextXLoc++;

		if (nextXLoc == sizeX) {
			nextXLoc = 0;
			nextYLoc++;

			if (nextYLoc == sizeY) {
				full = true;
			}
		}
	}
}
