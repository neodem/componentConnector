package com.neodem.componentConnector.model;

/**
 * @author vfumo
 * 
 */
public class Location {
	public Location(int row, int col) {
		this.col = col;
		this.row = row;
	}

	private int col;
	private int row;

	@Override
	public String toString() {
		return "[" + row + "," + col + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + row;
		result = prime * result + col;
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
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Location)) {
			return false;
		}
		Location other = (Location) obj;
		if (row != other.row) {
			return false;
		}
		if (col != other.col) {
			return false;
		}
		return true;
	}

	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
}
