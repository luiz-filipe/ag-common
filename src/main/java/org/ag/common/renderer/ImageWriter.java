package org.ag.common.renderer;

import java.util.concurrent.Callable;

public interface ImageWriter extends Callable<Void> {
    /**
     * Returns the folder that the image will be written in.
     *
     * @return folder image will be written.
     */
    String getPath();

    /**
     * Returns the full path where the image will be written in, containing the folder and the image's name.
     *
     * @return full path where the image will be written.
     */
    String getFullPath();
}
