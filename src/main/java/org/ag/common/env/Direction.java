package org.ag.common.env;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * These are the directions each node can use to look for a neighbour. The idea
 * is that a node can have a neighbour in each of the following directions.
 * 
 * The AG Common package supports only 2D environments for now. Nodes can be
 * connected to up to 8 neighbours.
 * 
 * @author Luiz Abrahao <luiz@abrahao.com>
 */
public enum Direction {
	NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

	private static final List<Direction> values = Arrays.asList(Direction
			.values());
	private static final Random random = new Random();

	public static Direction randomDirection() {
		return values.get(random.nextInt(values.size()));
	}

	public Direction getOposit() {
		switch (this) {
		case NORTH:
			return Direction.SOUTH;

		case NORTH_EAST:
			return Direction.SOUTH_WEST;

		case EAST:
			return Direction.WEST;

		case SOUTH_EAST:
			return Direction.NORTH_WEST;

		case SOUTH:
			return Direction.NORTH;

		case SOUTH_WEST:
			return Direction.NORTH_EAST;

		case WEST:
			return Direction.EAST;

		case NORTH_WEST:
			return Direction.SOUTH_EAST;
		}

		throw new RuntimeException("Could not give oposit direction for " + this);
	}
}
