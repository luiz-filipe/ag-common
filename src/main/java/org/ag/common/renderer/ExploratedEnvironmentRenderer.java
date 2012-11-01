package org.ag.common.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

	public ExploratedEnvironmentRenderer(Environment environment, String path) {
		super(environment, path);

		this.colourEnv = Color.white;
		this.colorVisitedNode = Color.black;
	}

	public ExploratedEnvironmentRenderer(Environment environment, String path,
			Color colourEnv, Color colorVisitedNode) {
		super(environment, path);
		
		this.colourEnv = colourEnv;
		this.colorVisitedNode = colorVisitedNode;
	}

	@Override
	public Void call() throws Exception {
		final BufferedImage bufferedImage = new BufferedImage(
				environment.getNumberOfColumns(),
				environment.getNumberOfLines(), BufferedImage.TYPE_INT_RGB);

		final Graphics2D g2d = bufferedImage.createGraphics();

		logger.debug("Starting rendering environment explored image.");

		for (int l = 0; l < environment.getNumberOfLines(); l++) {
			if (Thread.currentThread().isInterrupted()) {
				logger.info("Explored environment renderer was interrupted "
						+ "and will not complete for image: {}.", path);

				g2d.dispose();
				break;
			}

			for (int c = 0; c < environment.getNumberOfColumns(); c++) {
				if (environment.getNodeAt(l, c).getAgents() == null) {
					g2d.setColor(colourEnv);

				} else {
					g2d.setColor(colorVisitedNode);
				}

				g2d.fillRect(c, l, c + 1, l + 1);
			}
		}

		g2d.dispose();
		
		try {
			File file = new File(path);

			logger.debug("Starting writing environment explored image at: {}",
					path);
			ImageIO.write(bufferedImage, "png", file);

		} catch (IOException e) {
			logger.error("Error rendering explored space. Could not write "
					+ "image at: {}", path);
			logger.error(e.getMessage());
		}

		logger.debug("Finished rendering environment explored image at: {}",
				path);
		
		return null;
	}
}
