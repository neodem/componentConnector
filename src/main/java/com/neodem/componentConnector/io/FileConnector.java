package com.neodem.componentConnector.io;

import java.io.File;

import com.neodem.componentConnector.model.sets.ComponentSet;

public interface FileConnector {
	/**
	 * 
	 * @param componentDefs
	 * @param set
	 * 
	 * @return
	 */
	public ComponentSet read(File componentDefs, File set);
	public void writeToFile(File file, ComponentSet set);
}
