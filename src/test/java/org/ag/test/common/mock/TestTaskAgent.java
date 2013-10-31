package org.ag.test.common.mock;

import org.ag.common.agent.TaskAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple agent of type <i>TestTaskAgentType</i>. Agents of this class are used in tests that need agents in it.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>*
 */
public class TestTaskAgent extends TaskAgent {
    private static final Logger logger = LoggerFactory.getLogger(TestTaskAgent.class);

    /**
     * Constructs an agent with the given identifier and assign the <i>TestTaskAgentType</i> as its type.
     *
     * @param id agent's unique identifier.
     */
    public TestTaskAgent(String id) {
        super(id, TestTaskAgentType.TYPE);
    }

    /**
     * Executes the <i>WandererTask</i> and rests for 200 milliseconds repeatedly until the thread the agent is running
     * on is interrupted.
     *
     * @return Void
     * @throws Exception if the agent is not able to perform the task.
     */
    @Override
    public Void call() throws Exception {
        while (!Thread.currentThread().isInterrupted()) {
            this.getTaskList().get(0).execute(this);
            logger.trace("[{}] moved to: {}", this.getId(), this.getCurrentNode().getId());

            try {
                Thread.sleep(200);

            } catch (InterruptedException e) {
                // reset the flag to allow thread terminate on its own...
                Thread.currentThread().interrupt();
            }
        }

        logger.info("[{}] asked to stop...", this.getId());
        return null;
    }

}
