package org.ag.test.common.renderer;

import org.ag.common.agent.Agent;
import org.ag.common.renderer.ExploratedEnvironmentRenderer;
import org.ag.common.renderer.Renderer;
import org.ag.common.renderer.SingleRendererImageWriter;
import org.ag.common.simulation.BasicEnvironment;
import org.ag.common.simulation.Environment;
import org.ag.test.common.mock.TestTaskAgent;
import org.junit.Test;

public class SingleRendererImageWriterTest {

	@Test
	public void renderTest() {
		final Environment env = new BasicEnvironment(200, 200);
		final Agent a = new TestTaskAgent("a");

		env.placeAgentAtTheMiddle(a);
		
		final Renderer r = new ExploratedEnvironmentRenderer(env);
		final SingleRendererImageWriter ir = new SingleRendererImageWriter(r,
				"target/single-test.png");
		
		ir.write();
	}
}
