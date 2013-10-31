package org.ag.common.simulation;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.jcip.annotations.GuardedBy;

import org.ag.common.agent.Agent;
import org.ag.common.env.Coordinate;
import org.ag.common.env.EnvironmentElement;
import org.ag.common.env.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the base functionality of the <i>Environment</i> interface.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
public abstract class AbstractEnvironment implements Environment {
    private static final Logger logger = LoggerFactory.getLogger(AbstractEnvironment.class);

    @GuardedBy("this")
    private final Node[][] env;
    @GuardedBy("this")
    private final Dimension dimension;
    @GuardedBy("this")
    private final List<EnvironmentElement> environmentElements;

    public AbstractEnvironment(Node[][] env, Dimension dimension) {
        this.env = env;
        this.dimension = dimension;
        this.environmentElements = new ArrayList<EnvironmentElement>();
    }

    @Override
    public int getHeight() {
        return this.dimension.height;
    }

    @Override
    public int getWidth() {
        return this.dimension.width;
    }

    @Override
    public void placeAgentAt(final Agent agent, final int line, final int column) {
        if ((line < 0) || (column < 0)) {
            logger.error( "Cannot place agent '{}' into the environment. The value for line and column must be equal or " +
                    "grater than 0", agent.getId());

            return;
        }

        if (line > this.getHeight() - 1) {
            logger.error("Cannot place agent '{}' into the environment. The maximum allowed value for line is: {}",
                    agent.getId(), this.getHeight() - 1);

            return;
        }

        if (column > this.getWidth() - 1) {
            logger.error("Cannot place agent '{}' into the environment. The maximum allowed value for column is: {}",
                    agent.getId(), this.getWidth() - 1);

            return;
        }

        env[line][column].addAgentStartingHere(agent);
    }

    @Override
    public void placeAgentAtTheMiddle(final Agent agent) {
        env[this.getHeight() / 2][this.getWidth() / 2]
                .addAgentStartingHere(agent);
    }

    @Override
    public Node getNodeAt(final int line, final int column) {
        if ((line < 0) || (column < 0)) {
            logger.error("Cannot return node. line and column parameters"
                    + "must be greater or equal to 0.");

            return null;
        }

        if (line > this.getHeight() - 1) {
            logger.error("Cannot return node. The maximum allowed value for"
                    + " line is: {}", this.getHeight() - 1);

            return null;
        }

        if (column > this.getWidth() - 1) {
            logger.error("Cannot return node. The maximum allowed value for "
                    + "column is: {}", this.getWidth() - 1);

            return null;
        }

        return env[line][column];
    }

    @Override
    public void addEnvironmentElement(final EnvironmentElement element,
                                      final int line, final int column) {

        this.environmentElements.add(element);
        element.connectToNeighbours(env[line][column]);
        this.replaceNodesWithEnvironmentElement(element, line, column);
    }

    @Override
    public List<EnvironmentElement> getEnvironmentElements() {
        return Collections.unmodifiableList(this.environmentElements);
    }

    @Override
    public EnvironmentElement getEnvironmentElement(String id) {
        for (EnvironmentElement element : this.environmentElements) {
            if (element.getId().equals(id)) {
                return element;
            }
        }

        logger.error("Could not locate enviornment element with id '{}', "
                + "returning null", id);

        return null;
    }

    @Override
    public Coordinate getCentre() {
        return new Coordinate(getHeight() / 2, getWidth() / 2);
    }

    private void replaceNodesWithEnvironmentElement(final EnvironmentElement element, final int line,
                                                    final int column) {

        for (int l = line; l < line + element.getDimension().height; l++) {
            for (int c = column; c < column + element.getDimension().width; c++) {
                env[l][c].disconnectFromNeighbours();
                env[l][c] = null;
                env[l][c] = element.getNode(l - line, c - column);
            }
        }
    }
}
