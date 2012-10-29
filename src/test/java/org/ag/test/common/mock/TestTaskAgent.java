package org.ag.test.common.mock;

import org.ag.common.agent.TaskAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTaskAgent extends TaskAgent {
	private static final Logger logger = LoggerFactory
			.getLogger(TestTaskAgent.class);
	
	public TestTaskAgent(String id) {
		super(id, TestTaskAgentType.TYPE);
	}

	@Override
	public Void call() throws Exception {
		while (!Thread.currentThread().isInterrupted()) {
			this.getTaskList().get(0).execute(this);
			logger.debug("[{}] moved to: {}", this.getId(), this.getCurrentNode().getId());
			
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
