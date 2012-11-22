package org.ag.common.renderer;

import java.util.concurrent.Callable;

public class ImageWriterTask implements Callable<Void>{

	private final ImageWriter imageWriter;

	public ImageWriterTask(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
	}
	
	@Override
	public Void call() throws Exception {
		this.imageWriter.write();
		return null;
	}

}
