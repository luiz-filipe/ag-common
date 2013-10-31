package org.ag.common.renderer;

import net.jcip.annotations.ThreadSafe;
import org.ag.common.simulation.Environment;

/**
 * Base implementation of the <i>Renderer</i> interface. Provides the building blocks for creating more complex
 * renderer.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
@ThreadSafe
public abstract class AbstractRenderer implements Renderer {
    protected final Environment environment;
    protected final String outputName;

    /**
     * Constructs a renderer with a name for the output image and the environment it will render.
     *
     * @param outputName name used for the image when writing it in disk.
     * @param environment environment to be rendered.
     */
    public AbstractRenderer(String outputName, Environment environment) {
        this.environment = environment;
        this.outputName = appendPNGExtension(outputName);
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public String getOutputName() {
        return this.outputName;
    }

    /**
     * Makes sure the rendered image filename ends up with '.png' extension.
     *
     * @param filename rendered image filename.
     * @return filename with .png in the end if not present.
     */
    public static final String appendPNGExtension(String filename) {
        if (filename.endsWith(".png")) {
            return filename;
        }

        return filename + ".png";
    }
}
