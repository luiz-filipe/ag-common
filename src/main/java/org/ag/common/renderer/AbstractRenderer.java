package org.ag.common.renderer;

import org.ag.common.simulation.Environment;

public abstract class AbstractRenderer implements Renderer {
	protected final Environment environment;

	public AbstractRenderer(Environment environment) {
		this.environment = environment;
	}
}
