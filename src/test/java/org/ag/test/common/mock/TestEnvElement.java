package org.ag.test.common.mock;

import java.awt.Color;
import java.awt.Dimension;

import org.ag.common.env.EnvironmentElement;
import org.ag.common.env.EnvironmentFactory;

public class TestEnvElement extends EnvironmentElement {

	public TestEnvElement(String id, Dimension dimension, Color colour) {
		super(id, dimension, colour, EnvironmentFactory
				.createBasicNodeGrid(dimension));
	}
}
