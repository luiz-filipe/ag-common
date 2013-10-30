package org.ag.common.task;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import net.jcip.annotations.Immutable;

/**
 * Simulations need to be able to schedule tasks for future execution. The ScheduledTaskWrapper is a simple class that
 * encapsulate the task and details on how the framework should execute it.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
@Immutable
public class ScheduledTaskWrapper<T> {
    private final TimeUnit unit;
    private final long delay;
    private final Callable<T> task;

    /**
     * Constructs a wrapper with a callable and the delay until its execution.
     *
     * @param task callable that will be executed.
     * @param delay amount of time until its execution.
     * @param unit unite for the time delay.
     */
    public ScheduledTaskWrapper(Callable<T> task, long delay, TimeUnit unit) {
        this.task = task;
        this.delay = delay;
        this.unit = unit;
    }

    /**
     * Returns the unit for the time delay execution.
     *
     * @return unit of time delay.
     */
    public TimeUnit getUnit() { return unit; }

    /**
     * Returns the delay until the task execution.
     *
     * @return delay until task execution.
     */
    public long getDelay() { return delay; }

    /**
     * Returns the task to be executed.
     *
     * @return task to be executed.
     */
    public Callable<T> getTask() { return task; }
}
