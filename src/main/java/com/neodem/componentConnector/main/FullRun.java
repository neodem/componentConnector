package com.neodem.componentConnector.main;

import java.io.File;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.io.DefaultFileConnector;
import com.neodem.componentConnector.io.FileConnector;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.optimizer.RandomizingSetOptimizer;
import com.neodem.componentConnector.solver.MutiplePathMultiplePassConnectionSolver;
import com.neodem.componentConnector.solver.optimizers.connection.ConectionInverter;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionAlternatePinTrier;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionMover;
import com.neodem.componentConnector.solver.optimizers.connection.ConnectionOptimizer;
import com.neodem.componentConnector.solver2.JustOneOptimizerSolver;
import com.neodem.componentConnector.solver2.Solver;
import com.neodem.componentConnector.tools.Calculator;

/**
 * @author vfumo
 * 
 */
public class FullRun {

	protected static final Log log = LogFactory.getLog(FullRun.class);

	private FileConnector fc;
	
	private Calculator c = new Calculator();

	public FullRun() {
		fc = initFileConnector();
		Solver s = getSolver();
		ComponentSet set = getInitialComponentSet();

		File bestFile = new File("best.xml");
		int best = c.calculateSetScore(set);
		while (true) {
			s.solve(set);
			int size = c.calculateSetScore(set);
			if (size >= best) {
				break;
			}
			best = size;
			log.info("found better solution : " + best);
			fc.writeComponentSetToFile(set, bestFile);
			set = fc.readIntoComponentSet(bestFile);
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
		File componentSetFile = new File(url.getPath());

		return fc.readIntoComponentSet(componentSetFile);
	}

	private Solver getSolver() {
		return new JustOneOptimizerSolver(new RandomizingSetOptimizer(10000));
		
//		ConnectionOptimizer r = new ConectionInverter();
//		ConnectionOptimizer m = new ConnectionMover();
//		ConnectionOptimizer pt = new ConnectionAlternatePinTrier();
//		return new MutiplePathMultiplePassConnectionSolver(Arrays.asList(r, m, pt));
	}

	public static void main(String[] args) {
		new FullRun();
	}
}
