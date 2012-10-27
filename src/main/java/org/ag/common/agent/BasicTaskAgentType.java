package org.ag.common.agent;

import java.util.ArrayList;
import java.util.List;

import org.ag.common.task.Task;
import org.ag.common.task.WandererTask;

import net.jcip.annotations.ThreadSafe;

/**
 * This is the reference implementation of a concrete TaskAgentType. Note that
 * it is implemented as an Enum to implement the Singleton pattern.
 * 
 * This agent yype only has the WandererTask in its list and should be used in
 * unit tests and as a reference when building more complex and domain specific
 * agent types.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
@ThreadSafe
public enum BasicTaskAgentType implements TaskAgentType {
	TYPE;

	private final String name = "agent:type:basic-task";
	private final List<Task> tasks;

	BasicTaskAgentType() {
		tasks = new ArrayList<Task>();
		tasks.add(new WandererTask());
	}

	@Override
	public List<Task> getTasks() {
		return tasks;
	}

	@Override
	public String getName() {
		return name;
	}
}