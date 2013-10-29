package org.ag.test.common.env;

import java.awt.Color;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.ag.common.env.BasicEnvironmentElement;
import org.junit.Test;

public class EnviromentElementTest {
	@Test
	public void equalsContractTest() {
		EqualsVerifier.forClass(BasicEnvironmentElement.class)
		.withPrefabValues(Color.class, Color.red, Color.black)
		.verify();
	}
}
