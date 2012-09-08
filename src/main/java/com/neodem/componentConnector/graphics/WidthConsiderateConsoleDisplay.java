package com.neodem.componentConnector.graphics;

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
 * TODO : does not work yet.. 
 * 
 * @author vfumo
 * 
 */
public class WidthConsiderateConsoleDisplay extends CrudeConsoleDisplay implements Display {
	
	private int width;

	public WidthConsiderateConsoleDisplay(int width) {
		this.width = width;
	}

	@Override
	protected String drawPanel(ComponentSet set) {
		GraphicsPanel p = new DefaultGraphicsPanel();
		p.setVSpacing(1);

		List<List<Component>> relays = set.getAllRows();
		for (List<Component> row : relays) {
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
}
