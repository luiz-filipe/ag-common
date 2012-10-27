package org.ag.common.task;

import java.util.Random;

import org.ag.common.agent.Agent;
import org.ag.common.env.Direction;

import net.jcip.annotations.Immutable;

/**
 * A simple implementation of a task. When executing this task the agent wanders
 * the environment without worrying about anything else. The task picks up a
 * random direction and the agent moves to that neighbour node.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
@Immutable
public class WandererTask extends AbstractTask {
	public static final String NAME = "Wanderer";
	private static final int nDirections = 4;
	private static final Random rand = new Random();

	public WandererTask() {
		super(WandererTask.NAME);
	}

	@Override
	public void execute(final Agent agent) {
		final Direction directionToMove = WandererTask.getRandomDirection();
		agent.getCurrentNode().getNeighbour(directionToMove).addAgent(agent);
	}

	/**
	 * This method returns one of the neighbours of the agent's current node. It
	 * chooses one direction at random, if there is no neighbour on that
	 * direction, it tries again until one is found.
	 * 
	 * Note that his method would fall into a deadlock if the agent's current
	 * node is not connected to any other node.
	 * 
	 * @param agent
	 *            Agent that is performing the task
	 * @return Node that the agent is going to move to.
	 */
	public static Direction getRandomDirection() {
		final int randomDirection = WandererTask.rand.nextInt(nDirections);
		Direction direction = null;

		switch (randomDirection) {
		case 0:
			direction = Direction.NORTH;
			break;
		case 1:
			direction = Direction.EAST;
			break;
		case 2:
			direction = Direction.SOUTH;
			break;
		case 3:
			direction = Direction.WEST;
			break;
		}

		return direction;
	}
}