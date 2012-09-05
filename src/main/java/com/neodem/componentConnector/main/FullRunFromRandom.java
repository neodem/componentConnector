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
import com.neodem.componentConnector.solver.optimizers.ConnectionAlternatePinTrier;
import com.neodem.componentConnector.solver.optimizers.ConnectionMover;
import com.neodem.componentConnector.solver.optimizers.ConnectionOptimizer;
import com.neodem.componentConnector.solver.optimizers.ConnectionRotator;
import com.neodem.componentConnector.solver.optimizers.RandomizingSetOptimizer;

/**
 * @author vfumo
 * 
 */
public class FullRunFromRandom {
	
	protected static final Log log = LogFactory.getLog(FullRunFromRandom.class);
	
	private FileConnector c = new DefaultFileConnector();

	private ComponentSet makeSet() {	
		ClassLoader classLoader = FullRunFromRandom.class.getClassLoader();
		
		URL url = classLoader.getResource("Full-components.xml");
		File componentsFile = new File(url.getPath());

		url = classLoader.getResource("Full-connectables.xml");
		File connectablesFile = new File(url.getPath());

		url = classLoader.getResource("Full-connections.xml");
		File connectionsFile = new File(url.getPath());

		return c.read(componentsFile, connectablesFile, connectionsFile);
	}

	private Solver s;

	public FullRunFromRandom() {
		initSolver();
		
		File out = new File("best.out");
		
		// run continuously, printing out each 'best' solution
		int best = 1000;
		while (true) {
			ComponentSet set = makeSet();
			s.solve(set);
			int size = set.getTotalSize();
			if (size < best) {
				best = size;
				log.info("found better solution : " + best);
				c.writeToFile(out, set);
			}
		}
	}

	private void initSolver() {
		ConnectionOptimizer r = new ConnectionRotator();
		ConnectionOptimizer m = new ConnectionMover();
		ConnectionOptimizer pt = new ConnectionAlternatePinTrier();
		s = new MutiplePathMultiplePassConnectionSolver(Arrays.asList(r, m, pt), new RandomizingSetOptimizer(2000));
	}

	public static void main(String[] args) {
		new FullRunFromRandom();
	}
}
