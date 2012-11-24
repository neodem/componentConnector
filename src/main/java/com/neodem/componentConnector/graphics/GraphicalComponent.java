package com.neodem.componentConnector.graphics;

import static com.neodem.componentConnector.model.Side.Left;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neodem.common.utility.text.Chars;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.graphics.text.model.ConsoleTools;
import com.neodem.graphics.text.model.GraphicsObject;
import com.neodem.graphics.text.util.DisplayHelper;

/**
 * made up of a left side and right side (to hold connections) and a middle
 * (component) section
 * 
 * @author vfumo
 * 
 */
public class GraphicalComponent implements GraphicsObject {

	private static final int DEFAULT_HEIGHT = 8;
	private static final int COMPONENT_WIDTH = 14;
	private static final int SIDE_WIDTH = 15;

	protected int height = DEFAULT_HEIGHT;
	protected int width = COMPONENT_WIDTH;

	protected int pinsPerSide = 0;
	protected boolean inverted = false;

	private List<String> lines = new ArrayList<String>(height);
	protected BaseComponent parent;
	private Map<Integer, GraphicalConnection> leftConnections = new HashMap<Integer, GraphicalConnection>();
	private Map<Integer, GraphicalConnection> rightConnections = new HashMap<Integer, GraphicalConnection>();
	private String componentName;
	private Location componentLocation;

	protected GraphicalComponent(String name, Location location, BaseComponent parent, int pinsPerSide, int height,
			boolean inverted, Collection<Connection> toCons) {
		this.componentName = name;
		this.componentLocation = location;
		this.parent = parent;
		this.pinsPerSide = pinsPerSide;
		this.height = height;
		this.inverted = inverted;
		addRelatedConnections(toCons);
		computeLines();
	}

	public GraphicalComponent(String name, Location location, BaseComponent c, boolean inverted,
			Collection<Connection> cons) {
		this(name, location, c, c.getNumberofPins() / 2, (c.getNumberofPins() / 2) + 2, inverted, cons);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neodem.graphics.model.GraphicsObject#getLine(int)
	 */
	public String getLine(int lineIndex) {
		return lines.get(lineIndex);
	}

	public String getAsString() {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < height; i++) {
			b.append(getLine(i));
			b.append(Chars.NEWLINE);
		}
		return b.toString();
	}

	public boolean isValid() {
		return true;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	protected void addRelatedConnections(Collection<Connection> cons) {
		if (cons != null) {
			for (Connection c : cons) {
				addRelatedConnection(c);
			}
		}
	}
	
	protected void addRelatedConnection(Connection c) {
		Pin fromPin = c.getFromPin();

		// determine connection index
		int index = parent.determineSideIndex(inverted, fromPin);

		// determine connection side
		Side connectionSide = parent.getSideForPin(inverted, fromPin);

		GraphicalConnection con = null;
		if (connectionSide == Left) {
			con = new GraphicalLeftSideConnection(c, false);
			if (inverted) {
				con.invert();
			}
			leftConnections.put(index, con);
		} else {
			con = new GraphicalRightSideConnection(c, false);
			if (inverted) {
				con.invert();
			}
			rightConnections.put(index, con);
		}
	}

	/**
	 * will have a top line and bottom line. The rest are middle lines. The info
	 * starts at middle line 2
	 */
	protected void computeLines() {

		// determine the width
		width = COMPONENT_WIDTH + (2 * SIDE_WIDTH);

		// generate lines
		String line = "";
		for (int lineIndex = 0; lineIndex < height; lineIndex++) {
			// --- make the line
			line = makeLine(lineIndex);
			// add line
			lines.add(line);
		}
	}

	protected String makeLine(int lineIndex) {
		String line;
		// generate top or bottom
		if (lineIndex == 0 || lineIndex == (height - 1)) {
			line = ConsoleTools.spaces(SIDE_WIDTH) + ConsoleTools.chars(COMPONENT_WIDTH, '-')
					+ ConsoleTools.spaces(SIDE_WIDTH);
		} else {
			// this is a middle line
			int middleIndex = lineIndex - 1;

			// make the text to display (if any)
			String middleText = makeMiddleText(middleIndex);

			String middleContent = makeMiddleContent(middleIndex, middleText);
			String leftContent = makeLeftContent(middleIndex);
			String rightContent = makeRightContent(middleIndex);

			line = leftContent + middleContent + rightContent;
		}
		return line;
	}

	private String makeMiddleText(int middleIndex) {
		String middleText = null;
		if (middleIndex == 1) {
			middleText = componentName;
		} else if (middleIndex == 2) {
			middleText = makeInfoString();
		} else if (middleIndex == 3) {
			middleText = componentLocation.toString();
		}
		return middleText;
	}

	/**
	 * make a string with spacing and the correct pin numbers :
	 * 
	 * |12 content 1|
	 * 
	 * @param middleLineCounter
	 * @return
	 */
	protected String makeMiddleContent(int middleIndex, String content) {
		String leftSide = "|" + getLeftPinNumber(middleIndex) + " ";
		String rightSide = " " + getRightPinNumber(middleIndex) + "|";

		int spaceAvailable = COMPONENT_WIDTH - leftSide.length() - rightSide.length();
		if (spaceAvailable < 0) {
			throw new RuntimeException("um.. ");
		}

		String middle = ConsoleTools.centerInto(content, spaceAvailable, ' ');

		return leftSide + middle + rightSide;
	}

	protected String makeRightContent(int lineIndex) {
		String rightContent = "";
		GraphicalConnection con = rightConnections.get(lineIndex);
		if (con != null) {
			// make it
			rightContent = con.getAsString();
		}
		rightContent = DisplayHelper.fitIntoLeft(rightContent, SIDE_WIDTH);
		return rightContent;
	}

	protected String makeLeftContent(int lineIndex) {
		String leftContent = "";
		GraphicalConnection con = leftConnections.get(lineIndex);
		if (con != null) {
			// make it
			leftContent = con.getAsString();
		}
		leftContent = DisplayHelper.fitIntoRight(leftContent, SIDE_WIDTH);
		return leftContent;
	}

	private String getRightPinNumber(int middleIndex) {
		if (inverted) {
			return "" + ((pinsPerSide * 2) - middleIndex);
		}
		return "" + (pinsPerSide - middleIndex);
	}

	private String getLeftPinNumber(int middleIndex) {
		if (inverted) {
			return "" + (middleIndex + 1);
		}
		return "" + (pinsPerSide + middleIndex + 1);
	}

	private String makeInfoString() {
		StringBuffer info = new StringBuffer();
		if (inverted) {
			info.append("I");
		}

		return info.toString();
	}

}
