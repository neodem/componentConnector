package com.neodem.componentConnector.graphics;

import java.util.ArrayList;
import java.util.List;

import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * 
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
	protected String drawSetAsString(ComponentSet set) {

		int totalCols = set.getNumColumns();
		int colStart = 0;

		StringBuffer b = new StringBuffer();

		do {
			int colEnd = colStart + width - 1;
			List<List<Component>> pagedComponents = getPagedComponents(set, colStart, colEnd);
			b.append(drawIntoString(pagedComponents, set));
			colStart += width;
			
			b.append("--------next page\n\n");
		} while (colStart < totalCols);

		return b.toString();
	}

	protected List<List<Component>> getPagedComponents(ComponentSet set, int colStart, int colEnd) {
		int rows = set.getSizeY();
		List<List<Component>> comps = new ArrayList<List<Component>>(rows);
		for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
			List<Component> row = set.getRow(rowIndex);
			List<Component> windowedRow = windowRow(colStart, colEnd, row);
			if (windowedRow != null) {
				comps.add(windowedRow);
			}
		}
		return comps;
	}

	/**
	 * for a given row, return a new list of just the cols we specify (if they
	 * exist in the row)
	 * 
	 * @param colStart
	 * @param colEnd
	 * @param row
	 * @return
	 */
	protected List<Component> windowRow(int colStart, int colEnd, List<Component> row) {
		if (row == null) {
			return null;
		}

		List<Component> windowedRow = new ArrayList<Component>();
		for (int i = colStart; i < colEnd+1; i++) {
			Component c = null;
			try {
				c = row.get(i);
				windowedRow.add(c);
			} catch (IndexOutOfBoundsException e) {
				windowedRow.add(null);
			}
		}

		return windowedRow;
	}
}
