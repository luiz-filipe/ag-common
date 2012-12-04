package org.ag.test.common.env;

import java.awt.Color;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.ag.common.env.EnvironmentElement;
import org.junit.Test;

public class EnviromentElementTest {
	@Test
	public void equalsContractTest() {
		EqualsVerifier.forClass(EnvironmentElement.class)
		.withPrefabValues(Color.class, Color.red, Color.black)
		.verify();
	}
}
