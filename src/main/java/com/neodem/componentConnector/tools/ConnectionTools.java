package com.neodem.componentConnector.tools;

import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Locatable;

/**
 * @author vfumo
 * 
 */
public class ConnectionTools {

	public static boolean onSameRow(Connection c) {
		Connectable from = c.getFrom();
		Connectable to = c.getTo();
		
		if((from instanceof Locatable) && (to instanceof Locatable)){
			int fromY = ((Locatable)from).getyLoc();
			int toY = ((Locatable)to).getyLoc();

			// same row?
			if (fromY == toY) {
				return true;
			}
		}

		return false;
	}

	public static boolean inSameColumn(Connection c) {
		Connectable from = c.getFrom();
		Connectable to = c.getTo();
		
		if((from instanceof Locatable) && (to instanceof Locatable)){
			int fromX = ((Locatable)from).getxLoc();
			int toX = ((Locatable)to).getxLoc();

			// same col?
			if (fromX == toX) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean nextToEachOtherHorizontally(Connection c) {
		Connectable from = c.getFrom();
		Connectable to = c.getTo();
		
		if((from instanceof Locatable) && (to instanceof Locatable)){
			int fromX = ((Locatable)from).getxLoc();
			int toX = ((Locatable)to).getxLoc();

			if ((fromX - toX == 1) || (toX - fromX == 1)) {
				return true;
			}
		}

		return false;
	}

	public static boolean nextToEachOtherVeritcally(Connection c) {
		Connectable from = c.getFrom();
		Connectable to = c.getTo();
		
		if((from instanceof Locatable) && (to instanceof Locatable)){
			int fromY = ((Locatable)from).getyLoc();
			int toY = ((Locatable)to).getyLoc();

			if ((fromY - toY == 1) || (toY - fromY == 1)) {
				return true;
			}
		}

		return false;
	}

	public static boolean adjacent(Connection c) {
		return nextToEachOtherHorizontally(c) || nextToEachOtherVeritcally(c);
	}

	public static boolean toRightOfFrom(Connection c) {
		return calcToXMinusFromX(c) > 0;
	}

	public static boolean toLeftOfFrom(Connection c) {
		return calcToXMinusFromX(c) < 0;
	}

	private static int calcToXMinusFromX(Connection c) {
		Connectable from = c.getFrom();
		Connectable to = c.getTo();
		
		if((from instanceof Locatable) && (to instanceof Locatable)){
			int fromX = ((Locatable)from).getxLoc();
			int toX = ((Locatable)to).getxLoc();
			return toX - fromX;
		}
		
		return 0;
	}

}
