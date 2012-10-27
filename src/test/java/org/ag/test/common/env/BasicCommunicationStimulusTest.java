package org.ag.test.common.env;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.ag.common.env.BasicCommunicationStimulus;
import org.ag.test.common.mock.TestCommunicationStimulus;
import org.junit.Test;


public class BasicCommunicationStimulusTest {
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(BasicCommunicationStimulus.class)
			.withRedefinedSubclass(TestCommunicationStimulus.class)
			.verify();
	}
}
