package org.ag.common.renderer;

import org.ag.common.simulation.Environment;

import java.util.concurrent.Callable;

/**
 * A renderer is a task that outputs an image that represents the state of the environment at the time the renderer was
 * run by the client.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
public interface Renderer extends Callable<RenderedImage> {
    /**
     * Return the name that will be used for the image when writing it to disk.
     *
     * @return image's name
     */
    String getOutputName();

    /**
     * Returns the environment that the renderer is performing against.
     *
     * @return environment that the renderer will render.
     */
    Environment getEnvironment();
}
