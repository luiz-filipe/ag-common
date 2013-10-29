package org.ag.test.common.simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.ag.common.agent.Agent;
import org.ag.common.simulation.BasicEnvironment;
import org.ag.common.simulation.Environment;
import org.ag.common.simulation.Simulation;
import org.ag.test.common.mock.TestTaskAgent;

public class LongSimulationRunTest {
    public static void main(String[] args) {
        final List<Agent> agents = new ArrayList<Agent>();

        agents.add(new TestTaskAgent("a01"));
        agents.add(new TestTaskAgent("a02"));
        agents.add(new TestTaskAgent("a03"));
        agents.add(new TestTaskAgent("a04"));
        agents.add(new TestTaskAgent("a05"));
        agents.add(new TestTaskAgent("a06"));
        agents.add(new TestTaskAgent("a07"));
        agents.add(new TestTaskAgent("a08"));
        agents.add(new TestTaskAgent("a09"));

        final Environment env = new BasicEnvironment(200, 200);
        final Simulation simulation = new Simulation("target/", env, 20);

        for (int i = 0; i < agents.size(); i++) {
            simulation.addAgentMiddleEnvironment(agents.get(i));
        }

        simulation.scheduleEnvironmentExploredRenderer("long-explored-10.png", 10, TimeUnit.SECONDS);
        simulation.scheduleEnvironmentExploredRenderer("long-explored-20.png", 20, TimeUnit.SECONDS);
        simulation.scheduleEnvironmentExploredRenderer("long-explored-40.png", 40, TimeUnit.SECONDS);
        simulation.scheduleEnvironmentExploredRenderer("long-explored-80.png", 80, TimeUnit.SECONDS);
        simulation.scheduleEnvironmentExploredRenderer("long-explored-160.png", 160, TimeUnit.SECONDS);

        simulation.run(160, TimeUnit.SECONDS);
    }
}
