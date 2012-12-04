package org.ag.common.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.ag.common.env.Direction;
import org.ag.common.env.EnvironmentElement;
import org.ag.common.env.Node;
import org.ag.common.simulation.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This renderer will render all present environment elements. It does not
 * sweeps the entire node grid in order to find the positions of the elements to
 * render, but it retrieves the list of environment elements from the
 * environment and calculates the vertical and horizontal offset of the element
 * in relation to the coordinate (0,0) of the environment.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
public class EnvironmentElementsRenderer extends AbstractRenderer {
	private static final Logger logger = LoggerFactory
			.getLogger(EnvironmentElementsRenderer.class);

	public EnvironmentElementsRenderer(Environment environment) {
		super(environment);
	}

	@Override
	public BufferedImage call() throws Exception {
		final BufferedImage bufferedImage = new BufferedImage(
				environment.getWidth(), environment.getHeight(),
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
				logger.warn("Environment elements renderer was interrupted "
						+ "and will not complete...");
				break;
			}

			neighbour = topLeftNode.getNeighbour(Direction.WEST);

			while (neighbour != null) {
				horizontalOffset++;
				neighbour = neighbour.getNeighbour(Direction.WEST);
			}

			g2d.setColor(element.getColour());
			g2d.drawRect(horizontalOffset, verticalOffset, horizontalOffset
					+ element.getDimension().width,
					verticalOffset + element.getDimension().height);
		}

		g2d.dispose();
		return bufferedImage;
	}
}
