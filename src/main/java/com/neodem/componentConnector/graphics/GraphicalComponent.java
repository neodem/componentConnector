package com.neodem.componentConnector.graphics;

import static com.neodem.componentConnector.model.Side.Left;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.Side;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.tools.ComponentTools;
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

	private int height = DEFAULT_HEIGHT;
	private int width = COMPONENT_WIDTH;

	private int pinsPerSide = 0;
	boolean inverted = false;

	private List<String> lines = new ArrayList<String>(height);
	private Component parent;
	private Map<Integer, GraphicalConnection> leftConnections = new HashMap<Integer, GraphicalConnection>();
	private Map<Integer, GraphicalConnection> rightConnections = new HashMap<Integer, GraphicalConnection>();

	public GraphicalComponent(Component c) {
		parent = c;
		pinsPerSide = c.getNumberofPins() / 2;
		height = pinsPerSide + 2;
		inverted = c.isInverted();
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

			String middleText = null;
			if (middleIndex == 1) {
				middleText = parent.getName();
			} else if (middleIndex == 2) {
				middleText = makeInfoString(parent);
			}

			String middleContent = makeMiddleContent(middleIndex, middleText);
			String leftContent = makeLeftContent(middleIndex);
			String rightContent = makeRightContent(middleIndex);

			line = leftContent + middleContent + rightContent;
		}
		return line;
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

	public void addRelatedConnection(Connection c) {
		Pin pin = c.getPin(parent);

		// determine connection index
		int index = ComponentTools.determineSideIndex(parent, pin);

		// determine connection side
		Side connectionSide = ComponentTools.getSideForPin(parent, pin);

		GraphicalConnection con = null;
		if (connectionSide == Left) {
			con = new GraphicalLeftSideConnection(c, parent);
			if (inverted) {
				con.invert();
			}
			leftConnections.put(index, con);
		} else {
			con = new GraphicalRightSideConnection(c, parent);
			if (inverted) {
				con.invert();
			}
			rightConnections.put(index, con);
		}
	}

	public void addRelatedConnections(Collection<Connection> cons) {
		for (Connection c : cons) {
			addRelatedConnection(c);
		}
	}

	private String makeInfoString(Component c) {
		StringBuffer info = new StringBuffer();
		if (c.isInverted()) {
			info.append("I");
		}

		// / F == fixed (non-mobile)
		if (!c.isMoveable()) {
			info.append("F");
		}
		return info.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neodem.graphics.model.GraphicsObject#isValid()
	 */
	public boolean isValid() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neodem.graphics.model.GraphicsObject#getHeight()
	 */
	public int getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.neodem.graphics.model.GraphicsObject#getLine(int)
	 */
	public String getLine(int lineIndex) {
		computeLines();
		return lines.get(lineIndex);
	}

	public String getAsString() {
		computeLines();
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < height; i++) {
			b.append(getLine(i));
			b.append('\n');
		}
		return b.toString();
	}

	public int getWidth() {
		return width;
	}

}
