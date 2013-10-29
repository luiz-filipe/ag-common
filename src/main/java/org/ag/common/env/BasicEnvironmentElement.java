package org.ag.common.env;

import java.awt.Color;
import java.awt.Dimension;

/**
 * An environment element is an abstraction to anything that could be added to
 * the environment grid. For example, nodes that could represent obstacles in
 * the element. Each element has a dimension, a colour that is used by the
 * renderer, and identification string that should be unique for each
 * environment.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public class BasicEnvironmentElement implements EnvironmentElement {
    private final String id;
    private final Dimension dimension;
    private final Color colour;
    private final Node[][] nodes;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public Color getColour() {
        return colour;
    }

    public BasicEnvironmentElement(final String id, final Dimension dimension,
                                   final Color colour, final Node[][] nodes) {

        this.id = id;
        this.dimension = dimension;
        this.colour = colour;
        this.nodes = nodes;
    }

    @Override
    public void connectToNeighbours(final Node initialNode) {
        Node initialLineNode = initialNode;
        Node currentNode = initialNode;

        for (int l = 0; l < dimension.height; l++) {
            for (int c = 0; c < dimension.width; c++) {
                // first column updates
                if (c == 0) {
                    if (l == 0) {
                        nodes[l][c].setNeighbours(Direction.NORTH,
                                currentNode.getNeighbour(Direction.NORTH));
                        nodes[l][c].setNeighbours(Direction.NORTH_EAST,
                                currentNode.getNeighbour(Direction.NORTH_EAST));
                        nodes[l][c].setNeighbours(Direction.SOUTH_WEST,
                                currentNode.getNeighbour(Direction.SOUTH_WEST));
                        nodes[l][c].setNeighbours(Direction.WEST,
                                currentNode.getNeighbour(Direction.WEST));
                        nodes[l][c].setNeighbours(Direction.NORTH_WEST,
                                currentNode.getNeighbour(Direction.NORTH_WEST));
                    }

                    if ((l > 0) && (l < dimension.height - 1)) {
                        nodes[l][c].setNeighbours(Direction.SOUTH_WEST,
                                currentNode.getNeighbour(Direction.SOUTH_WEST));
                        nodes[l][c].setNeighbours(Direction.WEST,
                                currentNode.getNeighbour(Direction.WEST));
                        nodes[l][c].setNeighbours(Direction.NORTH_WEST,
                                currentNode.getNeighbour(Direction.NORTH_WEST));
                    }

                    if (l == dimension.height - 1) {
                        nodes[l][c].setNeighbours(Direction.SOUTH_EAST,
                                currentNode.getNeighbour(Direction.SOUTH_EAST));
                        nodes[l][c].setNeighbours(Direction.SOUTH,
                                currentNode.getNeighbour(Direction.SOUTH));
                        nodes[l][c].setNeighbours(Direction.SOUTH_WEST,
                                currentNode.getNeighbour(Direction.SOUTH_WEST));
                        nodes[l][c].setNeighbours(Direction.WEST,
                                currentNode.getNeighbour(Direction.WEST));
                        nodes[l][c].setNeighbours(Direction.NORTH_WEST,
                                currentNode.getNeighbour(Direction.NORTH_WEST));
                    }

                    currentNode = currentNode.getNeighbour(Direction.EAST);
                }

                // middle columns updates
                if ((c > 0) && c < dimension.width - 1) {
                    if (l == 0) {
                        nodes[l][c].setNeighbours(Direction.NORTH,
                                currentNode.getNeighbour(Direction.NORTH));
                        nodes[l][c].setNeighbours(Direction.NORTH_EAST,
                                currentNode.getNeighbour(Direction.NORTH_EAST));
                        nodes[l][c].setNeighbours(Direction.NORTH_WEST,
                                currentNode.getNeighbour(Direction.NORTH_WEST));
                    }

                    if (l == dimension.height - 1) {
                        nodes[l][c].setNeighbours(Direction.SOUTH_EAST,
                                currentNode.getNeighbour(Direction.SOUTH_EAST));
                        nodes[l][c].setNeighbours(Direction.SOUTH,
                                currentNode.getNeighbour(Direction.SOUTH));
                        nodes[l][c].setNeighbours(Direction.SOUTH_WEST,
                                currentNode.getNeighbour(Direction.SOUTH_WEST));
                    }

                    currentNode = currentNode.getNeighbour(Direction.EAST);
                }

                // last column updates
                if (c == dimension.width - 1) {
                    if (l == 0) {
                        nodes[l][c].setNeighbours(Direction.NORTH,
                                currentNode.getNeighbour(Direction.NORTH));
                        nodes[l][c].setNeighbours(Direction.NORTH_EAST,
                                currentNode.getNeighbour(Direction.NORTH_EAST));
                        nodes[l][c].setNeighbours(Direction.EAST,
                                currentNode.getNeighbour(Direction.EAST));
                        nodes[l][c].setNeighbours(Direction.SOUTH_EAST,
                                currentNode.getNeighbour(Direction.SOUTH_EAST));
                        nodes[l][c].setNeighbours(Direction.NORTH_WEST,
                                currentNode.getNeighbour(Direction.NORTH_WEST));

                    }

                    if ((l > 0) && (l < dimension.height - 1)) {
                        nodes[l][c].setNeighbours(Direction.NORTH_EAST,
                                currentNode.getNeighbour(Direction.NORTH_EAST));
                        nodes[l][c].setNeighbours(Direction.EAST,
                                currentNode.getNeighbour(Direction.EAST));
                        nodes[l][c].setNeighbours(Direction.SOUTH_EAST,
                                currentNode.getNeighbour(Direction.SOUTH_EAST));
                    }

                    if (l == dimension.height - 1) {
                        nodes[l][c].setNeighbours(Direction.NORTH_EAST,
                                currentNode.getNeighbour(Direction.NORTH_EAST));
                        nodes[l][c].setNeighbours(Direction.EAST,
                                currentNode.getNeighbour(Direction.EAST));
                        nodes[l][c].setNeighbours(Direction.SOUTH_EAST,
                                currentNode.getNeighbour(Direction.SOUTH_EAST));
                        nodes[l][c].setNeighbours(Direction.SOUTH,
                                currentNode.getNeighbour(Direction.SOUTH));
                        nodes[l][c].setNeighbours(Direction.SOUTH_WEST,
                                currentNode.getNeighbour(Direction.SOUTH_WEST));
                    }

                    initialLineNode = initialLineNode
                            .getNeighbour(Direction.SOUTH);
                    currentNode = initialLineNode;
                }
            }
        }
    }

    @Override
    public Node getNode(final int line, final int column) {
        if ((line >= 0) && (line <= dimension.height - 1)) {
            if ((column >= 0) && (column <= dimension.width - 1)) {
                return nodes[line][column];
            }
        }

        return null;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((dimension == null) ? 0 : dimension.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof BasicEnvironmentElement))
            return false;
        BasicEnvironmentElement other = (BasicEnvironmentElement) obj;
        if (dimension == null) {
            if (other.dimension != null)
                return false;
        } else if (!dimension.equals(other.dimension))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "EnvironmentElement [id=" + id + ", dimension=" + dimension
                + ", colour=" + colour + "]";
    }
}
