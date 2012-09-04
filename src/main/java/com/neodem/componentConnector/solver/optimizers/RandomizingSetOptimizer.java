package com.neodem.componentConnector.solver.optimizers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.neodem.componentConnector.model.Connectable;
import com.neodem.componentConnector.model.Connection;
import com.neodem.componentConnector.model.component.Component;
import com.neodem.componentConnector.model.sets.AutoAddComponentSet;
import com.neodem.componentConnector.model.sets.ComponentSet;

/**
 * taking some time to get a good starting set for optimization is 
 * totally worth it. This class can get a set to a nice state after 
 * 20k runs or so..
 * 
 * TODO : does not respect the Relay.movable flag
 * 
 * @author vfumo
 *
 */
public class RandomizingSetOptimizer implements SetOptimizer {

	private Random random = new Random();
	private int numRuns;

	public RandomizingSetOptimizer(int runs) {
		this.numRuns = runs;
	}
	
	/* 
	 * this will run 'numRuns' times and just randomly shift around the relays and rotate
	 * them and return the run that was the best. If there were none better than 
	 * the given one, that one is returned
	 */
	public void optimize(ComponentSet input) {
		ComponentSet keeper = input;
		int keeperSize = keeper.getTotalSize();
		for (int i = 0; i < numRuns; i++) {
			ComponentSet randomSet = randomSetOptimize(input, keeperSize);
			int randomSize = randomSet.getTotalSize();

			if (randomSize < keeperSize) {
				keeper = randomSet;
				keeperSize = randomSize;
			}
		}

		input.copyRelayStates(keeper);
	}

	/*
	 * will keep running until we have no more positive gain
	 */
	private ComponentSet randomSetOptimize(ComponentSet set, int previousBest) {
		Collection<Component> originalComponents = set.getAllComponents();
		Collection<Connection> originalConnections = set.getAllConnections();

		List<Component> sources = new ArrayList<Component>(originalComponents);
		
		AutoAddComponentSet newSet = new AutoAddComponentSet(set);

		// add relays with the same names to the new set
		Collections.shuffle(sources);
		for (Component r : sources) {
			newSet.addComponentAtRandomLocation(r);

			if (random.nextBoolean()) {
				r.invert();
			}
		}

		// add connections to the new set
		for (Connection c : originalConnections) {
			Connectable oFrom = c.getFrom();
			Connectable oTo = c.getTo();

			Component from = newSet.getComponent(oFrom.getName());
			Component to = newSet.getComponent(oTo.getName());
			
			newSet.addConnection(new Connection(from, c.getFromPins(), to, c.getToPins()));
		}

		// now we have a new set with relays in new random locations and
		// rotations
		// is it better than the best?

		int size = newSet.getTotalSize();

		if (size < previousBest) {
			randomSetOptimize(newSet, size);
		}

		return newSet;
	}

}
