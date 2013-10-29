package org.ag.common.agent;

import java.util.ArrayList;
import java.util.List;

import org.ag.common.task.Task;
import org.ag.common.task.WandererTask;

import net.jcip.annotations.ThreadSafe;

/**
 * This is a reference implementation of <i>TaskAgentType</i>, it isn't intended to be used in real simulations. Note
 * that the <i>Singleton</i> pattern is implemented here through the use of a <i>Enum</i>. This is the preferred method.
 *
 * <p>This agent Type only has the WandererTask in its list.</p>
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