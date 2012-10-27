package org.ag.test.common.task;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.ag.common.task.AbstractTask;
import org.junit.Test;

public class AbstractTaskTest {
	
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(AbstractTask.class)
			.verify();
	}
}
