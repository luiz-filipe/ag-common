package org.ag.common.env;

import net.jcip.annotations.Immutable;

/**
 * Simple object class to represent a coordinate to a cell in the environment.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
@Immutable
public class Coordinate {
	private final int column;
	private final int line;

	public int getColumn() {
		return column;
	}

	public int getLine() {
		return line;
	}

	public Coordinate(final int line, final int column) {
		this.column = column;
		this.line = line;
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
