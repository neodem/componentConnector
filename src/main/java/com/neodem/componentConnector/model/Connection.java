package com.neodem.componentConnector.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Connection between two relay objects
 * 
 * @author vfumo
 * 
 */
public class Connection {

	protected Connectable to;
	protected Connectable from;
	
	// pins we can connect to (sometimes there are dup pins on a component (sometimes on diff sides!))
	private Set<Pin> toPins;
	private Set<Pin> fromPins;
	
	// the actual pins we're connected to
	protected Pin toPin;
	protected Pin fromPin;
	
	public Connection(Connectable fromComp, Collection<Pin> fromPins, Connectable toComp, Collection<Pin> toPins) {
		this.from = fromComp;
		this.to = toComp;
		
		// assign the connection to the first pin found in the collection and init our new set
		fromPin = fromPins.iterator().next();
		this.fromPins = new HashSet<Pin>(fromPins.size());
		for(Pin pin : fromPins) {
			this.fromPins.add(pin);
		}
		
		// assign the connection to the first pin found in the collection and init our new set
		toPin = toPins.iterator().next();
		this.toPins = new HashSet<Pin>(toPins.size());
		for(Pin pin : toPins) {
			this.toPins.add(pin);
		}
	}
	
	public boolean isValid() {
		return to != null && from != null && toPin != null && fromPin != null;
	}

	/**
	 * does this connection use this Component?
	 * 
	 * @param c
	 * @return
	 */
	public boolean uses(Connectable c) {
		if (to.equals(c) || from.equals(c)) {
			return true;
		}
		return false;
	}

	/**
	 * return the pin for the given component. If the component is not part of the
	 * connection return null
	 */
	public Pin getPin(Connectable other) {
		if (to.equals(other)) {
			return toPin;
		}

		if (from.equals(other)) {
			return fromPin;
		}

		return null;
	}

	/**
	 * get the other component in the connection
	 * 
	 * @param c
	 * @return
	 */
	public Connectable getOther(Connectable c) {
		if (to.equals(c)) {
			return from;
		}

		if (from.equals(c)) {
			return to;
		}

		return null;
	}
	
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("Connection [");
		b.append(from.getName());
		b.append(':');
		b.append(fromPin);
		if(fromPins.size() > 1) {
			b.append("(alt:");
			boolean first = true;
			for (Pin p : fromPins) {
				if(p.equals(fromPin)) {
					continue;
				}
				if (!first) {
					b.append(',');
				}
				b.append(p.getPinNumber());
				first = false;
			}
			b.append(')');
		}
		b.append(" -> ");
		b.append(to.getName());
		b.append(':');
		b.append(toPin);
		if(toPins.size() > 1) {
			b.append("(alt:");
			boolean first = true;
			for (Pin p : toPins) {
				if(p.equals(toPin)) {
					continue;
				}
				if (!first) {
					b.append(',');
				}
				b.append(p.getPinNumber());
				first = false;
			}
			b.append(')');
		}
		b.append(']');
		
		return b.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((fromPin == null) ? 0 : fromPin.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((toPin == null) ? 0 : toPin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connection other = (Connection) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (fromPin != other.fromPin)
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (toPin != other.toPin)
			return false;
		return true;
	}

	public Connectable getTo() {
		return to;
	}

	public Connectable getFrom() {
		return from;
	}

	public Pin getToPin() {
		return toPin;
	}

	public Pin getFromPin() {
		return fromPin;
	}

	/**
	 * @return the toPins
	 */
	public Collection<Pin> getToPins() {
		return toPins;
	}

	/**
	 * @return the fromPins
	 */
	public Collection<Pin> getFromPins() {
		return fromPins;
	}

	/**
	 * @param toPin the toPin to set
	 */
	public void setToPin(Pin toPin) {
		this.toPin = toPin;
	}

	/**
	 * @param fromPin the fromPin to set
	 */
	public void setFromPin(Pin fromPin) {
		this.fromPin = fromPin;
	}

	public void setPin(String id, Pin pin) {
		if("from".equalsIgnoreCase(id)) {
			setFromPin(pin);
		}
		if("to".equalsIgnoreCase(id)) {
			setToPin(pin);
		}
	}
	
	public Pin getPin(String id) {
		if("from".equalsIgnoreCase(id)) {
			return fromPin;
		}
		if("to".equalsIgnoreCase(id)) {
			return toPin;
		}
		return null;
	}
}
