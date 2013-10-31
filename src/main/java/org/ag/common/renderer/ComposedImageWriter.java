package org.ag.common.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.jcip.annotations.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Image writer that combines multiple images already rendered into one.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
@ThreadSafe
public class ComposedImageWriter extends AbstractImageWriter {
    private static final Logger logger = LoggerFactory.getLogger(ComposedImageWriter.class);

    private final String[] imagesNames;
    private final String name;

    /**
     * Constructs image writer with the path the image will be written to, the image's filename and the name of the
     * image files that are going to be combined to form the final image.
     *
     * @param path folder image will be written to.
     * @param name image's filename.
     * @param imagesNames images filename that will be combined into final image.
     */
    public ComposedImageWriter(String path, String name, String[] imagesNames) {
        super(path);

        // make sure the filename of the base files have '.png' at the end
        this.name = AbstractRenderer.appendPNGExtension(name);

        for (int i = 0; i < imagesNames.length; i++) {
            imagesNames[i] = AbstractRenderer.appendPNGExtension(imagesNames[i]);
        }

        this.imagesNames = imagesNames;
    }

    /**
     * Combines the given files disk into a single image. This is used to create complex rendered images made by
     * previously rendered images.

     * @return Void
     * @throws Exception if not able to complete task.
     */
    @Override
    public Void call() throws Exception {
        if (imagesNames == null) {
            logger.warn("Images name is null, will not write final image into disk: '{}'", this.getFullPath());
            return null;
        }

        if (imagesNames.length == 0) {
            logger.warn("Images name is empty, will not write final image into disk: '{}'", this.getFullPath());
            return null;
        }

        try {
            final File file = new File(this.getFullPath());
            file.mkdirs();
            file.createNewFile();

            final File firstImageFile = new File(path + File.separator + imagesNames[0]);
            final BufferedImage firstImage = ImageIO.read(firstImageFile);

            logger.debug("Starting writing composed image: '{}'", this.getFullPath());

            final BufferedImage finalImage = new BufferedImage(firstImage.getWidth(), firstImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);

            final Graphics g = finalImage.createGraphics();
            g.drawImage(firstImage, 0, 0, null);

            for (int i = 1; i < imagesNames.length; i++) {
                final File imageFile = new File(path + File.separator + imagesNames[i]);
                final BufferedImage image = ImageIO.read(imageFile);

                g.drawImage(image, 0, 0, null);
            }

            g.dispose();
            ImageIO.write(finalImage, "png", file);

            logger.info("Finished writing composed image: '{}'", this.getFullPath());
            return null;

        } catch (IOException e) {
            logger.error("Error while writing image to disk: '{}'", this.getFullPath());
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public String getFullPath() {
        return this.path + File.separator + this.name;
    }
}
