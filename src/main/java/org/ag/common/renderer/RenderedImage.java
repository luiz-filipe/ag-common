package org.ag.common.renderer;

import java.awt.image.BufferedImage;

import net.jcip.annotations.Immutable;

/**
 * A rendered image is a product of a renderer execution. It contains a <i>BufferedImage</i> and a name that is used
 * when writing the image to disk.
 *
 * <p>This object part of the framework's renderer mechanism. The actual rendering has been separated from writing the
 * image in disk, this decision was taken because writing the images is disk is considerable slower than in memory,
 * and having a collection of <i>RenderedImage</i> opens up the opportunity of manipulating them before writing to disk.
 * </p>
 *
 * <p>This class is immutable.</p>
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
@Immutable
public class RenderedImage {
    private final String name;
    private final BufferedImage image;

    public RenderedImage(String name, BufferedImage image) {
        this.name = name;
        this.image = image;
    }

    /**
     * Returns the image's name used when writing it to disk.
     *
     * @return image's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the actual rendered image.
     *
     * @return rendered image.
     */
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof RenderedImage))
            return false;
        RenderedImage other = (RenderedImage) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
