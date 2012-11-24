package com.neodem.componentConnector.model.components;

import static com.neodem.componentConnector.model.Side.Left;
import static com.neodem.componentConnector.model.Side.Right;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.neodem.common.utility.text.Chars;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;

public class BaseComponent {

	private String id;
	private String type;

	private int pinCount;

	private Collection<Pin> pins = new HashSet<Pin>();

	/**
	 * flag for movability.
	 */
	private boolean moveable = true;

	private Collection<Connection> connectsTo;

	public BaseComponent(String id, String type, int pinCount) {
		this.pinCount = pinCount;
		this.id = id;
		this.type = type;
		connectsTo = new HashSet<Connection>();
	}

	/**
	 * copy constructor
	 * 
	 * @param s
	 */
	public BaseComponent(BaseComponent s) {
		this.id = new String(s.id);
		this.type = new String(s.type);
		this.pinCount = s.pinCount;
		this.moveable = s.moveable;
		this.connectsTo = new HashSet<Connection>(s.connectsTo.size());

		for (Connection sc : s.connectsTo) {
			Connection c = new Connection(sc);
			this.connectsTo.add(c);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + id + ",(" + type + ")]";
	}

	/**
	 * give a full display of the component and its connections
	 * 
	 * @return
	 */
	public String display() {
		StringBuffer b = new StringBuffer();

		b.append(this);
		b.append(Chars.NEWLINE);
		for (Connection c : connectsTo) {
			b.append("  ");
			b.append(c);
			b.append(Chars.NEWLINE);
		}

		return b.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(19, 59).append(id).append(type).append(pinCount).append(pins).append(moveable)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		BaseComponent rhs = (BaseComponent) obj;
		return new EqualsBuilder().append(id, rhs.id).append(type, rhs.type).append(pinCount, rhs.pinCount)
				.append(moveable, rhs.moveable).append(pins, rhs.pins).isEquals();
	}

	public Collection<Connection> removeConnection(Connection c) {
		connectsTo.remove(c);
		return connectsTo;
	}

	public Collection<Connection> addConnection(Connection c) {
		connectsTo.add(c);
		return connectsTo;
	}

	public boolean isValid() {
		return true;
	}

	public Collection<Connection> getConnections() {
		return new HashSet<Connection>(connectsTo);
	}

	/**
	 * return all pins that match this label
	 * 
	 * @param pinLabel
	 * @return empty collection if none match
	 */
	public Collection<Pin> getPins(String pinLabel) {
		Collection<Pin> returnPins = new HashSet<Pin>(pinCount);

		for (Pin p : pins) {
			if (p.getLabel().equals(pinLabel)) {
				returnPins.add(p);
			}
		}
		return returnPins;
	}

	/**
	 * 
	 * @param pinNumber
	 * @param componentSide
	 * @param label
	 * @return
	 */
	public Collection<Pin> addPin(int pinNumber, String label) {
		return addPin(new Pin(pinNumber, label));
	}

	/**
	 * 
	 * @param pinNumber
	 * @param componentSide
	 * @param label
	 * @return
	 */
	public Collection<Pin> addPin(int pinNumber) {
		return addPin(pinNumber, "");
	}

	/**
	 * 
	 * @param pin
	 * @return
	 */
	public Collection<Pin> addPin(Pin pin) {
		pins.add(pin);
		return pins;
	}

	/**
	 * for a given pin number determine the side it is on
	 * 
	 * @param fromPin
	 * @return
	 */
	public Side getSideForPin(Boolean inverted, Pin fromPin) {
		if (inverted) {
			if (fromPin.getPinNumber() <= (getNumberofPins() / 2)) {
				return Left;
			}
			return Right;
		}

		if (fromPin.getPinNumber() <= (getNumberofPins() / 2)) {
			return Right;
		}
		return Left;
	}

	/**
	 * for a given pin, determine the 'index' (From top to bottom) where the pin
	 * connects
	 * 
	 * @param fromPin
	 * @return
	 */
	public int determineSideIndex(Boolean inverted, Pin fromPin) {
		int pinsPerSide = getNumberofPins() / 2;
		int pinNumber = fromPin.getPinNumber();

		if (inverted) {
			// inverted puts pin one at top left
			if (pinNumber > pinsPerSide) {
				return pinNumber - (2 * (pinNumber - pinsPerSide));
			}
			return pinNumber - 1;
		}

		// regular puts pin one at bottom right
		if (pinNumber <= pinsPerSide) {
			return pinsPerSide - pinNumber;
		}
		return pinNumber - pinsPerSide - 1;

	}

	/**
	 * @return the name
	 */
	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	/**
	 * @return the pins
	 */
	public Collection<Pin> getPins() {
		return pins;
	}

	/**
	 * @param pins
	 *            the pins to set
	 */
	public void setPins(Collection<Pin> pins) {
		this.pins = pins;
	}

	public int getNumberofPins() {
		return pinCount;
	}

	public int getPinCount() {
		return pinCount;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
}
