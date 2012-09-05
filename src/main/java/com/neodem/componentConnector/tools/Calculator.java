package com.neodem.componentConnector.tools;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * in charge of the distance calculations.. YOu can plug in different
 * calculators depending on the weighting of your distances
 * 
 * @author vfumo
 * 
 */
public interface Calculator {
	
	int calculateSetSize(ComponentSet set);

	int calculateDistance(Connection connection);
}
