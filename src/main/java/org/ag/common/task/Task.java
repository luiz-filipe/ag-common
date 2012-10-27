package org.ag.common.task;

import org.ag.common.agent.Agent;

/**
 * A task for the framework is not a unit of work like a task is in Java
 * concurrent programming, in this framework a task is more like a process to 
 * achieve something, for example, one could create a task to find food
 * sources. Tasks can even use another tasks to achieve their goals.
 * 
 * The execute(Agent) method executed by agents when they wish to achieve some
 * goal and the task would help them witht that.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public interface Task {
	/**
	 * Each task type has a name, an identifier that is unique and is used to
	 * tell one task to another when it is needed.
	 *	
	 * @return String task type identifier.
	 */
	String getName();
	
	/**
	 * Executes a set of instruction that defines the agent's behaviour when
	 * executing the task.
	 * 
	 * The parameter <em>agent</em> might seem a bit awkward at first, but it
	 * is necessary because the task need to have access to the agent's context
	 * when executing, for example: the agent's current node 
	 * 
	 * @param agent Agent that will perform the task.
	 */
	void execute(final Agent agent);
}
