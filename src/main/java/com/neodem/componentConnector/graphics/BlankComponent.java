package com.neodem.componentConnector.graphics;

import com.neodem.componentConnector.model.Location;


public class BlankComponent extends GraphicalComponent {
	public BlankComponent(int colNum, int rowNum) {
		super("<BLANK>", new Location(rowNum, colNum), null, 6,8, false, null);
	}
}
