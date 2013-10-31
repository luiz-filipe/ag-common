package org.ag.common.renderer;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Image writer for images made by a single <i>RenderedImage</i> image.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
@ThreadSafe
public class SingleRendererImageWriter extends AbstractImageWriter {
    private static final Logger logger = LoggerFactory.getLogger(SingleRendererImageWriter.class);
    private final RenderedImage image;

    /**
     * Constructs the image writer with a path to the folder where the image will be saved in and the rendered image
     * itself.
     *
     * @param path path image file will be written to.
     * @param image image to be written in disk.
     */
    public SingleRendererImageWriter(String path, RenderedImage image) {
        super(path);
        this.image = image;
    }

    /**
     * Writes image to disk.
     *
     * @return Void
     * @throws Exception if task cannot finish.
     */
    @Override
    public Void call() throws Exception {
        final String imagePath = path + File.separator + image.getName();

        if (image != null) {
            try {
                File file = new File(imagePath);
                file.mkdirs();
                file.createNewFile();

                logger.debug("Starting writing image in disk: '{}'", file.getAbsolutePath());
                ImageIO.write(image.getImage(), "png", file);
                logger.debug("Finished writing image in disk: '{}'", file.getAbsolutePath());

            } catch (IOException e) {
                logger.error("Error rendering explored space. Could not write image: '{}'", imagePath);
                logger.error(e.getMessage());
            }

        } else {
            logger.warn("Image is null, won't be written in disk: '{}'", imagePath);
        }

        return null;
    }

    @Override
    public String getFullPath() {
        return this.path + File.separator + image.getName();
    }
}
