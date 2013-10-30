package org.ag.common.task;

import org.ag.common.agent.Agent;
import org.ag.common.env.Direction;
import org.ag.common.env.Node;

import net.jcip.annotations.Immutable;

/**
 * A simple implementation of a task. When executing this task the agent wanders the environment without worrying about
 * anything else. The task picks up a random direction and the agent moves to that neighbour node.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
@Immutable
public class WandererTask extends AbstractTask {
    public static final String NAME = "Wanderer";

    public WandererTask() {
        super(WandererTask.NAME);
    }

    /**
     * Randomly navigates from node to node in the environment.
     *
     * <p>Note that this method would fall into a deadlock if the agent's current node is not connected to any other
     * node.</p>
     */
    @Override
    public void execute(final Agent agent) {
        final Node neighbourNode = agent.getCurrentNode().getNeighbour(
                Direction.getRandomDirection());

        if (neighbourNode != null) {
            neighbourNode.addAgent(agent);

        } else {
            this.execute(agent);
        }
    }
}