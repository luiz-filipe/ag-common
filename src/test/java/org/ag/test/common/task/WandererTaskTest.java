package org.ag.test.common.task;

import org.ag.common.agent.Agent;
import org.ag.common.env.BasicNode;
import org.ag.common.env.Direction;
import org.ag.common.env.Node;
import org.ag.common.task.Task;
import org.ag.common.task.WandererTask;
import org.ag.test.common.mock.TestAgent;
import org.ag.test.common.mock.TestAgentType;
import org.junit.Test;

public class WandererTaskTest {
    /**
     * The idea of this test it's to make sure that the task will not try to add the agent to a node in a direction that
     * does not have a neighbour node.
     *
     * <p>There is no assertion to be made here, if no NullPointer exception is thrown the test should be considered
     * as passed.</p>
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
