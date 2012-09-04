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
public class FromLocated {
	
	protected static final Log log = LogFactory.getLog(FromLocated.class);
	
	private FileConnector c = new DefaultFileConnector();

	private ComponentSet makeSet() {
		URL url = FromLocated.class.getClassLoader().getResource("4inMultiLocated.xml");
		File testSet = new File(url.getPath());

		url = FromLocated.class.getClassLoader().getResource("connectables.xml");
		File defs = new File(url.getPath());
		
		return c.read(defs, testSet);
	}

	private Solver s;
	private SetOptimizer so = new RandomizingSetOptimizer(2000);

	public FromLocated() {
		initSolver();
		
		ComponentSet set = makeSet();
		
		File out = new File("best.out");
		
		// run continuously, printing out each 'best' solution
		int best = 1000;
		while (true) {
			s.solve(set);
			int size = set.getTotalSize();
			if (size < best) {
				best = size;
				log.info("found better solution : " + best);
				c.writeToFile(out, set);
			}
			//so.optimize(set);
		}
	}

	private void initSolver() {
		ConnectionOptimizer r = new ConnectionRotator();
		ConnectionOptimizer m = new ConnectionMover();
		ConnectionOptimizer pt = new ConnectionAlternatePinTrier();
		s = new MutiplePathMultiplePassConnectionSolver(Arrays.asList(r, m, pt));
	}

	public static void main(String[] args) {
		new FromLocated();
	}
}
