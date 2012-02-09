package com.neodem.componentConnector.model;

public class Pin {
	private int pinNumber;

	private String label;

	public Pin(int pinNumber, String label) {
		super();
		this.pinNumber = pinNumber;
		this.label = label;
	}

	@Override
	public String toString() {
		return "" + pinNumber + ":" + label;
	}

	/**
	 * @return the pinNumber
	 */
	public int getPinNumber() {
		return pinNumber;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + pinNumber;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pin other = (Pin) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (pinNumber != other.pinNumber)
			return false;
		return true;
	}
}
