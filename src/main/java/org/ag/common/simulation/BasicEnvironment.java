package org.ag.common.simulation;

import java.awt.Dimension;

import org.ag.common.env.EnvironmentFactory;

/**
 * The <i>BasicEnvironment</i> class is a basic concrete implementation of the environment class, it is formed by nodes
 * from the <i>BasicNode</i> class.
 *
 * @see org.ag.common.env.BasicNode
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public class BasicEnvironment extends AbstractEnvironment {
    public BasicEnvironment(final int height, final int width) {
        super(EnvironmentFactory.createBasicNodeGrid(new Dimension(height,
                width)), new Dimension(height, width));
    }
}
