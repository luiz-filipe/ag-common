package org.ag.common.renderer;

import java.awt.image.BufferedImage;

import net.jcip.annotations.Immutable;

@Immutable
public class RenderedImage {
	private final String name;
	private final BufferedImage image;
	
	public RenderedImage(String name, BufferedImage image) {
		this.name = name;
		this.image = image;
	}

	public String getName() {
		return name;
	}

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
