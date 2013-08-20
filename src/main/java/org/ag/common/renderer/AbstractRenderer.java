package org.ag.common.renderer;

import org.ag.common.simulation.Environment;

public abstract class AbstractRenderer implements Renderer {
	protected final Environment environment;
	protected final String name;

	public AbstractRenderer(String name, Environment environment) {
		this.name = name;
		this.environment = environment;
	}
}
