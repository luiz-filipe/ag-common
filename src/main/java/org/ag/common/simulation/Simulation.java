package org.ag.common.simulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import net.jcip.annotations.NotThreadSafe;

import org.ag.common.agent.Agent;
import org.ag.common.renderer.ExploratedEnvironmentRenderer;
import org.ag.common.renderer.Renderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NotThreadSafe
public class Simulation {
	private static final Logger logger = LoggerFactory
			.getLogger(Simulation.class);

	private final ScheduledExecutorService executor;
	private final int poolSize;
	private final String basePath;

	private final Environment environment;
	private final List<Agent> agents;
	private final List<Future<Void>> tasks;
	private final List<ScheduledTaskWrapper> scheduledTasks;
	private final List<ScheduledFuture<Void>> scheduledFutures;

	public Simulation(final String basePath, final int nLines,
			final int nColumns, final int poolSize) {
		// TODO add slash if not present to the end of basePath
		this.basePath = basePath;
		this.poolSize = poolSize;
		this.executor = Executors.newScheduledThreadPool(poolSize);

		this.environment = new Environment(nLines, nColumns);
		this.agents = new ArrayList<Agent>();
		this.tasks = new ArrayList<Future<Void>>();

		this.scheduledTasks = new ArrayList<ScheduledTaskWrapper>();
		this.scheduledFutures = new ArrayList<ScheduledFuture<Void>>();
	}

	public int getPoolSize() {
		return poolSize;
	}

	public int getNumberOfAgents() {
		return this.agents.size();
	}

	public void addAgent(final Agent agent, final int line, final int column) {
		this.agents.add(agent);
		this.environment.placeAgentAt(agent, line, column);
	}

	public void addAgentMiddleEnvironment(final Agent agent) {
		this.agents.add(agent);
		this.environment.placeAgentAtTheMiddle(agent);
	}

	public void scheduleRenderer(final Renderer renderer, final long delay,
			final TimeUnit unit) {

		scheduledTasks.add(new ScheduledTaskWrapper(renderer, delay, unit));
	}

	public void scheduleEnvironmentExploredRenderer(final String filename,
			final long delay, final TimeUnit unit) {
		Renderer r = new ExploratedEnvironmentRenderer(environment, basePath
				+ filename);

		this.scheduleRenderer(r, delay, unit);
	}

	public void scheduleEnvironmentExploredRenderer(final String filename,
			final Color colourEnv, final Color colourVisited, final long delay,
			final TimeUnit unit) {
		
		Renderer r = new ExploratedEnvironmentRenderer(environment, basePath
				+ filename, colourEnv, colourVisited);

		this.scheduleRenderer(r, delay, unit);
	}

	public void run(final long time, final TimeUnit unit) {
		logger.info("Starting simulation...");

		for (Agent agent : agents) {
			tasks.add(executor.submit(agent));
		}

		for (ScheduledTaskWrapper taskWrapper : scheduledTasks) {
			scheduledFutures.add(executor.schedule(taskWrapper.getTask(),
					taskWrapper.getPeriod(), taskWrapper.getUnit()));
		}

		executor.schedule(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				executor.shutdownNow();

				try {
					if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
						logger.error("Could not stop simulation!");
					}

				} catch (InterruptedException e) {
					executor.shutdownNow();
					Thread.currentThread().interrupt();
				}

				return null;
			}
		}, time, unit);
	}
}
