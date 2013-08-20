package org.ag.common.renderer;

import java.util.concurrent.Callable;

public abstract class AbstractImageWriter implements Callable<Void> {
	protected final String path;
	
	public AbstractImageWriter(String path) {
		this.path = path;
	}
}
