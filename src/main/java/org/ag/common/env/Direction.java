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

	private static final List<Direction> values = Arrays.asList(Direction.values());
	private static final Random random = new Random();

	public static Direction randomDirection() {
		return values.get(random.nextInt(values.size()));
	}
}
