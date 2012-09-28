package com.neodem.componentConnector.graphics;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class CrudeConsoleDisplayTest extends AbstractBaseRelayLocatorTest {

	
	@Test
	public void getComponentsToDrawShouldWorkCorrectly() {
		ComponentSet set = new ComponentSet(3, 3);
		Component r1 = relayFactory.make("1", 1, 2);
		set.addComponent(r1);
		Component r2 = relayFactory.make("2", 2, 1);
		set.addComponent(r2);
		Component r3 = relayFactory.make("3", 1, 1);
		set.addComponent(r3);
		Component r4 = relayFactory.make("4", 1, 0);
		set.addComponent(r4);

		CrudeConsoleDisplay c = new CrudeConsoleDisplay();
		
		List<List<Component>> result = c.getComponentsToDraw(set);
		assertThat(result.size(), is(3));

		List<Component> row0 = result.get(0);
		List<Component> row1 = result.get(1);
		List<Component> row2 = result.get(2);

		assertThat(row0.size(), is(1));
		assertThat(row1.size(), is(2));
		assertThat(row2.size(), is(1));

		assertThat((Component) row0.get(0), equalTo(r4));
		assertThat((Component) row1.get(0), equalTo(r3));
		assertThat((Component) row1.get(1), equalTo(r2));
		assertThat((Component) row2.get(0), equalTo(r1));
	}

}
