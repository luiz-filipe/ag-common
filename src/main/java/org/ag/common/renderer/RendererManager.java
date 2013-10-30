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

import net.jcip.annotations.ThreadSafe;
import org.ag.common.task.ScheduledTaskWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A renderer manger is used by simulations to schedule and run the tasks that render images. It contains its own thread
 * pool and it manages both parts of the process, rendering and writing the images in disk.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
@ThreadSafe
public class RendererManager {
    private static final Logger logger = LoggerFactory.getLogger(RendererManager.class);
    private static final int defaultTimeoutInSeconds = 30;
    private static final int defaultNThreads = 10;
    private final String basePath;
    private List<RenderedImage> renderedImages;
    private final ScheduledExecutorService executor;
    private final List<ScheduledTaskWrapper<RenderedImage>> renderers;
    private final List<ScheduledFuture<RenderedImage>> renderersFutures;
    private final List<Future<Void>> writersFutures;
    private final Map<String, String[]> imageGroupings;
    private long delayToStartTimeOut = 0;
    private TimeUnit delayToStartTimeOUnit = TimeUnit.SECONDS;

    /**
     * Creates a renderer manager with a path to a folder that will be used to save the images to.
     *
     * @param basePath path to folder that will contain the generated files.
     */
    public RendererManager(String basePath) {
        this.basePath = basePath;
        this.renderedImages = new ArrayList<RenderedImage>();
        this.executor = Executors.newScheduledThreadPool(defaultNThreads);
        this.renderers = new ArrayList<ScheduledTaskWrapper<RenderedImage>>();
        this.renderersFutures = new ArrayList<ScheduledFuture<RenderedImage>>();
        this.imageGroupings = new HashMap<String, String[]>();
        this.writersFutures = new ArrayList<Future<Void>>();

    }

    /**
     * Creates a renderer manager with a path to a folder that will be used to save the images to and the maximum number
     * of tasks that the manager can execute at the same time.
     *
     * @param basePath path to folder that will contain the generated files.
     * @param numberOfThreads size of the renderer manager's thread-pool.
     */

    public RendererManager(String basePath, int numberOfThreads) {
        this.basePath = basePath;
        this.renderedImages = new ArrayList<RenderedImage>();
        this.renderers = new ArrayList<ScheduledTaskWrapper<RenderedImage>>();
        this.renderersFutures = new ArrayList<ScheduledFuture<RenderedImage>>();
        this.executor = Executors.newScheduledThreadPool(numberOfThreads);
        this.imageGroupings = new HashMap<String, String[]>();
        this.writersFutures = new ArrayList<Future<Void>>();
    }

    public synchronized void setDelayToStartTimeOut(long delay, TimeUnit unit) {
        this.delayToStartTimeOut = delay;
        this.delayToStartTimeOUnit = unit;
    }

    /**
     * Schedule a renderer to execute from the time the <i>run</i> method is called.s
     *
     * @param renderer renderer to be executed.
     * @param delay delay until the renderer is executed.
     * @param unit time unite for the delay.
     */
    public void scheduleRender(Renderer renderer, long delay, TimeUnit unit) {
        renderers.add(new ScheduledTaskWrapper<RenderedImage>(renderer, delay, unit));
    }

    /**
     * Request a new composite image to be written based on the images with filename given.
     *
     * @param name composite image filename
     * @param images images that will be merged into composite image.
     */
    public void writeComposeImage(String name, String[] images) {
        this.imageGroupings.put(name, images);
    }

    /**
     * Schedules and submits the rendering and writing tasks to the pool.
     */
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
            // this make sure that the timeout tolerance will be start being count only after the simulation had time
            // to finish.
            if (!executor.awaitTermination(delayToStartTimeOut + delayToStartTimeOUnit.convert(defaultTimeoutInSeconds,
                    TimeUnit.SECONDS), delayToStartTimeOUnit)) {
                logger.error("RendererManager shutdown timeout, forcing shutdown...");
            }

        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}