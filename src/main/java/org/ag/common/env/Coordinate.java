package org.ag.common.env;

import net.jcip.annotations.Immutable;

/**
 * Representation of a coordinate in the environment.
 *
 * <p>This class is immutable.</p>
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
@Immutable
public class Coordinate {
    private final int column;
    private final int line;

    /**
     * Constructs a coordinate with a vertical and a horizontal coodrinate.
     * @param line vertical coordinate
     * @param column horizontal coordinate
     */
    public Coordinate(final int line, final int column) {
        this.column = column;
        this.line = line;
    }

    /**
     * Returns the horizontal coordinate of the point.
     *
     * @return horizontal coordinate of the point.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Returns the vertical coordinate of the point.
     *
     * @return vertical coordinate of the point.
     */
    public int getLine() {
        return line;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + column;
        result = prime * result + line;
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Coordinate))
            return false;
        Coordinate other = (Coordinate) obj;
        if (column != other.column)
            return false;
        if (line != other.line)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Coordinate [column=" + column + ", line=" + line + "]";
    }
}
