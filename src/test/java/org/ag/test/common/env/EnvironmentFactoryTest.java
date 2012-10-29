package org.ag.test.common.env;

import static org.junit.Assert.assertTrue;

import org.ag.common.env.Direction;
import org.ag.common.env.EnvironmentFactory;
import org.ag.common.env.Node;
import org.junit.Test;

public class EnvironmentFactoryTest {

	@Test
	public void basicNodeGrid() {
		Node[][] grid = EnvironmentFactory.createBasicNodeGrid(3, 3);
		
		assertTrue(grid[0][0].getId().equals("n0,0"));
		assertTrue(grid[1][0].getId().equals("n1,0"));
		assertTrue(grid[2][0].getId().equals("n2,0"));
		
		assertTrue(grid[0][1].getId().equals("n0,1"));
		assertTrue(grid[1][1].getId().equals("n1,1"));
		assertTrue(grid[2][1].getId().equals("n2,1"));
		
		assertTrue(grid[0][2].getId().equals("n0,2"));
		assertTrue(grid[1][2].getId().equals("n1,2"));
		assertTrue(grid[2][2].getId().equals("n2,2"));
		
		assertTrue(grid[0][0].getNeighbour(Direction.NORTH) == null);
		assertTrue(grid[0][0].getNeighbour(Direction.NORTH_EAST) == null);
		assertTrue(grid[0][0].getNeighbour(Direction.EAST).equals(grid[0][1]));
		assertTrue(grid[0][0].getNeighbour(Direction.SOUTH_EAST).equals(grid[1][1]));
		assertTrue(grid[0][0].getNeighbour(Direction.SOUTH).equals(grid[1][0]));
		assertTrue(grid[0][0].getNeighbour(Direction.SOUTH_WEST) == null);
		assertTrue(grid[0][0].getNeighbour(Direction.WEST) == null);
		assertTrue(grid[0][0].getNeighbour(Direction.NORTH_WEST) == null);
		
		assertTrue(grid[1][1].getNeighbour(Direction.NORTH).equals(grid[0][1]));
		assertTrue(grid[1][1].getNeighbour(Direction.NORTH_EAST).equals(grid[0][2]));
		assertTrue(grid[1][1].getNeighbour(Direction.EAST).equals(grid[1][2]));
		assertTrue(grid[1][1].getNeighbour(Direction.SOUTH_EAST).equals(grid[2][2]));
		assertTrue(grid[1][1].getNeighbour(Direction.SOUTH).equals(grid[2][1]));
		assertTrue(grid[1][1].getNeighbour(Direction.SOUTH_WEST).equals(grid[2][0]));
		assertTrue(grid[1][1].getNeighbour(Direction.WEST).equals(grid[1][0]));
		assertTrue(grid[1][1].getNeighbour(Direction.NORTH_WEST).equals(grid[0][0]));
		
		assertTrue(grid[2][2].getNeighbour(Direction.NORTH).equals(grid[1][2]));
		assertTrue(grid[2][2].getNeighbour(Direction.NORTH_EAST) == null);
		assertTrue(grid[2][2].getNeighbour(Direction.EAST) == null);
		assertTrue(grid[2][2].getNeighbour(Direction.SOUTH_EAST) == null);
		assertTrue(grid[2][2].getNeighbour(Direction.SOUTH) == null);
		assertTrue(grid[2][2].getNeighbour(Direction.SOUTH_WEST) == null);
		assertTrue(grid[2][2].getNeighbour(Direction.WEST).equals(grid[2][1]));
		assertTrue(grid[2][2].getNeighbour(Direction.NORTH_WEST).equals(grid[1][1]));
	}
}
