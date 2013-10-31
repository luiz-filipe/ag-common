package org.ag.test.common.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.ag.common.renderer.EnvironmentElementsRenderer;
import org.ag.common.renderer.RenderedImage;
import org.ag.common.simulation.BasicEnvironment;
import org.ag.common.simulation.Environment;
import org.ag.test.common.mock.TestEnvElement;
import org.junit.Test;

public class EnvironmentElementRendererTest {
    @Test
    public void renderTest() throws Exception {
        final ExecutorService executor = Executors.newFixedThreadPool(1);
        final Environment environment = new BasicEnvironment(300, 300);
        final EnvironmentElementsRenderer renderer = new EnvironmentElementsRenderer("test-env-elements.png", environment);
        final TestEnvElement e1 = new TestEnvElement("e1", new Dimension(10, 10),  Color.blue);
        final TestEnvElement e2 = new TestEnvElement("e2", new Dimension(20, 20),  Color.red);
        final TestEnvElement e3 = new TestEnvElement("e3", new Dimension(30, 30),  Color.green);

        environment.addEnvironmentElement(e1, 10, 10);
        environment.addEnvironmentElement(e2, 11, 11);
        environment.addEnvironmentElement(e3, 12, 12);

        List<Callable<RenderedImage>> renderers = new ArrayList<Callable<RenderedImage>>();
        renderers.add(renderer);

        executor.invokeAll(renderers);

        final List<Future<RenderedImage>> futures = executor.invokeAll(renderers);
        TestImageWriter.writeImage(futures.get(0).get().getImage(), "target/env-elements.png");
    }
}
