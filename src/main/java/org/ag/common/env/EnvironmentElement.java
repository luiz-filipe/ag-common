package org.ag.common.env;

import java.awt.*;

/**
 * An environment element is an abstraction to anything that could be added to the environment grid. For example, nodes
 * that could represent obstacles in the element. Each element has a dimension, a colour that is used by the renderer,
 * and identification string that should be unique for each environment.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
public interface EnvironmentElement {
    /**
     * Returns the element's unique identifier.
     *
     * @return element's unique identifier.
     */
    String getId();

    /**
     * Returns the element's dimension.
     *
     * @return element's dimension.
     */
    Dimension getDimension();

    /**
     * Returns the colour that will be used when rendering the element.
     *
     * @return element's colour.
     */
    Color getColour();

    /**
     * Connects the element to the environment starting from the given node, if the given node has the coordinates
     * (10, 10) in relation to the environment and the element's dimensions are 15x15, the element will spand from the
     * coordinates (10, 10) until (25, 25) in relation to the environment.
     *
     * @param initialNode the node that will be replaced by the top-left node from the nest.
     */
    void connectToNeighbours(Node initialNode);

    /**
     * Returns the node at the line and column given, note that these are in relation to the element, not to the
     * environment
     *
     * @param line vertical coordinate of the node in relation to the element.
     * @param column horizontal coordinate of the node in relation to the element.
     * @return node at line, column or null if these are out of bounds.
     */
    Node getNode(int line, int column);
}