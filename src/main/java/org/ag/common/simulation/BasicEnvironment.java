package org.ag.common.simulation;

import java.awt.Dimension;

import org.ag.common.env.EnvironmentFactory;

/**
 * A environment provides encapsulation for a grid of basic nodes and some handy
 * methods to place agents into this grid.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
public class BasicEnvironment extends AbsractEnvironment {
	public BasicEnvironment(final int height, final int width) {
		super(EnvironmentFactory.createBasicNodeGrid(new Dimension(height,
				width)), new Dimension(height, width));
	}
}
