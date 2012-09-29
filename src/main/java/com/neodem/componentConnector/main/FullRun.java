package com.neodem.componentConnector.main;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.io.DefaultFileConnector;
import com.neodem.componentConnector.io.FileConnector;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.solver.MutiplePathMultiplePassConnectionSolver;
import com.neodem.componentConnector.solver.Solver;
import com.neodem.componentConnector.solver.optimizers.connection.ConectionInverter;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionAlternatePinTrier;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionMover;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;

/**
 * @author vfumo
 * 
 */
public class FullRun {
	
	protected static final Log log = LogFactory.getLog(FullRun.class);
	
	private FileConnector c;

	public FullRun() {
		c = initFileConnector();
		Solver s = getSolver();
		ComponentSet set = getInitialComponentSet();
		
		File bestFile = new File("best.xml");
		int best = set.getTotalSize();
		while (true) {
			s.solve(set);
			int size = set.getTotalSize();
			if (size < best) {
				best = size;
				log.info("found better solution : " + best);
				c.writeComponentSetToFile(set, bestFile);
				set = c.readIntoComponentSet(bestFile);
			} else {
				break;
			}
		}
	}

	private FileConnector initFileConnector() {
		ClassLoader classLoader = FullRunRandom.class.getClassLoader();

		URL url = classLoader.getResource("All-Connectables.xml");
		File defs = new File(url.getPath());
		
		return new DefaultFileConnector(defs);
	}

	private ComponentSet getInitialComponentSet() {
		ClassLoader classLoader = FullRun.class.getClassLoader();
		
		URL url = classLoader.getResource("All.xml");
		File componentsFile = new File(url.getPath());
		
		return c.readIntoComponentSet(componentsFile);
	}
	
	private Solver getSolver() {
		ConnectionOptimizer r = new ConectionInverter();
		ConnectionOptimizer m = new ConnectionMover();
		ConnectionOptimizer pt = new ConnectionAlternatePinTrier();
		return new MutiplePathMultiplePassConnectionSolver(Arrays.asList(r, m, pt));
	}

	public static void main(String[] args) {
		new FullRun();
	}
}
