package org.ag.test.common.agent;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.ag.common.agent.TaskAgent;
import org.junit.Test;

public class TaskAgentTest {
    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(TaskAgent.class).verify();
    }
}
