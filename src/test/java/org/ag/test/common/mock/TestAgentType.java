package org.ag.test.common.mock;

import org.ag.common.agent.AgentType;

/**
 * Agent type implemented to be used in unit testing.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
public enum TestAgentType implements AgentType {
    TYPE;

    private static final String name = "agent:type:test";

    /**
     * Returns agent's type unique identifier.
     *
     * @return string 'agent:type:test' as identifier.
     */
    @Override
    public String getName() {
        return name;
    }

}
