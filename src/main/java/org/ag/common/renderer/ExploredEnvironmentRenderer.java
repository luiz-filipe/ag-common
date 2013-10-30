package org.ag.common.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.jcip.annotations.ThreadSafe;
import org.ag.common.simulation.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This tasks navigates through every node in an environment and paints only the nodes that have been visited by agents.
 * If the painting colour is not defined at construction time black is used.
 *
 * @author luiz@luizabrahao.com
 */
@ThreadSafe
public class ExploredEnvironmentRenderer extends AbstractRenderer {
    private static final Logger logger = LoggerFactory.getLogger(ExploredEnvironmentRenderer.class);

    private final Color colourEnv;
    private final Color colourVisitedNode;

    /**
     * Constructs the renderer with an output name and the environment it will render. The non-visited nodes will be be
     * rendered as transparent and visited node black.
     *
     * @param outputName name of the output file.
     * @param environment environment to be rendered.
     */
    public ExploredEnvironmentRenderer(String outputName, Environment environment) {
        super(outputName, environment);

        this.colourEnv = new Color(255, 255, 255, 0);
        this.colourVisitedNode = new Color(0, 0, 0, 255);
    }

    /**
     * Constructs the renderer with an output name, the environment it will render and the colour the nodes that have
     * been visited will be rendered.
     *
     * @param outputName name of the output file.
     * @param environment environment to be rendered.
     * @param colourVisitedNode colour visited nodes will be rendered in.
     */
    public ExploredEnvironmentRenderer(String outputName, Environment environment, Color colourVisitedNode) {
        super(outputName, environment);

        this.colourEnv = new Color(255, 255, 255, 0);
        this.colourVisitedNode = colourVisitedNode;
    }

    /**
     * Constructs the renderer with an output name, the environment it will render and the colour the nodes that have
     * been visited will be rendered.
     *
     * @param outputName name of the output file.
     * @param environment environment to be rendered.
     * @param colourEnv colour nodes not visited will be rendered in.
     * @param colourVisitedNode colour visited nodes will be rendered in.
     */
    public ExploredEnvironmentRenderer(String outputName, Environment environment, Color colourEnv,
                                       Color colourVisitedNode) {
        super(outputName, environment);

        this.colourEnv = colourEnv;
        this.colourVisitedNode = colourVisitedNode;
    }

    /**
     * Navigates through the environment checking whether its nodes have been visited by the any agents or not, this is
     * done by checking if the node's agents list is null. All nodes initialise their agent list lazily in order to save
     * memory usage, so if this list is still null at the time the renderer visits the node, it means that no agent has
     * been there and the renderer will show that in the final image.
     *
     * <p>The renderer scans all nodes from a line in the environment before moving to the next one. Every time it is
     * done with one line, it checks if the current thread has been interrupted, if that is the case it logs that it
     * couldn't finish and stops.</p>
     *
     * @return rendered image of visited nodes.
     * @throws Exception if unable to finish rendering.
     */
    @Override
    public RenderedImage call() throws Exception {
        final BufferedImage bufferedImage = new BufferedImage( environment.getWidth(), environment.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        final Graphics2D g2d = bufferedImage.createGraphics();

        for (int l = 0; l < environment.getHeight(); l++) {
            if (Thread.currentThread().isInterrupted()) {
                logger.info("Explored environment renderer was interrupted and will not complete...");

                g2d.dispose();
                break;
            }

            for (int c = 0; c < environment.getWidth(); c++) {
                if (environment.getNodeAt(l, c).getAgents() == null) {
                    g2d.setColor(colourEnv);

                } else {
                    g2d.setColor(colourVisitedNode);
                }

                g2d.drawLine(c, l, c, l);
            }
        }

        g2d.dispose();
        logger.trace("Finished rendering environment explored image.");

        return new RenderedImage(outputName, bufferedImage);
    }
}
