package com.neodem.componentConnector.model.sets;

import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.components.BaseComponent;

public class SetItem {
	
	private BaseComponent item;
	private Location itemLocation;
	private Boolean Inverted;
	
	public SetItem(BaseComponent item, Location itemLocation, Boolean inverted) {
		super();
		this.item = item;
		this.itemLocation = itemLocation;
		Inverted = inverted;
	}

	@Override
	public int hashCode() {
		final int prime = 91;
		int result = 1;
		result = prime * result + ((Inverted == null) ? 0 : Inverted.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((itemLocation == null) ? 0 : itemLocation.hashCode());
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
		SetItem other = (SetItem) obj;
		if (Inverted == null) {
			if (other.Inverted != null)
				return false;
		} else if (!Inverted.equals(other.Inverted))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (itemLocation == null) {
			if (other.itemLocation != null)
				return false;
		} else if (!itemLocation.equals(other.itemLocation))
			return false;
		return true;
	}

	public BaseComponent getItem() {
		return item;
	}

	public Location getItemLocation() {
		return itemLocation;
	}

	public Boolean getInverted() {
		return Inverted;
	}
}
