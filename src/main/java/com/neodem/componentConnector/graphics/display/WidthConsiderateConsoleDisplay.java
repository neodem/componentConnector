package com.neodem.componentConnector.graphics.display;

import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.model.sets.SetItem;

/**
 * 
 * 
 * 
 * @author vfumo
 * 
 */
public class WidthConsiderateConsoleDisplay extends GraphicalConsoleDisplay implements Display {

	private int width;

	public WidthConsiderateConsoleDisplay(int width) {
		this.width = width;
	}

	@Override
	protected String drawSetAsString(ComponentSet set) {

		int totalCols = set.getCols();
		int colStart = 0;

		StringBuffer b = new StringBuffer();

		do {
			int colEnd = colStart + width - 1;
			if(colEnd >= totalCols) {
				colEnd = totalCols -1;
			}
			SetItem[][] pagedComponents = getPagedComponents(set, colStart, colEnd);
			b.append(drawIntoString(pagedComponents, colStart));
			colStart += width;

			b.append("--------next page\n\n");
		} while (colStart < totalCols);

		return b.toString();
	}

	protected SetItem[][] getPagedComponents(ComponentSet set, int colStart, int colEnd) {
		int rows = set.getRows();
		int colMax = set.getCols();

		SetItem[][] allComps = getComponentsToDraw(set);
		
		SetItem[][] pagedComps = new SetItem[rows][colEnd-colStart+1];

		for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
			int col=0;
			for (int colIndex = colStart; colIndex <= colEnd; colIndex++) {
				if (colIndex < colMax) {
					SetItem item = allComps[rowIndex][colIndex];
					pagedComps[rowIndex][col++] = item;
				}
			}
		}
		return pagedComps;
	}
}
