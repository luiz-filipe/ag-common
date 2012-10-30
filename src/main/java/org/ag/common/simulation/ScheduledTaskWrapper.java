package org.ag.common.simulation;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import net.jcip.annotations.Immutable;

@Immutable
public class ScheduledTaskWrapper {
	private final TimeUnit unit;
	private final long period;
	private final Callable<Void> task;

	public ScheduledTaskWrapper(Callable<Void> task, long period, TimeUnit unit) {
		this.task = task;
		this.period = period;
		this.unit = unit;
	}

	public TimeUnit getUnit() { return unit; }
	public long getPeriod() { return period; }
	public Callable<Void> getTask() { return task; }
}
