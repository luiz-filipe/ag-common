package org.ag.test.common.task;

import static org.junit.Assert.assertTrue;

import org.ag.common.agent.Agent;
import org.ag.common.env.BasicNode;
import org.ag.common.env.Direction;
import org.ag.common.env.Node;
import org.ag.common.task.Task;
import org.ag.common.task.WandererTask;
import org.ag.test.common.mock.TestAgent;
import org.ag.test.common.mock.TestAgentType;
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

		final float pNorth = (float) nNorth / nInteractions * 100;
		final float pEast = (float) nEast / nInteractions * 100;
		final float pSouth = (float) nSouth / nInteractions * 100;
		final float pWest = (float) nWest / nInteractions * 100;

		logger.info("Random neighbour direction results ----------");
		logger.info("Number of times north node was selected: {} ({}%)", 
				nNorth, pNorth);
		logger.info("Number of times east node was selected: {} ({}%)", 
				nEast, pEast);
		logger.info("Number of times south node was selected: {} ({}%)", 
				nSouth, pSouth);
		logger.info("Number of times west node was selected: {} ({}%)", 
				nWest, pWest);

		assertTrue(nNorth > expectedMinimum);
		assertTrue(nNorth < expectedMaximum);
		assertTrue(nEast > expectedMinimum);
		assertTrue(nEast < expectedMaximum);
		assertTrue(nSouth > expectedMinimum);
		assertTrue(nSouth < expectedMaximum);
		assertTrue(nWest > expectedMinimum);
		assertTrue(nWest < expectedMaximum);
	}
	
	/**
	 * The idea of this test it's to make sure that the task will not try to add
	 * the agent to a node in a direction that does not have a neighbour node.
	 */
	@Test
	public void nodeChangeTest() {
		final int nExecutions = 10;
		
		final Node centre = new BasicNode("centre");
		final Node left = new BasicNode("left");
		final Node right = new BasicNode("right");
		final Agent a = new TestAgent("a", TestAgentType.TYPE, centre, false);
		final Task t = new WandererTask();
		
		centre.setNeighbours(Direction.WEST, left);
		centre.setNeighbours(Direction.EAST, right);
		
		for (int i = 0; i < nExecutions; i++) {
			t.execute(a);
		}
		
	}
}
