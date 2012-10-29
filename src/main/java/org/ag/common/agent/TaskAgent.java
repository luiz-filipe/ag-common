package org.ag.common.agent;

import java.util.List;

import org.ag.common.env.Node;
import org.ag.common.task.Task;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * This class is a task-oriented agent. It contains a list of tasks that the
 * agent is capable to execute. Only one task can be executed at time, the
 * currentTask variable holds the current task in execution.
 * 
 * This class is thread-safe.
 * 
 * I have decided not to protect the code throwing custom exceptions at this
 * stage, it should be an improvement to be considered if the model is to be
 * expanded.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */

@ThreadSafe
public abstract class TaskAgent extends AbstractAgent {
	@GuardedBy("this")
	private Task currentTask;
	protected final TaskAgentType agentType;

	public TaskAgent(final String id, final TaskAgentType agentType,
			final Node currentNode, final boolean recordNodeHistory) {

		super(id, agentType, currentNode, recordNodeHistory);
		this.agentType = agentType;
	}

	public TaskAgent(final String id, final TaskAgentType agentType,
			final boolean recordNodeHistory) {

		super(id, agentType, recordNodeHistory);
		this.agentType = agentType;
	}

	public TaskAgent(final String id, final TaskAgentType agentType) {
		super(id, agentType);

		this.agentType = agentType;
	}

	public List<Task> getTaskList() {
		return agentType.getTasks();
	}

	public synchronized Task getCurrentTask() {
		return currentTask;
	}

	public synchronized void setCurrentTask(final Task currentTask) {
		this.currentTask = currentTask;
	}

	public synchronized void setCurrentTask(final String taskName) {
		this.currentTask = this.getTaskByName(taskName);
	}

	public Task getTaskByName(final String name) {
		for (Task task : agentType.getTasks()) {
			if (task.getName().equals(name)) {
				return task;
			}
		}

		throw new RuntimeException(this.getId()
				+ " is not capable of performing task " + name);
	}
}