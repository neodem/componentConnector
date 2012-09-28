package com.neodem.componentConnector.graphics;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.Component;
import com.neodem.componentConnector.model.sets.ComponentSet;

public class WidthConsiderateConsoleDisplayTest extends AbstractBaseRelayLocatorTest {

	private WidthConsiderateConsoleDisplay c;
	
	@Before
	public void before() {
		c = new WidthConsiderateConsoleDisplay(2);
	}
	
	@After
	public void after() {
		c = null;
	}
	
	@Test
	public void asStringShouldWorkAsIntended() {
		
		// 3 cols, one row
		ComponentSet set = new ComponentSet(3, 3);
		set.addComponent(relayFactory.make("1", 0, 0));
		set.addComponent(relayFactory.make("2", 1, 0));
		set.addComponent(relayFactory.make("3", 2, 0));
		set.addComponent(relayFactory.make("4", 0, 1));
		set.addComponent(relayFactory.make("5", 1, 1));
		set.addComponent(relayFactory.make("6", 2, 1));
		
		System.out.println(c.asString(set));
	}
	
	@Test
	public void getPagedComponentsShouldWorkAsIntended() {
		
		// 3 cols, one row
		ComponentSet set = new ComponentSet(3, 3);
		Component r1 = relayFactory.make("1", 0, 0);
		set.addComponent(r1);
		Component r2 = relayFactory.make("2", 1, 0);
		set.addComponent(r2);
		Component r3 = relayFactory.make("3", 2, 0);
		set.addComponent(r3);

		List<List<Component>> result = c.getPagedComponents(set, 0, 1);
		// check rows
		assertThat(result.size(), is(3));

		List<Component> row = result.get(0);

		assertThat(row.size(), is(2));

		assertThat((Component) row.get(0), equalTo(r1));
		assertThat((Component) row.get(1), equalTo(r2));
		
		c.asString(set);
	}

	@Test
	public void windowRowShouldTrimTheRow(){
		List<Component> row = new ArrayList<Component>();
		Component r1 = relayFactory.make("1", 0, 0);
		Component r2 = relayFactory.make("2", 1, 0);
		Component r3 = relayFactory.make("3", 2, 0);
		row.add(r1);
		row.add(r2);
		row.add(r3);
		
		List<Component> result = c.windowRow(0, 1, row);
		assertThat(result.size(), is(2));
		assertThat((Component) result.get(0), equalTo(r1));
		assertThat((Component) result.get(1), equalTo(r2));
	}
	
	@Test
	public void windowRowShouldReturnANullFilledListListOnAnEmptyRow(){
		List<Component> row = Collections.emptyList();
		
		List<Component> result = c.windowRow(0, 1, row);
		assertThat(result.size(), is(2));
		assertThat((Component) result.get(0), nullValue());
		assertThat((Component) result.get(1), nullValue());
	}
	
	@Test
	public void windowRowShouldReturnAShortListOnAShortRow(){
		List<Component> row = new ArrayList<Component>();
		Component r1 = relayFactory.make("1", 0, 0);
		row.add(r1);
		
		List<Component> result = c.windowRow(0, 1, row);
		assertThat(result.size(), is(2));
		assertThat((Component) result.get(0), equalTo(r1));
		assertThat((Component) result.get(1), nullValue());
	}
	
	@Test
	public void windowRowShouldInsertANullOnMissingComponent(){
		List<Component> row = new ArrayList<Component>();
		Component r1 = relayFactory.make("1", 0, 0);
		Component r3 = relayFactory.make("3", 2, 0);
		row.add(r1);
		row.add(null);
		row.add(r3);
		
		List<Component> result = c.windowRow(0, 2, row);
		assertThat(result.size(), is(3));
		assertThat((Component) result.get(0), equalTo(r1));
		assertThat((Component) result.get(1), nullValue());
		assertThat((Component) result.get(2), equalTo(r3));
	}
	
}
