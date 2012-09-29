package com.neodem.componentConnector.tools;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;

import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;

public class ComponentTools {
	/**
	 * for a given pin number determine the side it is on
	 * 
	 * @param fromPin
	 * @return
	 */
	public static Side getSideForPin(Connectable c, Pin fromPin) {
		if(c.isInverted()) {
			if (fromPin.getPinNumber() <= (c.getNumberofPins() / 2)) {
				return Left;
			}
			return Right;
		}
		
		if (fromPin.getPinNumber() <= (c.getNumberofPins() / 2)) {
			return Right;
		}
		return Left;
	}

	/**
	 * for a given component and pin, determine the 'index' (From top to bottom)
	 * where the pin connects
	 * 
	 * @param c
	 * @param fromPin
	 * @return
	 */
	public static int determineSideIndex(Connectable c, Pin fromPin) {
		int pinsPerSide = c.getNumberofPins() / 2;
		int pinNumber = fromPin.getPinNumber();
		
		if(c.isInverted()) {
			// inverted puts pin one at top left
			if (pinNumber > pinsPerSide) {
				return pinNumber - (2 * (pinNumber-pinsPerSide));
			}
			return pinNumber - 1;
		} 
		
		// regular puts pin one at bottom right
		if (pinNumber <= pinsPerSide) {
			return pinsPerSide - pinNumber;
		}
		return pinNumber - pinsPerSide - 1;
		
	}
}
