package com.neodem.componentConnector.io;

import java.io.File;

import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * will read and write a set to a file
 * 
 * @author vfumo
 *
 */
public interface FileConnector {
	/**
	 * 
	 * @param setDef
	 * @return a filed ComponentSet
	 */
	public ComponentSet readIntoComponentSet(File setDef);
	
	/**
	 * 
	 * @param set
	 * @param file
	 */
	public void writeComponentSetToFile(ComponentSet set, File file);
}
