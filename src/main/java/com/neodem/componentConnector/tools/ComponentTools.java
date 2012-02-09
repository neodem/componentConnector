package com.neodem.componentConnector.tools;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;

import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.component.Component;

public class ComponentTools {
	/**
	 * for a given pin number determine the side it is on
	 * 
	 * @param fromPin
	 * @return
	 */
	public static Side getSideForPin(Component c, Pin fromPin) {
		if (fromPin.getPinNumber() <= (c.getNumberofPins() / 2)) {
			return Right;
		}
		return Left;
	}

	/**
	 * for a given component and pin, determine the 
	 * 'index' (From top to bottom) where the pin connects
	 * 
	 * @param c
	 * @param fromPin
	 * @return
	 */
	public static int determineSideIndex(Component c, Pin fromPin) {
		int rightSideMax = c.getNumberofPins() / 2;
		int pinNumber = fromPin.getPinNumber();

		if (pinNumber <= rightSideMax) {
			return rightSideMax - pinNumber;
		}
		return pinNumber - rightSideMax;
	}
	
	/**
	 * return some pin on the given side of the component
	 * 
	 * @param c
	 * @param side
	 * @return
	 */
	public static Pin getPinOnSide(Component c, Side side) {
		if(side == Right) {
			return new Pin(1, "");
		}
		return new Pin(c.getNumberofPins(), "");
	}
}
