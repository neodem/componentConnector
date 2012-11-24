package com.neodem.componentConnector.graphics.display;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import com.neodem.componentConnector.AbstractBaseRelayLocatorTest;
import com.neodem.componentConnector.model.components.BaseComponent;
import com.neodem.componentConnector.model.sets.ComponentSet;
import com.neodem.componentConnector.model.sets.SetItem;

public class CrudeConsoleDisplayTest extends AbstractBaseRelayLocatorTest {

	@Test
	public void getComponentsToDrawShouldWorkCorrectly() {
		ComponentSet set = new ComponentSet(3, 3);
		BaseComponent r1 = addToSet(set, "1", 2, 1);
		BaseComponent r2 = addToSet(set, "2", 1, 2);
		BaseComponent r3 = addToSet(set, "3", 1, 1);
		BaseComponent r4 = addToSet(set, "4", 0, 1);

		GraphicalConsoleDisplay c = new GraphicalConsoleDisplay();

		SetItem[][] result = c.getComponentsToDraw(set);
		assertThat(result.length, is(3));

		SetItem[] row0 = result[0];
		SetItem[] row1 = result[1];
		SetItem[] row2 = result[2];

		assertThat(row0.length, is(3));
		assertThat(row1.length, is(3));
		assertThat(row2.length, is(3));

		assertThat((BaseComponent) row0[1].getItem(), equalTo(r4));
		assertThat((BaseComponent) row1[1].getItem(), equalTo(r3));
		assertThat((BaseComponent) row1[2].getItem(), equalTo(r2));
		assertThat((BaseComponent) row2[1].getItem(), equalTo(r1));
	}

}
