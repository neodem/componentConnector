package com.neodem.componentConnector.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.graphics.text.model.DefaultGraphicsPanel;
import com.neodem.graphics.text.model.DefaultGraphicsRow;
import com.neodem.graphics.text.model.GraphicsPanel;
import com.neodem.graphics.text.model.GraphicsRow;

/**
 * will simply print the set, not caring for anything like file width, etc.
 * 
 * @author vfumo
 * 
 */
public class CrudeConsoleDisplay implements Display {

	public String asString(ComponentSet set) {
		StringBuffer b = new StringBuffer();
		b.append(drawSetAsString(set));
		b.append(set.displayConnections());
		return b.toString();
	}

	/**
	 * 
	 * @param set
	 * @return a double list of Components with the outer list being the row and
	 *         the inner list being the components in that row
	 */
	protected List<List<Component>> getComponentsToDraw(ComponentSet set) {
		int sizeY = set.getSizeY();

		// get all rows of the set
		List<List<Component>> comps = new ArrayList<List<Component>>(sizeY);
		for (int rowIndex = 0; rowIndex < sizeY; rowIndex++) {
			comps.add(set.getRow(rowIndex));
		}
		return comps;
	}

	protected String drawSetAsString(ComponentSet set) {
		List<List<Component>> comps = getComponentsToDraw(set);
		return drawIntoString(comps, set);
	}

	/**
	 * 
	 * @param comps
	 * @param set
	 * @return
	 */
	protected String drawIntoString(List<List<Component>> comps, ComponentSet set) {
		GraphicsPanel p = new DefaultGraphicsPanel();
		p.setVSpacing(1);

		for (List<Component> row : comps) {
			GraphicsRow gRow = new DefaultGraphicsRow();
			for (Component c : row) {
				if (c != null) {
					Collection<Connection> cons = set.getConnectionsForComponent(c);
					GraphicalComponent gComp = new GraphicalComponent(c);
					gComp.addRelatedConnections(cons);
					gRow.add(gComp);
				}
			}
			p.addRow(gRow);
		}

		return p.asString();
	}

}
