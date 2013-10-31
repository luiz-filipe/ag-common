package org.ag.test.common.agent;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.ag.common.agent.AbstractAgent;
import org.junit.Test;

public class AbstractAgentTest {

    @Test
    public void equalsContract() {
        EqualsVerifier.forClass(AbstractAgent.class).verify();
    }
}
