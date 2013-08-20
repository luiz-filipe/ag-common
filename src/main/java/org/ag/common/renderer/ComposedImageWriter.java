package org.ag.common.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class ComposedImageWriter extends AbstractImageWriter {
	private static final Logger logger = LoggerFactory
			.getLogger(ComposedImageWriter.class);

	private final String[] imagesNames;
	private final String name;

	public ComposedImageWriter(String path, String name, String[] imagesNames) {
		super(path);
		this.name = name;
		this.imagesNames = imagesNames;
	}

	@Override
	public Void call() {
		if (imagesNames == null) {
			logger.warn("Image '{}' is null, will not write it in disk.", name);
			return null;
		}
		
		if (imagesNames.length == 0) {
			logger.warn("Image '{}' is empty, will not write it in disk.", name);
			return null;
		}
		
		try {
			final File file = new File(path + "/" + name);
			final File firstImageFile = new File(path + "/" + imagesNames[0]);
			final BufferedImage firstImage = ImageIO.read(firstImageFile);

			logger.debug("Starting writing composed image at: {}", path);

			final BufferedImage finalImage = new BufferedImage(
					firstImage.getWidth(),
					firstImage.getHeight(),
					BufferedImage.TYPE_INT_ARGB);

			final Graphics g = finalImage.createGraphics();
			g.drawImage(firstImage, 0, 0, null);
			
			for (int i = 1; i < imagesNames.length; i++) {
				final File imageFile = new File(path + "/" + imagesNames[i]);
				final BufferedImage image = ImageIO.read(imageFile);
				
				g.drawImage(image, 0, 0, null);
			}
			

			g.dispose();
			ImageIO.write(finalImage, "png", file);

			logger.info("Finished writing composed image at: {}", path);
			return null;
			
		} catch (IOException e) {
			logger.error("Error rendering explored space. Could not write "
					+ "image at: {}", path);
			logger.error(e.getMessage());
		}
		
		return null;
	}
}
