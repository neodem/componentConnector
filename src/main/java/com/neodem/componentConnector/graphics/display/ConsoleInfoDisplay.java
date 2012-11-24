package com.neodem.componentConnector.graphics.display;

import java.util.Collection;

import com.neodem.common.utility.text.Chars;
import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * @author vfumo
 * 
 */
public class ConsoleInfoDisplay implements Display {

	public void displaySet(ComponentSet set) {
		System.out.println(asString(set));
	}

	public String asString(ComponentSet set) {
		Collection<BaseComponent> allComponents = set.getAllComponents();

		StringBuffer b = new StringBuffer();
		for (BaseComponent c : allComponents) {
			b.append(c.display());
			b.append(Chars.NEWLINE);
		}

		return b.toString();
	}
}
