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
import com.neodem.componentConnector.solver.optimizers.set.FullShiftingSetOptimizer;
import com.neodem.componentConnector.solver.optimizers.set.SetOptimizer;
import com.neodem.componentConnector.solver.optimizers.set.RandomizingSetOptimizer;

/**
 * @author vfumo
 * 
 */
public class FullRunRandom {

	protected static final Log log = LogFactory.getLog(FullRunRandom.class);

	private FileConnector c = new DefaultFileConnector();

	private ComponentSet makeSet() {
		ClassLoader classLoader = FullRunRandom.class.getClassLoader();

		URL url = classLoader.getResource("Full-components.xml");
		File componentsFile = new File(url.getPath());

		url = classLoader.getResource("Full-connectables.xml");
		File connectablesFile = new File(url.getPath());

		url = classLoader.getResource("Full-connections.xml");
		File connectionsFile = new File(url.getPath());

		return c.read(componentsFile, connectablesFile, connectionsFile);
	}

	private Solver s;

	public FullRunRandom() {
		initSolver();

		File out = new File("best.xml");

		// run continuously, printing out each 'best' solution
		int best = 1000;
		ComponentSet startingSet = makeSet();

		SetOptimizer so = new FullShiftingSetOptimizer();
		ComponentSet solvedSet = so.optimize(startingSet);

		while (true) {
			log.info("Starting Size = " + solvedSet.getTotalSize());
			solvedSet = s.solve(solvedSet);
			int size = solvedSet.getTotalSize();
			if (size < best) {
				best = size;
				log.info("found better solution : " + best);
				c.writeToFile(out, solvedSet);
			}
		}
	}

	private void initSolver() {
		ConnectionOptimizer r = new ConectionInverter();
		ConnectionOptimizer m = new ConnectionMover();
		ConnectionOptimizer pt = new ConnectionAlternatePinTrier();
		s = new MutiplePathMultiplePassConnectionSolver(Arrays.asList(r, m, pt));
	}

	public static void main(String[] args) {
		new FullRunRandom();
	}
}
