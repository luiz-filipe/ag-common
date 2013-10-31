package org.ag.common.agent;

import java.util.List;

import org.ag.common.task.Task;

/**
 * Task agents have a list of tasks they are enable to execute. This list comes from their type. Each type of task
 * agents declare this list of tasks and it is used by the agent during their life-cycle.
 *
 * <p>Task agent type should be implemented as singleton in order to save memory consumption</p>
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 *
 */
public interface TaskAgentType extends AgentType {
    List<Task> getTasks();
}