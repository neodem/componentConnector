package com.neodem.componentConnector.model.component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.component.Component;

/**
 * @author vfumo
 * 
 */
public class ComponentTest extends AbstractBaseRelayLocatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void twoComponentsOtherwiseIdenticalButForRotationShouldHaveDifferentHashCodes() {
		Component r1 = relayFactory.make("r1", 1, 1);
		Component r2 = relayFactory.make("r1", 1, 1);
		r2.invert();

		assertThat(r1.hashCode(), not(equalTo(r2.hashCode())));
	}

	@Test
	public void twoComponentsWithTheSameIdShouldBeEqual() {
		Component r1 = relayFactory.make("r1", 1, 1);
		Component r2 = relayFactory.make("r1", 1, 1);
		r2.invert();

		assertThat(r1, equalTo(r2));
	}

}
