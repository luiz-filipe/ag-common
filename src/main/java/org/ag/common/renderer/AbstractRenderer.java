package org.ag.common.renderer;

import org.ag.common.simulation.Environment;

public abstract class AbstractRenderer implements Renderer {
	protected final Environment environment;
	protected final String path;

	public AbstractRenderer(Environment environment, String path) {
		this.environment = environment;
		this.path = path;
	}
}
