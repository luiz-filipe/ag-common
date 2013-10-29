package org.ag.common.renderer;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleRendererImageWriter extends AbstractImageWriter {
    private static final Logger logger = LoggerFactory.getLogger(SingleRendererImageWriter.class);
    private final RenderedImage image;

    public SingleRendererImageWriter(String path, RenderedImage image) {
        super(path);
        this.image = image;
    }

    @Override
    public Void call() {
        final String imagePath = path + File.separator + image.getName();

        if (image != null) {
            try {
                File file = new File(imagePath);
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

}
