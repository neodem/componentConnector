package com.neodem.componentConnector.tools;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.model.Location;
import com.neodem.componentConnector.model.Pin;
import com.neodem.componentConnector.model.components.Item;

/**
 * @author vfumo
 * 
 */
public class CalculatorTest  {

	Calculator calculator;

	@Before
	public void setUp() throws Exception {
		calculator = new Calculator();
	}

	@After
	public void tearDown() throws Exception {
		calculator = null;
	}

	// absolute score is score without considering rotation

	@Test
	public void OnSameRowAndAdjacentShouldHaveZeroScore() {
		Location from = new Location(0,0);
		Location to = new Location(0,1);
		
		assertThat(calculator.calculateAbsoluteScore(from, to), is(0));
	}

	@Test
	public void OnSameRowAndOneApartShouldHaveOneScore() {
		Location from = new Location(0,0);
		Location to = new Location(0,2);
		
		assertThat(calculator.calculateAbsoluteScore(from, to), is(1));
		assertThat(calculator.calculateAbsoluteScore(to, from), is(1));
	}

	@Test
	public void OneRowApartButOnTopOfEachOtherShouldHaveZeroScore() {
		Location from = new Location(0,0);
		Location to = new Location(1,0);
		
		assertThat(calculator.calculateAbsoluteScore(from, to), is(0));
	}

	@Test
	public void TwoRowsApartButOnTopOfEachOtherShouldHaveOneScore() {
		Location from = new Location(0,0);
		Location to = new Location(2,0);
		
		assertThat(calculator.calculateAbsoluteScore(from, to), is(1));
		assertThat(calculator.calculateAbsoluteScore(to, from), is(1));
	}

	@Test
	public void OneRowApartAndOneColApartShouldHaveTwoScore() {
		Location from = new Location(0,0);
		Location to = new Location(1,1);
		
		assertThat(calculator.calculateAbsoluteScore(from, to), is(2));
	}

	@Test
	public void TwoRowsApartAndOneColApartShouldHaveThreeScore() {
		Location from = new Location(0,0);
		Location to = new Location(2,1);
		
		assertThat(calculator.calculateAbsoluteScore(from, to), is(3));
	}
	
	@Test
	public void sameRowAdjacentShouldHaveVariousValuesDependingOnRotation() {
		Location fromLoc = new Location(0,0);
		Location toLoc = new Location(0,1);
		
		Item from = new Item("from", 12);
		Item to = new Item("to", 12);
		
		Pin leftSidePin =  new Pin(7, "");
		Pin rightSidePin =  new Pin(6, "");
		
		assertThat(calculator.calculateRotationalScore(from, fromLoc, false, leftSidePin, to, toLoc, false, leftSidePin), is(1));
		assertThat(calculator.calculateRotationalScore(from, fromLoc, false, leftSidePin, to, toLoc, false, rightSidePin), is(2));
		assertThat(calculator.calculateRotationalScore(from, fromLoc, false, rightSidePin, to, toLoc, false, rightSidePin), is(1));
		assertThat(calculator.calculateRotationalScore(from, fromLoc, false, rightSidePin, to, toLoc, false, leftSidePin), is(0));
	}

	@Test
	public void sameColumnAdjacentShouldHaveVariousValuesDependingOnRotation() {
		Location fromLoc = new Location(0,0);
		Location toLoc = new Location(1,0);
		
		Item from = new Item("from", 12);
		Item to = new Item("to", 12);
		
		Pin leftSidePin =  new Pin(7, "");
		Pin rightSidePin =  new Pin(6, "");
		
		assertThat(calculator.calculateRotationalScore(from, fromLoc, false, leftSidePin, to, toLoc, false, leftSidePin), is(0));
		assertThat(calculator.calculateRotationalScore(from, fromLoc, false, leftSidePin, to, toLoc, false, rightSidePin), is(1));
		assertThat(calculator.calculateRotationalScore(from, fromLoc, false, rightSidePin, to, toLoc, false, rightSidePin), is(0));
		assertThat(calculator.calculateRotationalScore(from, fromLoc, false, rightSidePin, to, toLoc, false, leftSidePin), is(1));
	}
}
