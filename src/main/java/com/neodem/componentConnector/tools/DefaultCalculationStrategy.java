package com.neodem.componentConnector.tools;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;

import com.neodem.componentConnector.model.Side;

public class DefaultCalculationStrategy implements CalculationStrategy {
	public int relativeDistance(int loc1, int loc2) {

		if (loc1 == loc2) {
			return 0;
		}

		if (loc1 > loc2) {
			return loc1 - loc2;
		}

		return loc2 - loc1;
	}

	/**
	 * if we are aligned on top of each other, a connection on the same side is
	 * ideal so that gets a 0. On opposite sides we have a 1
	 * 
	 * @param fromSide
	 * @param toSide
	 * @return
	 */
	public int aligned(Side fromSide, Side toSide) {
		if ((fromSide == Right && toSide == Right) || (fromSide == Left && toSide == Left))
			return 0;
		return 1;
	}

	public int toRightOfFrom(Side fromSide, Side toSide) {
		if (fromSide == Right) {
			if (toSide == Left)
				return 0;
			if (toSide == Right)
				return 1;
		}

		// from side is left
		if (toSide == Left)
			return 1;

		return 2;
	}

	public int toLeftOfFrom(Side fromSide, Side toSide) {
		if (fromSide == Left) {
			if (toSide == Left)
				return 1;
			if (toSide == Right)
				return 0;
		}

		// from side is right
		if (toSide == Right)
			return 1;

		return 2;
	}
}
