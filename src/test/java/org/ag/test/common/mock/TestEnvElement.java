package org.ag.test.common.mock;

import java.awt.Color;
import java.awt.Dimension;

import org.ag.common.env.BasicEnvironmentElement;
import org.ag.common.env.EnvironmentFactory;

/**
 * A simple concrete implementation for an environment element to be used in tests. It is composed by nodes of the
 * <i>BasicNode</i> node implementation.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
public class TestEnvElement extends BasicEnvironmentElement {

    /**
     * Constructs a test environment element with an unique identifier, dimension and the colour that it will be
     * rendered.
     *
     * @param id unique identifier.
     * @param dimension element's dimension.
     * @param colour colour used when rendering the element.
     */
    public TestEnvElement(String id, Dimension dimension, Color colour) {
        super(id, dimension, colour, EnvironmentFactory.createBasicNodeGrid(dimension));
    }
}
