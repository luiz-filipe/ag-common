package org.ag.common.env;

/**
 * These are the directions each node can use to look for a neighbour. The idea
 * is that a node can have a neighbour in each of the following directions.
 * 
 * At this stage, as we only have the BasicNode implementation, which has a
 * square shape, we only have four possible neighbours, thus we have four
 * directions. Nothing stops us to create more complex nodes, hexagon shaped for
 * example, with 6 neighbours. In that case extra directions could be added to
 * describe the direction of a hexagon-node and its neighbour.
 * 
 * @author Luiz Abrahao <luiz@abrahao.com>
 * 
 */
public enum Direction {
	NORTH, EAST, SOUTH, WEST;
}
