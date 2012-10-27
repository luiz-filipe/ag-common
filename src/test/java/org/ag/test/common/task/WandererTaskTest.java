package org.ag.test.common.task;

import static org.junit.Assert.assertTrue;

import org.ag.common.env.BasicNode;
import org.ag.common.env.Direction;
import org.ag.common.env.Node;
import org.ag.common.task.WandererTask;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WandererTaskTest {
	private static final Logger logger = LoggerFactory
			.getLogger(WandererTaskTest.class);

	/**
	 * The idea of this test is to verify that the tasks chooses a neighbour
	 * randomly in a fair way. So it asks the WandererTask to choose a neighbour
	 * a thousand times and check the distribution of the chosen nodes
	 */
	@Test
	public void randomNeighbourDistribution() {
		final int nInteractions = 1000;
		final int nDirections = 4;
		final int margin = (int) ((nInteractions / nDirections) * 0.15);
		final int expectedMinimum = (nInteractions / nDirections) - margin;
		final int expectedMaximum = (nInteractions / nDirections) + margin;

		int nNorth = 0;
		int nEast = 0;
		int nSouth = 0;
		int nWest = 0;

		final Node north = new BasicNode("north");
		final Node east = new BasicNode("east");
		final Node south = new BasicNode("south");
		final Node west = new BasicNode("west");
		final Node centre = new BasicNode("centre");

		centre.setNeighbours(Direction.NORTH, north);
		centre.setNeighbours(Direction.EAST, east);
		centre.setNeighbours(Direction.SOUTH, south);
		centre.setNeighbours(Direction.WEST, west);

		for (int i = 0; i < nInteractions; i++) {
			Direction chosen = WandererTask.getRandomDirection();

			if (chosen.equals(Direction.NORTH)) {
				nNorth++;

			} else if (chosen.equals(Direction.EAST)) {
				nEast++;

			} else if (chosen.equals(Direction.SOUTH)) {
				nSouth++;

			} else {
				nWest++;
			}
		}

		logger.info("Random neighbour direction results ----------");
		logger.info("Number of times north node was selected: {}", nNorth);
		logger.info("Number of times east node was selected: {}", nEast);
		logger.info("Number of times south node was selected: {}", nSouth);
		logger.info("Number of times west node was selected: {}", nWest);

		assertTrue(nNorth > expectedMinimum);
		assertTrue(nNorth < expectedMaximum);
		assertTrue(nEast > expectedMinimum);
		assertTrue(nEast < expectedMaximum);
		assertTrue(nSouth > expectedMinimum);
		assertTrue(nSouth < expectedMaximum);
		assertTrue(nWest > expectedMinimum);
		assertTrue(nWest < expectedMaximum);
	}
}
