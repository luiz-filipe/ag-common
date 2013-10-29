package org.ag.test.common.mock;

import java.awt.Color;
import java.awt.Dimension;

import org.ag.common.env.BasicEnvironmentElement;
import org.ag.common.env.EnvironmentFactory;

public class TestEnvElement extends BasicEnvironmentElement {

	public TestEnvElement(String id, Dimension dimension, Color colour) {
		super(id, dimension, colour, EnvironmentFactory
				.createBasicNodeGrid(dimension));
	}
}
