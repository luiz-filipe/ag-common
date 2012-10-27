package org.ag.test.common.mock;

import org.ag.common.agent.AbstractAgent;
import org.ag.common.agent.AgentType;
import org.ag.common.env.Node;

public class TestAgent extends AbstractAgent {

	public TestAgent(String id, AgentType agentType, Node currentNode,
			boolean recordNodeHistory) {
		super(id, agentType, currentNode, recordNodeHistory);
	}

	@Override
	public Void call() throws Exception {
		throw new RuntimeException("You cannot use TestAgent as java tasks");
	}
}
