package com.neodem.componentConnector.main;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.io.DefaultFileConnector;
import com.neodem.componentConnector.io.FileConnector;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.optimizer.set.FullShiftingSetOptimizer;
import com.neodem.componentConnector.optimizer.set.SetOptimizer;
import com.neodem.componentConnector.solver.MutiplePathMultiplePassConnectionSolver;
import com.neodem.componentConnector.solver.optimizers.connection.ConectionInverter;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionAlternatePinTrier;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionMover;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;
import com.neodem.componentConnector.solver2.Solver;
import com.neodem.componentConnector.tools.Calculator;

/**
 * @author vfumo
 * 
 */
public class FullRunRandom {
	protected static final Log log = LogFactory.getLog(FullRunRandom.class);

	private FileConnector fc;
	
	private static final Calculator c = new Calculator();

	public FullRunRandom() {
		fc = initFileConnector();
		Solver s = initSolver();
		ComponentSet startingSet = getInitialComponentSet();

		File out = new File("best.xml");

		// run continuously, printing out each 'best' solution
		log.info("Starting Score = " + c.calculateSetScore(startingSet));

		SetOptimizer so = new FullShiftingSetOptimizer();
		ComponentSet set = so.optimize(startingSet);

		log.info("post Op Size = " + c.calculateSetScore(set));

		while (s.solve(set)) {
			log.info("found better solution : " + c.calculateSetScore(set));
			fc.writeComponentSetToFile(set, out);
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

		URL url = classLoader.getResource("All-unLocated.xml");
		File componentSetFile = new File(url.getPath());

		return fc.readIntoComponentSet(componentSetFile);
	}

	private Solver initSolver() {
//		ConnectionOptimizer r = new ConectionInverter();
//		ConnectionOptimizer m = new ConnectionMover();
//		ConnectionOptimizer pt = new ConnectionAlternatePinTrier();
//		return new MutiplePathMultiplePassConnectionSolver(Arrays.asList(m, r, pt));
		return null;
	}

	public static void main(String[] args) {
		new FullRunRandom();
	}
}
