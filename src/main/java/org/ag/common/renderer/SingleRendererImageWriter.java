package org.ag.common.renderer;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleRendererImageWriter extends AbstractImageWriter {
	private static final Logger logger = LoggerFactory
			.getLogger(SingleRendererImageWriter.class);

	private final RenderedImage image;
	
	public SingleRendererImageWriter(String path, RenderedImage image) {
		super(path);
		this.image = image;
	}
	
	@Override
	public Void call() {
		logger.debug("Writing '{}'...", image.getName());
		
		if (image != null) {
			try {
				File file = new File(path + "/" + image.getName());
				ImageIO.write(image.getImage(), "png", file);
				logger.debug("SingleRendererImageWriter finished for: {}", path);
				
			} catch (IOException e) {
				logger.error("Error rendering explored space. Could not write "
						+ "image at: {}", path);
				logger.error(e.getMessage());
			}

		} else {
			logger.warn("Image '{}' is null, will not write it in disk.", image.getName());
		}
		
		return null;
	}

}
