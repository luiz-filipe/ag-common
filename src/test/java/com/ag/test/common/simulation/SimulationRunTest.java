package com.ag.test.common.simulation;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

import org.ag.common.agent.Agent;
import org.ag.common.simulation.Simulation;
import org.ag.test.common.mock.TestTaskAgent;

public class SimulationRunTest {

	public static void main(String[] args) {
		final Agent a01 = new TestTaskAgent("a01");
		final Agent a02 = new TestTaskAgent("a02");

		final Simulation simulation = new Simulation("./target/", 200, 200, 20);

		simulation.addAgentMiddleEnvironment(a01);
		simulation.addAgentMiddleEnvironment(a02);

		simulation.scheduleEnvironmentExploredRenderer("explored-5.png",
				Color.DARK_GRAY, Color.white, 5, TimeUnit.SECONDS);
		
		simulation.scheduleEnvironmentExploredRenderer("explored-10.png",
				Color.DARK_GRAY, Color.white, 10, TimeUnit.SECONDS);

		simulation.scheduleEnvironmentExploredRenderer("explored-20.png",
				Color.DARK_GRAY, Color.white, 20, TimeUnit.SECONDS);
		
		simulation.scheduleEnvironmentExploredRenderer("explored-30.png",
				Color.DARK_GRAY, Color.white, 30, TimeUnit.SECONDS);
		
		simulation.run(30, TimeUnit.SECONDS);
	}
}
