package org.ag.common.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import org.ag.common.simulation.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class ComposedImageWriter implements ImageWriter {
	private static final Logger logger = LoggerFactory
			.getLogger(ComposedImageWriter.class);

	private int numberOfRenderers = 0;
	private final LinkedList<Renderer> renderers;
	private final Map<Integer, BufferedImage> layers;
	private final String path;
	private final Environment environment;

	public ComposedImageWriter(final Environment environment, final String path) {
		renderers = new LinkedList<Renderer>();
		layers = new HashMap<Integer, BufferedImage>();
		this.path = path;
		this.environment = environment;
	}

	public void addRenderer(final Renderer renderer) {
		renderers.add(renderer);
		numberOfRenderers++;
	}

	private void renderLayers() {
		final ExecutorService executor = Executors
				.newFixedThreadPool(numberOfRenderers);
		final Map<Integer, Future<BufferedImage>> futures = new HashMap<Integer, Future<BufferedImage>>();
		int rendererIndex = 0;

		logger.debug("Starting rendering layers for: {}", path);

		while (!renderers.isEmpty()) {
			futures.put(rendererIndex, executor.submit(renderers.removeFirst()));
			rendererIndex++;
		}

		executor.shutdown();

		for (int i = 0; i < futures.size(); i++) {
			try {
				layers.put(i, futures.get(i).get());

			} catch (InterruptedException e) {
				logger.error(
						"Not all renderers were able to complete for image '{}'",
						path);

			} catch (ExecutionException e) {
				logger.error("A renderer has thrown an exception...");
				logger.error(e.getCause().toString());
			}
		}
	}

	private void writeImage() {
		try {
			File file = new File(path);

			logger.debug("Starting writing composed image at: {}", path);

			final BufferedImage finalImage = new BufferedImage(
					environment.getWidth(),
					environment.getHeight(),
					BufferedImage.TYPE_INT_ARGB);

			final Graphics2D g2d = finalImage.createGraphics();

			for (int i = numberOfRenderers - 1; i >= 0; i--) {
				g2d.drawImage(layers.get(i), 0, 0, null);
			}

			g2d.dispose();
			ImageIO.write(finalImage, "png", file);

			logger.info("Finished writing composed image at: {}", path);

		} catch (IOException e) {
			logger.error("Error rendering explored space. Could not write "
					+ "image at: {}", path);
			logger.error(e.getMessage());
		}
	}

	@Override
	public void write() {
		this.renderLayers();
		this.writeImage();
	}
}
