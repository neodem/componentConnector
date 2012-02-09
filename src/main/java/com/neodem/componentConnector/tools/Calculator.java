package com.neodem.componentConnector.tools;

import com.neodem.componentConnector.model.Connection;

/**
 * in charge of the distance calculations.. YOu can plug in different
 * calculators depending on the weighting of your distances
 * 
 * @author vfumo
 * 
 */
public interface Calculator {

	int calculateDistance(Connection connection);
}
