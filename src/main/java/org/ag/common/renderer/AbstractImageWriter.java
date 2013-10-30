package org.ag.common.renderer;

import net.jcip.annotations.ThreadSafe;

/**
 * Provides basic implementation of <i>ImageWriter</i> interface.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
@ThreadSafe
public abstract class AbstractImageWriter implements ImageWriter {
    protected final String path;

    /**
     * Constructs a image writer with the path the image should be saved.
     *
     * @param path path to the folder image should be saved.
     */
    public AbstractImageWriter(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return this.path;
    }
}