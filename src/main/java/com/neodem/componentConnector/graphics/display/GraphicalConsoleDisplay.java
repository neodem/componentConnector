package com.neodem.componentConnector.graphics.display;

import java.util.Map;

import com.neodem.common.utility.text.Chars;
import com.neodem.componentConnector.graphics.BlankComponent;
import com.neodem.componentConnector.graphics.GraphicalComponent;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.model.sets.SetItem;
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
public class GraphicalConsoleDisplay extends ConsoleInfoDisplay implements Display {

	private int rows;
	private int cols;

	public String asString(ComponentSet set) {
		StringBuffer b = new StringBuffer();
		b.append(drawSetAsString(set));
		b.append(Chars.NEWLINE);
		b.append(super.asString(set));
		return b.toString();
	}

	protected String drawSetAsString(ComponentSet set) {
		SetItem[][] comps = getComponentsToDraw(set);
		return drawIntoString(comps, 0);
	}

	/**
	 * 
	 * @param set
	 * @return a two dimensional array of SetItems with the first index being
	 *         the row and the second being the column
	 */
	protected SetItem[][] getComponentsToDraw(ComponentSet set) {
		rows = set.getRows();
		cols = set.getCols();

		SetItem[][] items = new SetItem[rows][cols];

		Map<String, SetItem> itemMap = set.getItems();
		for (SetItem item : itemMap.values()) {
			Location loc = item.getItemLocation();
			int row = loc.getRow();
			int col = loc.getCol();
			items[row][col] = item;
		}

		return items;
	}

	/**
	 * 
	 * @param comps
	 *            [row][col]
	 * @param set
	 * @param colOffset
	 * @return
	 */
	protected String drawIntoString(SetItem[][] comps, int colOffset) {
		GraphicsPanel p = new DefaultGraphicsPanel();
		p.setVSpacing(1);

		for (int rowIndex = 0; rowIndex < comps.length; rowIndex++) {
			GraphicsRow gRow = new DefaultGraphicsRow();
			int colNum = colOffset;
			for (int colIndex = 0; colIndex < comps[rowIndex].length; colIndex++) {
				SetItem c = comps[rowIndex][colIndex];
				GraphicalComponent gComp;
				if (c == null) {
					gComp = new BlankComponent(colNum, rowIndex);
				} else {
					BaseComponent bc = c.getItem();
					gComp = new GraphicalComponent(bc.getId(), new Location(rowIndex, colNum), bc, c.getInverted(),
							bc.getConnections());
				}
				gRow.add(gComp);
				colNum++;
			}
			p.addRow(gRow);
		}

		return p.asString();
	}

}
