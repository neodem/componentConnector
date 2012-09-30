package com.neodem.componentConnector.model.components;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class BaseComponent {

	private String id;
	private String type;

	public BaseComponent(String id, String type) {
		this.id = id;
		this.type = type;
	}

	public abstract boolean isValid();

	@Override
	public int hashCode() {
		return new HashCodeBuilder(103, 99).append(id).append(type).toHashCode();
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
		return new EqualsBuilder().append(id, rhs.id).append(type, rhs.type).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Component [id=" + id + ", type=" + type + "]";
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

}
