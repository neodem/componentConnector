package com.neodem.componentConnector.model.sets;

import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.components.Item;


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
	
	public AutoAddComponentSet(int rows, int cols) {
		super(rows, cols);
	}

	public AutoAddComponentSet(ComponentSet set) {
		super(set);
	}

	public void addItem(Item c) {
		advance();
		if (full) {
			throw new IndexOutOfBoundsException("you are full!");
		}
		
		super.addItem(c, new Location(nextYLoc, nextXLoc), false);
	}

	protected void advance() {
		nextXLoc++;

		if (nextXLoc == cols) {
			nextXLoc = 0;
			nextYLoc++;

			if (nextYLoc == rows) {
				full = true;
			}
		}
	}
}
