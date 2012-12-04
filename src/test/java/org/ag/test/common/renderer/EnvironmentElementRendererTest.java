package org.ag.test.common.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.ag.common.renderer.EnvironmentElementsRenderer;
import org.ag.common.simulation.BasicEnvironment;
import org.ag.common.simulation.Environment;
import org.ag.test.common.mock.TestEnvElement;
import org.junit.Test;

public class EnvironmentElementRendererTest {
	@Test
	public void renderTest() throws Exception {
		final ExecutorService executor = Executors.newFixedThreadPool(1);
		final Environment environment = new BasicEnvironment(300, 300);
		final EnvironmentElementsRenderer renderer = new EnvironmentElementsRenderer(
				environment);
		final TestEnvElement e1 = new TestEnvElement("e1", new Dimension(10, 10),  Color.blue);
		final TestEnvElement e2 = new TestEnvElement("e2", new Dimension(20, 20),  Color.red);
		final TestEnvElement e3 = new TestEnvElement("e3", new Dimension(30, 30),  Color.green);
		
		environment.addEnvironmentElement(e1, 10, 10);
		environment.addEnvironmentElement(e2, 20, 20);
		environment.addEnvironmentElement(e3, 30, 30);
		
		List<Callable<BufferedImage>> renderers = new ArrayList<Callable<BufferedImage>>();
		renderers.add(renderer);
		
		executor.invokeAll(renderers);
		
		final List<Future<BufferedImage>> futures = executor.invokeAll(renderers);
		ImageWriter.writeImage(futures.get(0).get(), "target/env-elements.png");
	}
}
