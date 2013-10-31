package org.ag.common.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.jcip.annotations.ThreadSafe;
import org.ag.common.env.Direction;
import org.ag.common.env.EnvironmentElement;
import org.ag.common.env.Node;
import org.ag.common.simulation.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This renderer will render all present environment elements. It does not sweeps the entire node grid in order to find
 * the positions of the elements to render, but it retrieves the list of environment elements from the environment and
 * calculates the vertical and horizontal offset of the element in relation to the coordinate (0,0) of the environment.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
@ThreadSafe
public class EnvironmentElementsRenderer extends AbstractRenderer {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentElementsRenderer.class);

    /**
     * Constructs a renderer with an name for the output file and an environment.
     *
     * @param outputName output file's name
     * @param environment environment to be rendered.
     */
    public EnvironmentElementsRenderer(String outputName, Environment environment) {
        super(outputName, environment);
    }

    /**
     * Generates a <i>RenderedImage</i> containing all the environment elements present in the given environment.
     * Firstly it queries the environment for a list of all present elements, then it locates the position offset of
     * each element form the environment's top-left corner (0, 0). With the offset and the element's size the task knows
     * where it should render it in the image.
     *
     * @return rendered image with all environment elements.
     * @throws Exception if renderer cannot finish rendering image.
     */
    @Override
    public RenderedImage call() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage(environment.getWidth(), environment.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g2d = bufferedImage.createGraphics();

        for (EnvironmentElement element : environment.getEnvironmentElements()) {
            final Node topLeftNode = element.getNode(0, 0);
            Node neighbour = topLeftNode.getNeighbour(Direction.NORTH);
            int verticalOffset = 0;
            int horizontalOffset = 0;

            while (neighbour != null) {
                verticalOffset++;
                neighbour = neighbour.getNeighbour(Direction.NORTH);
            }

            if (Thread.currentThread().isInterrupted()) {
                logger.warn("Environment elements renderer was interrupted and won't complete for: '{}'", outputName);
                break;
            }

            neighbour = topLeftNode.getNeighbour(Direction.WEST);

            while (neighbour != null) {
                horizontalOffset++;
                neighbour = neighbour.getNeighbour(Direction.WEST);
            }

            g2d.setColor(element.getColour());
            g2d.drawRect(horizontalOffset, verticalOffset, element.getDimension().width, element.getDimension().height);
        }

        g2d.dispose();
        return new RenderedImage(outputName, bufferedImage);
    }
}
