package org.ag.common.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.ag.common.task.ScheduledTaskWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RendererManager {
	private static final Logger logger = LoggerFactory.getLogger(RendererManager.class);
	private static final int defaultNThreads = 10;
	private final String basePath;
	private List<RenderedImage> renderedImages;
	private final ScheduledExecutorService executor;
	private final List<ScheduledTaskWrapper<RenderedImage>> renderers;
	private final List<ScheduledFuture<RenderedImage>> renderersFutures;
	private final List<Future<Void>> writersFutures;
	private final Map<String, String[]> imageGroupings;
	
	public RendererManager(String basePath) {
		this.basePath = basePath;
		this.renderedImages = new ArrayList<RenderedImage>();
		this.executor = Executors.newScheduledThreadPool(defaultNThreads);
		this.renderers = new ArrayList<ScheduledTaskWrapper<RenderedImage>>();
		this.renderersFutures = new ArrayList<ScheduledFuture<RenderedImage>>();
		this.imageGroupings = new HashMap<String, String[]>();
		this.writersFutures = new ArrayList<Future<Void>>();
		
	}
	
	public RendererManager(String basePath, int numberOfThreads) {
		this.basePath = basePath;
		this.renderedImages = new ArrayList<RenderedImage>();
		this.renderers = new ArrayList<ScheduledTaskWrapper<RenderedImage>>();
		this.renderersFutures = new ArrayList<ScheduledFuture<RenderedImage>>();
		this.executor = Executors.newScheduledThreadPool(numberOfThreads);
		this.imageGroupings = new HashMap<String, String[]>();
		this.writersFutures = new ArrayList<Future<Void>>();
	}
	
	public void scheduleRender(Renderer renderer, String filename, long delay, TimeUnit unit) {
		renderers.add(new ScheduledTaskWrapper<RenderedImage>(renderer, delay, unit));
	}

	public void writeComposeImage(String name, String[] images) {
		this.imageGroupings.put(name, images);
	}

	public void run() {
		logger.debug("Starting renderer manager...");
		
		for (ScheduledTaskWrapper<RenderedImage> task : renderers) {
			renderersFutures.add(executor.schedule(task.getTask(), task.getDelay(), task.getUnit()));
		}
		
		try {
			for(ScheduledFuture<RenderedImage> future : renderersFutures) {
				renderedImages.add(future.get());
			}
			
		} catch (InterruptedException e) {
			logger.error("Image writer did not complete due interruption of " +
					"renderer: {}", basePath);
			return;
		
		} catch (ExecutionException e) {
			logger.error("Image writer did not complete due to a excution " +
					"problem: {}", basePath);
			
			logger.error(e.getCause().toString());
			return;
		}
		
		// write the images in disk
		for (RenderedImage image : renderedImages) {
			writersFutures.add(executor.submit(new SingleRendererImageWriter(basePath, image)));
		}
		
		for (Future<Void> writerFuture : writersFutures) {
			try {
				writerFuture.get();
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		// write composite images in disk
		for (Map.Entry<String, String[]> entry : imageGroupings.entrySet()) {
			final String finalName = entry.getKey();
			final String[] imagesNames = entry.getValue();
			
			executor.submit(new ComposedImageWriter(basePath, finalName, imagesNames));
		}
		renderedImages = null;
		executor.shutdown();
		
		try {
			if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
				logger.error("RendererManager shutdown timeout, forcing shutdown...");
			}

		} catch (InterruptedException e) {
			executor.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}