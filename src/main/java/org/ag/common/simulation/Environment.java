package org.ag.common.simulation;

import org.ag.common.agent.Agent;
import org.ag.common.env.Coordinate;
import org.ag.common.env.EnvironmentElement;
import org.ag.common.env.Node;

import java.util.List;

/**
 * The <i>Environment</i> interface declares the basic API for manipulating a collection of nodes as a whole, it doesn't
 * describe how the nodes should be stored themselves, but a two dimensional array seems to be the most obvious choice.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
public interface Environment {
    /**
     * Returns the height of the environment in number of lines of nodes.
     *
     * @return height of the environment
     */
    int getHeight();

    /**
     * Returns the width of the environment in number of columns of nodes.
     *
     * @return width of the environment
     */
    int getWidth();

    /**
     * Returns the coordinate to the central node of the environment.

     * @return coordinate to the centre of the environment.
     */
    Coordinate getCentre();

    /**
     * Returns the node at the line and column specified.
     *
     * @param line line of the environment.
     * @param column column of the environment.
     * @return node at coordinate line,column or null if no node is found.
     */
    public Node getNodeAt(final int line, final int column);

    /**
     * Add agent to the node at the coordinate line,column.
     *
     * @param agent agent to be added.
     * @param line line agent will be added.
     * @param column column agent will be added.
     */
    void placeAgentAt(final Agent agent, final int line, final int column);

    /**
     * Add agent to the node at the centre of the environment.
     *
     * @param agent agent to be added.
     */
    void placeAgentAtTheMiddle(final Agent agent);

    /**
     * Returns a list of the environment elements present in the environment.
     *
     * @return list of environment elements.
     */
    List<EnvironmentElement> getEnvironmentElements();

    /**
     * Add an environment element to the environment at the coordinate line,column.
     *
     * @param element element to be added.
     * @param line vertical coordinate of the position the element will be added.
     * @param column horizontal coordinate of the position the element will be added.
     */
    void addEnvironmentElement(EnvironmentElement element, int column, int line);

    /**
     * Returns a particular environment element in the environment.
     *
     * @param id element's unique identifier.
     * @return environment element with the identifier or null if not found.
     */
    EnvironmentElement getEnvironmentElement(String id);
}
