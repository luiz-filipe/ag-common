package org.ag.common.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.jcip.annotations.NotThreadSafe;

import org.ag.common.agent.Agent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NotThreadSafe
public class Simulation {
	private static final Logger logger = LoggerFactory
			.getLogger(Simulation.class);

	private final ScheduledExecutorService executor;
	private final int poolSize;

	private final Environment environment;
	private List<Agent> agents;
	private List<Future<Void>> tasks;

	public Simulation(final int nLines, final int nColumns, final int poolSize) {
		this.poolSize = poolSize;
		this.executor = Executors.newScheduledThreadPool(poolSize);

		this.environment = new Environment(nLines, nColumns);
		this.agents = new ArrayList<Agent>();
		this.tasks = new ArrayList<Future<Void>>();
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

	public void run(final long time, final TimeUnit unit) {
		logger.info("Starting simulation...");

		for (Agent agent : agents) {
			tasks.add(executor.submit(agent));
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
