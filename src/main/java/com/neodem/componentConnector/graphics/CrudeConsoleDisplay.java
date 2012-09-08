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

	public void displaySet(ComponentSet set) {
		System.out.println(asString(set));
	}

	protected String drawPanel(ComponentSet set) {
		GraphicsPanel p = new DefaultGraphicsPanel();
		p.setVSpacing(1);
		
		int sizeY = set.getSizeY();
		
		List<List<Component>> comps = getAllRows(set, sizeY);

		for (List<Component> row : comps) {
			GraphicsRow gRow = new DefaultGraphicsRow();
			for (Component c : row) {
				Collection<Connection> cons = set.getConnectionsForComponent(c);
					GraphicalComponent gComp = new GraphicalComponent(c);
					gComp.addRelatedConnections(cons);
					gRow.add(gComp);
				}
			p.addRow(gRow);
		}

		return p.asString();
	}

	protected List<List<Component>> getAllRows(ComponentSet set, int sizeY) {
		List<List<Component>> comps = new ArrayList<List<Component>>(sizeY);
		for (int rowIndex = 0; rowIndex < sizeY; rowIndex++) {
			comps.add(set.getRow(rowIndex));
		}
		return comps;
	}

	public String asString(ComponentSet set) {
		StringBuffer b = new StringBuffer();
		b.append(drawPanel(set));
		b.append(set.displayConnections());
		return b.toString();
	}

}
