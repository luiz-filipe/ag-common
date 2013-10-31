package org.ag.common.env;

import java.awt.Dimension;

/**
 * The EnvironmentFactory class is an utility class that holds the methods to generate environments to be used for
 * simulations.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
public class EnvironmentFactory {
    /**
     *
     */
    private EnvironmentFactory() {
    }

    /**
     * Initialises an environment based on BasicNode objects. This environment has rectangular shape and each node is
     * assigned an identifier following the pattern: "n+lineNumber,columnNumber", e.g. "n3,2" corresponds to the node
     * at the third line and second column.
     *
     * @param dimension dimension of the environment.
     *
     * @return two dimensional array of interconnected BasicNode objects.
     */
    public static Node[][] createBasicNodeGrid(final Dimension dimension) {
        Node[][] nodes = new Node[dimension.height][dimension.width];

        for (int l = 0; l < dimension.height; l++) {
            for (int c = 0; c < dimension.width; c++) {
                nodes[l][c] = new BasicNode("n" + l + "," + c);

                if (c != 0) {
                    nodes[l][c].setNeighbours(Direction.WEST, nodes[l][c - 1]);
                }

                if (l != 0) {
                    nodes[l][c].setNeighbours(Direction.NORTH, nodes[l - 1][c]);
                }

                if ((l != 0) && (c != 0)) {
                    nodes[l][c].setNeighbours(Direction.NORTH_WEST,
                            nodes[l - 1][c - 1]);
                }

                if ((l != 0) && (c != dimension.width - 1)) {
                    nodes[l][c].setNeighbours(Direction.NORTH_EAST,
                            nodes[l - 1][c + 1]);
                }
            }
        }

        return nodes;
    }
}
