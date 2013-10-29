package org.ag.common.env;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Nodes are connected in a 8-way, that is, they can have 8 different neighbours. This class declares these directions.
 * They are used throughout the framework in order to select nodes, move agents and create environments.
 *
 * @author Luiz Abrahao <luiz@abrahao.com>
 */
public enum Direction {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

    private static final List<Direction> values = Arrays.asList(Direction.values());
    private static final Random random = new Random();

    /**
     * Returns a random direction.
     *
     * @return random direction
     */
    public static Direction getRandomDirection() {
        return values.get(random.nextInt(values.size()));
    }

    /**
     * Returns the opposite direction. Possible combinations:
     *
     * <ul>
     *     <li>NORTH -> SOUTH</li>
     *     <li>NORTH_EAST -> SOUTH_WEST</li>
     *     <li><EAST -> WEST/li>
     *     <li>SOUTH_EAST -> NORTH_WEST</li>
     *     <li>SOUTH -> NORTH</li>
     *     <li>SOUTH_WEST -> NORTH_EAST</li>
     *     <li>WEST -> EAST</li>
     *     <li>NORTH_WEST -> SOUTH_EAST</li>
     * </ul>
     *
     * @return opposite direction
     */
    public Direction getOpposite() {
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

        throw new RuntimeException("Could not give oposit direction for "
                + this);
    }
}
