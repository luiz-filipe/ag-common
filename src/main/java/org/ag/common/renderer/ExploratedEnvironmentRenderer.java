package org.ag.common.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.ag.common.simulation.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple task to scan an environment and render a image that represents the
 * space explored by the agents at that point in time.
 * 
 * @author luiz@luizabrahao.com
 * 
 */
public class ExploratedEnvironmentRenderer extends AbstractRenderer {
	private static final Logger logger = LoggerFactory
			.getLogger(ExploratedEnvironmentRenderer.class);

	private final Color colourEnv;
	private final Color colorVisitedNode;

	public ExploratedEnvironmentRenderer(final Environment environment) {
		super(environment);

		this.colourEnv = new Color(255, 255, 255, 0);
		this.colorVisitedNode = new Color(0, 0, 0, 255);
	}

	public ExploratedEnvironmentRenderer(final Environment environment,
			final Color colorVisitedNode) {
		super(environment);

		this.colourEnv = new Color(255, 255, 255, 0);
		this.colorVisitedNode = colorVisitedNode;
	}

	public ExploratedEnvironmentRenderer(final Environment environment,
			final Color colourEnv, final Color colorVisitedNode) {
		super(environment);

		this.colourEnv = colourEnv;
		this.colorVisitedNode = colorVisitedNode;
	}

	@Override
	public BufferedImage call() throws Exception {
		final BufferedImage bufferedImage = new BufferedImage(
				environment.getWidth(), environment.getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		final Graphics2D g2d = bufferedImage.createGraphics();

		for (int l = 0; l < environment.getHeight(); l++) {
			if (Thread.currentThread().isInterrupted()) {
				logger.info("Explored environment renderer was interrupted "
						+ "and will not complete...");

				g2d.dispose();
				break;
			}

			for (int c = 0; c < environment.getWidth(); c++) {
				if (environment.getNodeAt(l, c).getAgents() == null) {
					g2d.setColor(colourEnv);

				} else {
					g2d.setColor(colorVisitedNode);
				}

				g2d.drawLine(c, l, c, l);
			}
		}

		g2d.dispose();
		logger.trace("Finished rendering environment explored image.");

		return bufferedImage;
	}
}
