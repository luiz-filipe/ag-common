package org.ag.test.common.env;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.ag.common.env.Coordinate;
import org.junit.Test;

public class CoordinateTest {
	@Test
	public void testEqualsContract() {
		EqualsVerifier.forClass(Coordinate.class).verify();
	}
}
