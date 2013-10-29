package org.ag.common.agent;

import java.util.Collections;
import java.util.List;

import org.ag.common.env.Node;
import org.ag.common.task.Task;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * This class is a task-oriented agent. It contains a list of tasks that the agent is capable to execute. Only one task
 * can be executed at time, the currentTask variable holds the current task in execution.
 *
 * <p>This class is thread-safe.</p>
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@ThreadSafe
public abstract class TaskAgent extends AbstractAgent {
    @GuardedBy("this")
    private Task currentTask;
    protected final TaskAgentType agentType;

    /**
     * Construct an agent that is able to execute tasks with an id, a type, the node it will start from and whether it
     * should keep track of the nodes it has visited or not.
     *
     * @param id agent's unique identifier.
     * @param agentType agent's type.
     * @param currentNode node the agent will start from.
     * @param recordNodeHistory whether the agent should record the nodes it has visited or not.
     */
    public TaskAgent(final String id, final TaskAgentType agentType,
                     final Node currentNode, final boolean recordNodeHistory) {

        super(id, agentType, currentNode, recordNodeHistory);
        this.agentType = agentType;
    }

    /**
     * Construct an agent that is able to execute tasks with an id, a type, and whether it should keep track of the
     * nodes it has visited or not.
     *
     * @param id agent's unique identifier.
     * @param agentType agent's type.
     * @param recordNodeHistory whether the agent should record the nodes it has visited or not.
     */
    public TaskAgent(final String id, final TaskAgentType agentType,
                     final boolean recordNodeHistory) {

        super(id, agentType, recordNodeHistory);
        this.agentType = agentType;
    }

    /**
     * Construct an agent that is able to execute tasks with an id, a type, agents created using this constructor will
     * not record the nodes they have visited.
     *
     * @param id agent's unique identifier.
     * @param agentType agent's type.
     */
    public TaskAgent(final String id, final TaskAgentType agentType) {
        super(id, agentType);

        this.agentType = agentType;
    }

    /**
     * Returns the tasks the agent is capable of performing depending on its type.
     * <p>Note that the list is unmodifiable.</p>
     *
     * @return list of tasks agent is capable of performing.
     */
    public List<Task> getTaskList() {
        return Collections.unmodifiableList(agentType.getTasks());
    }

    /**
     * Returns the task the agent is performing at the moment.
     *
     * @return task the agent is performing.
     */
    public synchronized Task getCurrentTask() {
        return currentTask;
    }

    /**
     * Sets the task that the agent is performing at the moment. This does not make the agent to actually execute the
     * task. This is set by the tasks or the decision controls that actually choose what the agent needs to execute
     * next. It is useful to 'tell' the agent which task it is performing at the moment so it might be used for the
     * decision making process of other tasks.
     *
     * @param currentTask tasks that the agent is performing.
     */
    public synchronized void setCurrentTask(final Task currentTask) {
        this.currentTask = currentTask;
    }

    /**
     * Sets the task that the agent is performing at the moment. This does not make the agent to actually execute the
     * task. This is set by the tasks or the decision controls that actually choose what the agent needs to execute
     * next. It is useful to 'tell' the agent which task it is performing at the moment so it might be used for the
     * decision making process of other tasks.
     *
     * @param taskName the name of the task the agent is performing.
     */
    public synchronized void setCurrentTask(final String taskName) {
        this.currentTask = this.getTaskByName(taskName);
    }

    /**
     * Return the task with the given name if the agent is capable of performing it. In case it isn't it throws a
     * runtime exception.
     *
     * @param name name of the task.
     * @throws RuntimeException if the agent isn't capable of perform the task with given name.
     * @return task with given name.
     */
    public Task getTaskByName(final String name) {
        for (Task task : agentType.getTasks()) {
            if (task.getName().equals(name)) {
                return task;
            }
        }

        throw new RuntimeException(this.getId() + " is not capable of performing task " + name);
    }
}