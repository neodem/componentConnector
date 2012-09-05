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
	 * @param componentsDef
	 * @param connectablesDef
	 * @param connectionsDef
	 * 
	 * @return a filed ComponentSet
	 */
	public ComponentSet read(File componentsDef, File connectablesDef, File connectionsDef);
	
	/**
	 * 
	 * @param file
	 * @param set
	 */
	public void writeToFile(File file, ComponentSet set);
}
