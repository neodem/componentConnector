package com.neodem.componentConnector.optimizer.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.componentConnector.model.sets.AutoAddComponentSet;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.tools.Calculator;

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
	
	protected static final Calculator calc = new Calculator();
	
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
		int bestSize = calc.calculateSetScore(input);
		ComponentSet best = input;
		log.debug("start : " + bestSize);
		for (int i = 0; i < numRuns; i++) {
			ComponentSet randomSet = createNewRandomSet(input);
			int randomSize = calc.calculateSetScore(randomSet);
			
			if (randomSize < bestSize) {
				best = randomSet;
				bestSize = randomSize;
				log.debug("improved : " + bestSize);
			}
		}
		return best;
	}

	private ComponentSet createNewRandomSet(final ComponentSet set) {
		Collection<BaseComponent> allComponents = set.getAllComponents();

		List<BaseComponent> sourceItems = new ArrayList<BaseComponent>(allComponents.size());
		sourceItems.addAll(allComponents);

		// create a new AACS with the same dimensions
		AutoAddComponentSet newSet = new AutoAddComponentSet(set);
		
		// add components with the same names to the new set
		Collections.shuffle(sourceItems);
		for (BaseComponent r : sourceItems) {
			BaseComponent component = new BaseComponent(r);
			
			newSet.addItem(component);
			
			if (random.nextBoolean()) {
				newSet.invertItem(component);
			}
		}
		
		return newSet;
	}

}
