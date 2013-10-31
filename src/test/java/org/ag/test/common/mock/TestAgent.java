package org.ag.test.common.mock;

import org.ag.common.agent.AbstractAgent;
import org.ag.common.agent.AgentType;
import org.ag.common.env.Node;

/**
 * Concrete implementation of <i>AbstractAgent</i> interface to be used in unit testing. Agents of this type are not
 * capable of executing any task and a <i>RuntimeException</i> will be throw if they are submitted to execution.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
public class TestAgent extends AbstractAgent {
    public TestAgent(String id, AgentType agentType, Node currentNode, boolean recordNodeHistory) {
        super(id, agentType, currentNode, recordNodeHistory);
    }

    /**
     * Implemented only in order to satisfy <i>Callable</i> interface requirement, throws <i>RuntimeException</i> if the
     * agent is submitted to an executor and this method is called.
     *
     * @return Void
     * @throws RuntimeException if method called.
     */
    @Override
    public Void call() throws Exception {
        throw new RuntimeException("You cannot use TestAgent as java tasks");
    }
}
