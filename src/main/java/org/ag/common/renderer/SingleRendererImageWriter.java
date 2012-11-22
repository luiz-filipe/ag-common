package org.ag.common.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleRendererImageWriter implements ImageWriter {
	private static final Logger logger = LoggerFactory
			.getLogger(SingleRendererImageWriter.class);

	private final Renderer renderer;
	private final String path;

	public SingleRendererImageWriter(Renderer renderer, String path) {
		this.renderer = renderer;
		this.path = path;
	}
	
	@Override
	public void write() {
		final ExecutorService executor = Executors.newFixedThreadPool(1);
		final Future<BufferedImage> task = executor.submit(renderer);
		BufferedImage image = null;
		
		executor.shutdown();
		logger.debug("SingleRendererImageWriter starting for: {}", path);
		
		try {
			image = task.get();
			
		} catch (InterruptedException e) {
			logger.error("Image writer did not complete due interruption of " +
					"renderer: {}", path);
			return;
		
		} catch (ExecutionException e) {
			logger.error("Image writer did not complete due to a excution " +
					"problem: {}", path);
			
			logger.error(e.getCause().toString());
			return;
		}
		
		if (image != null) {
			try {
				File file = new File(path);
				ImageIO.write(image, "png", file);

				logger.debug("SingleRendererImageWriter finished for: {}", path);
				
			} catch (IOException e) {
				logger.error("Error rendering explored space. Could not write "
						+ "image at: {}", path);
				logger.error(e.getMessage());
			}
		}
	}

}
