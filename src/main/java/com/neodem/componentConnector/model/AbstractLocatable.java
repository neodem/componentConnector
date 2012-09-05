package com.neodem.componentConnector.model;

/**
 * @author vfumo
 * 
 */
public class AbstractLocatable extends AbstractConnectable implements Locatable {

	/**
	 * flag for movability.
	 */
	private boolean moveable = true;

	private Location location;

	public AbstractLocatable(String name, String id, int pinCount) {
		this(name, id, pinCount, 0, 0);
	}

	public AbstractLocatable(String name, String id, int pinCount, int x, int y) {
		super(name, id, pinCount);
		location = new Location(x, y);
		moveable = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + (moveable ? 1231 : 1237);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof AbstractLocatable)) {
			return false;
		}
		AbstractLocatable other = (AbstractLocatable) obj;
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (moveable != other.moveable) {
			return false;
		}
		return true;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isValid() {
		// TODO also maybe check to make sure that the connections aren't
		// duplicated, etc.
		return true;
	}

	/**
	 * @return the xLoc
	 */
	public int getxLoc() {
		return location.getX();
	}

	/**
	 * @param xLoc
	 *            the xLoc to set
	 */
	public void setxLoc(int xLoc) {
		int oldY = location.getY();
		location = new Location(xLoc, oldY);
	}

	/**
	 * @return the yLoc
	 */
	public int getyLoc() {
		return location.getY();
	}

	/**
	 * @param yLoc
	 *            the yLoc to set
	 */
	public void setyLoc(int yLoc) {
		int oldX = location.getX();
		location = new Location(oldX, yLoc);
	}

	/**
	 * @param moveable
	 *            the moveable to set
	 */
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	/**
	 * @return the moveable
	 */
	public boolean isMoveable() {
		return moveable;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractLocatable [name=" + getName() + ", inverted=" + isInverted() + ", moveable=" + moveable
				+ ", location=" + location + "]";
	}
}
