package org.ag.common.simulation;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import net.jcip.annotations.Immutable;

/**
 * Simulations need to be able to schedule tasks for future execution. The
 * ScheduledTaskWrapper is a simple class that encapsulate the task and details
 * for executing it. 
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@Immutable
public class ScheduledTaskWrapper {
	private final TimeUnit unit;
	private final long delay;
	private final Callable<Void> task;

	public ScheduledTaskWrapper(Callable<Void> task, long delay, TimeUnit unit) {
		this.task = task;
		this.delay = delay;
		this.unit = unit;
	}

	public TimeUnit getUnit() { return unit; }
	public long getDelay() { return delay; }
	public Callable<Void> getTask() { return task; }
}
