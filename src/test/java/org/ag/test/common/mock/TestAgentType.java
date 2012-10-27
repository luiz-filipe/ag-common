package org.ag.test.common.mock;

import org.ag.common.agent.AgentType;

public enum TestAgentType implements AgentType {
	TYPE;

	private static final String name = "agent:type:test";
	
	@Override
	public String getName() {
		return name;
	}

}
