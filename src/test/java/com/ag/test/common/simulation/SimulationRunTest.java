package com.ag.test.common.simulation;

import java.util.concurrent.TimeUnit;

import org.ag.common.agent.Agent;
import org.ag.common.simulation.Simulation;
import org.ag.test.common.mock.TestTaskAgent;

public class SimulationRunTest {

	public static void main(String[] args) {
		final Agent a01 = new TestTaskAgent("a01");
		final Agent a02 = new TestTaskAgent("a02");
		
		final Simulation simulation = new Simulation(200, 200, 20);
		simulation.addAgentMiddleEnvironment(a01);
		simulation.addAgentMiddleEnvironment(a02);
		
		simulation.run(02, TimeUnit.SECONDS);

	}

}
