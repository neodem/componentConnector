package com.neodem.componentConnector.solver.optimizers.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.sets.AutoAddComponentSet;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * Will attempt use purely random location changes of the components (and
 * inversions) to find a better set.
 * 
 * taking some time to get a good starting set for optimization is totally worth
 * it. This class can get a set to a nice state after 20k runs or so..
 * 
 * @author vfumo
 * 
 */
public class RandomizingSetOptimizer implements SetOptimizer {
	private static final Log log = LogFactory.getLog(RandomizingSetOptimizer.class);
	
	private Random random = new Random();
	private int numRuns;

	public RandomizingSetOptimizer(int runs) {
		this.numRuns = runs;
	}

	/*
	 * this will run 'numRuns' times and just randomly shift around the relays
	 * and rotate them and return the run that was the best. If there were none
	 * better than the given one, that one is returned
	 */
	public ComponentSet optimize(final ComponentSet input) {
		ComponentSet best = input;
		int bestSize = best.getTotalSize();
		log.debug("start : " + bestSize);
		for (int i = 0; i < numRuns; i++) {
			ComponentSet randomSet = createNewRandomSet(input);
			int randomSize = randomSet.getTotalSize();
			
			if (randomSize < bestSize) {
				best = randomSet;
				bestSize = randomSize;
				log.debug("improved : " + bestSize);
			}
		}
		return best;
	}

	private ComponentSet createNewRandomSet(final ComponentSet set) {
		Collection<Component> originalComponents = set.getAllComponents();
		Collection<Connection> originalConnections = set.getAllConnections();

		List<Component> sources = new ArrayList<Component>();
		sources.addAll(originalComponents);

		// create a new AACS with the same dimensions
		AutoAddComponentSet newSet = new AutoAddComponentSet(set);
		
		// add components with the same names to the new set
		Collections.shuffle(sources);
		for (Component r : sources) {
			Component component = new Component(r);
			
			if (random.nextBoolean()) {
				component.invert();
			}
			
			newSet.addComponent(component);
		}
		
		// add connections to the new set
		for (Connection c : originalConnections) {
			String fromName = c.getFrom().getName();
			String toName = c.getTo().getName();
			
			Connectable from = newSet.getConnectable(fromName);
			Connectable to = newSet.getConnectable(toName);

			newSet.addConnection(new Connection(from, c.getFromPins(), to, c.getToPins()));
		}

		return newSet;
	}

}
