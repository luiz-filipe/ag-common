package org.ag.test.common.env;

import static org.junit.Assert.assertTrue;

import org.ag.common.env.BasicNode;
import org.ag.common.env.Direction;
import org.junit.Test;

public class NodeTest {
	@Test
	public void getNeighbourTest() {
		BasicNode n = new BasicNode("n");
		
		assertTrue(n.getNeighbour(Direction.WEST) == null);
		assertTrue(n.getNeighbour(Direction.EAST) == null);
		assertTrue(n.getNeighbour(Direction.NORTH) == null);
		assertTrue(n.getNeighbour(Direction.SOUTH) == null);
		
		BasicNode north = new BasicNode("n");
		n.setNeighbours(Direction.NORTH, north);
		
		assertTrue(n.getNeighbour(Direction.NORTH) == north);
		assertTrue(north.getNeighbour(Direction.SOUTH) == n);
		assertTrue(n.getNeighbour(Direction.WEST) == null);
		assertTrue(n.getNeighbour(Direction.EAST) == null);
		
		BasicNode south = new BasicNode("n");
		n.setNeighbours(Direction.SOUTH, south);
		
		assertTrue(n.getNeighbour(Direction.SOUTH) == south);
		assertTrue(south.getNeighbour(Direction.NORTH) == n);
		assertTrue(n.getNeighbour(Direction.WEST) == null);
		assertTrue(n.getNeighbour(Direction.EAST) == null);
		
		BasicNode west = new BasicNode("n");
		n.setNeighbours(Direction.WEST, west);
		
		assertTrue(n.getNeighbour(Direction.WEST) == west);
		assertTrue(west.getNeighbour(Direction.EAST) == n);
		assertTrue(n.getNeighbour(Direction.EAST) == null);

		BasicNode east = new BasicNode("n");
		n.setNeighbours(Direction.EAST, east);
		
		assertTrue(n.getNeighbour(Direction.EAST) == east);
		assertTrue(east.getNeighbour(Direction.WEST) == n);
	}
}
