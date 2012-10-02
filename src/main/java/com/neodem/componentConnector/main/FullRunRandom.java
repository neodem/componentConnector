package com.neodem.componentConnector.main;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.io.DefaultFileConnector;
import com.neodem.componentConnector.io.FileConnector;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.optimizer.SetOptimizer;
import com.neodem.componentConnector.solver.MutiplePathMultiplePassConnectionSolver;
import com.neodem.componentConnector.solver.optimizers.connection.ConectionInverter;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionAlternatePinTrier;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionMover;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;
import com.neodem.componentConnector.solver.optimizers.set.FullShiftingSetOptimizer;
import com.neodem.componentConnector.solver2.Solver;

/**
 * @author vfumo
 * 
 */
public class FullRunRandom {

	protected static final Log log = LogFactory.getLog(FullRunRandom.class);

	private FileConnector c;

	private Solver s;
	
	private ComponentSet makeSet() {
		ClassLoader classLoader = FullRunRandom.class.getClassLoader();

		URL url = classLoader.getResource("All-unLocated.xml");
		File componentsFile = new File(url.getPath());

		return c.readIntoComponentSet(componentsFile);
	}

	public FullRunRandom() {
		c = initFileConnector();
		initSolver();

		File out = new File("best.xml");

		// run continuously, printing out each 'best' solution
		ComponentSet startingSet = makeSet();
		log.info("Starting Size = " + startingSet.getTotalSize());

		SetOptimizer so = new FullShiftingSetOptimizer();
		ComponentSet set = so.optimize(startingSet);

		log.info("post Op Size = " + set.getTotalSize());

		while (s.solve(set)) {
			log.info("found better solution : " + set.getTotalSize());
			c.writeComponentSetToFile(set, out);
		}
	}

	private FileConnector initFileConnector() {
		ClassLoader classLoader = FullRunRandom.class.getClassLoader();

		URL url = classLoader.getResource("All-Connectables.xml");
		File defs = new File(url.getPath());
		
		return new DefaultFileConnector(defs);
	}
	
	private void initSolver() {
		ConnectionOptimizer r = new ConectionInverter();
		ConnectionOptimizer m = new ConnectionMover();
		ConnectionOptimizer pt = new ConnectionAlternatePinTrier();
		s = new MutiplePathMultiplePassConnectionSolver(Arrays.asList(m, r, pt));
	}

	public static void main(String[] args) {
		new FullRunRandom();
	}
}
