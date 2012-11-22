package org.ag.test.common.renderer;

import java.awt.Color;

import org.ag.common.agent.Agent;
import org.ag.common.renderer.ExploratedEnvironmentRenderer;
import org.ag.common.renderer.Renderer;
import org.ag.common.renderer.ComposedImageWriter;
import org.ag.common.simulation.BasicEnvironment;
import org.ag.common.simulation.Environment;
import org.ag.test.common.mock.TestTaskAgent;
import org.junit.Test;

public class ComposedImageWriterTest {
	@Test
	public void renderTwoLayers() {
		final Environment envA = new BasicEnvironment(200, 200);
		final Environment envB = new BasicEnvironment(200, 200);
		final Agent agentA = new TestTaskAgent("a");
		final Agent agentB = new TestTaskAgent("b");
		final Agent agentC = new TestTaskAgent("c");
		final Agent agentD = new TestTaskAgent("d");
		
		envA.placeAgentAt(agentA, 50, 50);
		envA.placeAgentAtTheMiddle(agentB);
		
		envB.placeAgentAt(agentC, 150, 150);
		envB.placeAgentAtTheMiddle(agentD);
		
		final Renderer r01 = new ExploratedEnvironmentRenderer(envA, Color.blue);
		final Renderer r02 = new ExploratedEnvironmentRenderer(envB, Color.red);
		final ComposedImageWriter rm = new ComposedImageWriter(envA, "target/manager-test.png");
		
		rm.addRenderer(r01);
		rm.addRenderer(r02);
		rm.write();
	}
}
