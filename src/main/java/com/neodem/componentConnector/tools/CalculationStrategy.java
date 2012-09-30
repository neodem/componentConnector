package com.neodem.componentConnector.tools;

import com.neodem.componentConnector.model.Side;

public interface CalculationStrategy {
	public int relativeDistance(int loc1, int loc2);

	public int aligned(Side fromSide, Side toSide);

	public int toRightOfFrom(Side fromSide, Side toSide);

	public int toLeftOfFrom(Side fromSide, Side toSide);

}
