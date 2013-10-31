package org.ag.test.common.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class used by unit tests to write a <i>BufferedImage</i> into disk.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
public class TestImageWriter {
    private static final Logger logger = LoggerFactory.getLogger(TestImageWriter.class);

    private TestImageWriter() {}

    /**
     * Writes the given <i>BufferedImage</i> to disk in the folder specified by the <i>path</i> parameter.
     *
     * @param image image to be written in disk.
     * @param path folder image will be written into.
     */
    public static void writeImage(BufferedImage image, String path) {
        try {
            File file = new File(path);
            file.mkdirs();
            file.createNewFile();
            logger.debug("Starting writing environment explored image at: {}", path);
            ImageIO.write(image, "png", file);

        } catch (IOException e) {
            logger.error("Error rendering explored space. Could not write image at: {}", path);
            logger.error(e.getMessage());
        }
    }
}
