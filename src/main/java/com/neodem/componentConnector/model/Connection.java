package com.neodem.componentConnector.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.neodem.componentConnector.model.components.BaseComponent;

/**
 * Connection to another Item
 * 
 * @author vfumo
 * 
 */
public class Connection {

	protected String toId;
	
	// pins we can connect to (sometimes there are dup pins on a component (sometimes on diff sides!))
	private Set<Pin> toPins;
	private Set<Pin> fromPins;
	
	// the actual pins we're connected to
	protected Pin toPin;
	protected Pin fromPin;
	
	public Connection(Collection<Pin> fromPins, String toId, Collection<Pin> toPins) {
		this.toId = toId;
		
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
	
	/**
	 * copy constructor
	 * 
	 * @param s
	 */
	public Connection(Connection s) {
		this.toId = new String(s.toId);
		this.toPin = new Pin(s.toPin);
		this.fromPin = new Pin(s.fromPin);
		
		this.toPins = new HashSet<Pin>(s.toPins.size());
		for(Pin p : s.toPins) {
			this.toPins.add(new Pin(p));
		}
		
		this.fromPins = new HashSet<Pin>(s.toPins.size());
		for(Pin p : s.fromPins) {
			this.fromPins.add(new Pin(p));
		}
	}

	public boolean isValid() {
		return StringUtils.isNotBlank(toId) && toPin != null && fromPin != null;
	}

	/**
	 * does this connection use this Component?
	 * 
	 * @param c
	 * @return
	 */
	public boolean uses(BaseComponent item) {
		if(toId.equals(item.getId())) return true;
		return false;
	}
	
	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("Connection [");
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
		b.append(toId);
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(55, 87).append(toId)
				.append(fromPin).append(toPin).toHashCode();
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
		Connection rhs = (Connection) obj;
		return new EqualsBuilder().append(toId, rhs.toId).append(fromPin, rhs.fromPin).append(toPin, rhs.toPin).isEquals();
	}
	

	public String getToId() {
		return toId;
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
}
