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
import com.neodem.componentConnector.solver.optimizers.SetOptimizer;

/**
 * @author vfumo
 * 
 */
public class FullRun {
	
	protected static final Log log = LogFactory.getLog(FullRun.class);
	
	private FileConnector c = new DefaultFileConnector();

	private ComponentSet makeSet(File componentsFile) {
		ClassLoader classLoader = FullRun.class.getClassLoader();
		
		URL url = classLoader.getResource("Full-connectables.xml");
		File connectablesFile = new File(url.getPath());

		url = classLoader.getResource("Full-connections.xml");
		File connectionsFile = new File(url.getPath());

		return c.read(componentsFile, connectablesFile, connectionsFile);
	}

	private Solver s;
	private SetOptimizer so = new RandomizingSetOptimizer(8000);

	public FullRun() {
		initSolver();
		
		ClassLoader classLoader = FullRun.class.getClassLoader();
		
		URL url = classLoader.getResource("Full-components.xml");
		File componentsFile = new File(url.getPath());
		
		ComponentSet set = makeSet(componentsFile);
		
		componentsFile = new File("best.xml");
		int best = 1000;
		while (true) {
			s.solve(set);
			int size = set.getTotalSize();
			if (size < best) {
				best = size;
				log.info("found better solution : " + best);
				c.writeToFile(componentsFile, set);
				set = makeSet(componentsFile);
			}
			so.optimize(set);
		}
	}

	private void initSolver() {
		ConnectionOptimizer r = new ConnectionRotator();
		ConnectionOptimizer m = new ConnectionMover();
		ConnectionOptimizer pt = new ConnectionAlternatePinTrier();
		s = new MutiplePathMultiplePassConnectionSolver(Arrays.asList(r, m, pt));
	}

	public static void main(String[] args) {
		new FullRun();
	}
}
