package com.neodem.componentConnector.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.neodem.componentConnector.graphics.Display;
import com.neodem.componentConnector.graphics.WidthConsiderateConsoleDisplay;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class PrettyPrintOutputFileConnector extends DefaultFileConnector implements FileConnector {

	@Override
	public void writeToFile(File file, ComponentSet set) {
		Display d = new WidthConsiderateConsoleDisplay(2);
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(file));
			out.write(d.asString(set));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
