package org.ag.test.common.renderer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.ag.common.agent.Agent;
import org.ag.common.renderer.ExploratedEnvironmentRenderer;
import org.ag.common.renderer.RenderedImage;
import org.ag.common.simulation.BasicEnvironment;
import org.ag.common.simulation.Environment;
import org.ag.test.common.mock.TestTaskAgent;
import org.junit.Test;

public class ExploratedEnvironmentRendererTest {
	@Test
	public void basicColourRender() throws InterruptedException, ExecutionException {
		final ExecutorService executor = Executors.newFixedThreadPool(1);
		
		final String path = "target/explorated-default.png";
		final Environment environment = new BasicEnvironment(3, 3);
		final Agent a = new TestTaskAgent("a");
		final ExploratedEnvironmentRenderer renderer = new ExploratedEnvironmentRenderer("test-exp-basiccolor.png", environment);
		
		environment.placeAgentAtTheMiddle(a);
		
		List<Callable<RenderedImage>> renderers = new ArrayList<Callable<RenderedImage>>();
		renderers.add(renderer);
		
		final List<Future<RenderedImage>> futures = executor.invokeAll(renderers);
		ImageWriter.writeImage(futures.get(0).get().getImage(), path);
	}
	
	@Test
	public void customColourRender() throws InterruptedException, ExecutionException {
		final ExecutorService executor = Executors.newFixedThreadPool(1);
		
		final Color colourEnv = new Color(81, 160, 37);
		final Color colourVisitedNode = new Color(83, 83, 77);
		final String path = "target/explorated-custom.png";
		final Environment environment = new BasicEnvironment(3, 3);
		final Agent a = new TestTaskAgent("a");
		final ExploratedEnvironmentRenderer renderer = new ExploratedEnvironmentRenderer("test-env-exp-color.png", environment, colourEnv, colourVisitedNode);
		
		environment.placeAgentAtTheMiddle(a);
		
		List<Callable<RenderedImage>> renderers = new ArrayList<Callable<RenderedImage>>();
		renderers.add(renderer);
		
		executor.invokeAll(renderers);
		
		final List<Future<RenderedImage>> futures = executor.invokeAll(renderers);
		ImageWriter.writeImage(futures.get(0).get().getImage(), path);
	}
	
	@Test
	public void renderTransparentBackground() throws InterruptedException, ExecutionException {
		final ExecutorService executor = Executors.newFixedThreadPool(1);
		final String path = "target/explorated-transparent.png";
		final Environment environment = new BasicEnvironment(300, 300);
		final Agent a = new TestTaskAgent("a");
		
		final ExploratedEnvironmentRenderer renderer = new ExploratedEnvironmentRenderer("test-env-exp-trans.png", environment, Color.red);

		environment.placeAgentAtTheMiddle(a);
		
		List<Callable<RenderedImage>> renderers = new ArrayList<Callable<RenderedImage>>();
		renderers.add(renderer);
		
		executor.invokeAll(renderers);
		
		final List<Future<RenderedImage>> futures = executor.invokeAll(renderers);
		ImageWriter.writeImage(futures.get(0).get().getImage(), path);
	}
}
