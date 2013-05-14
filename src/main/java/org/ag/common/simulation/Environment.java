package org.ag.common.simulation;

import java.util.List;

import org.ag.common.agent.Agent;
import org.ag.common.env.EnvironmentElement;
import org.ag.common.env.Node;

public interface Environment {
	void placeAgentAt(final Agent agent, final int line, final int column);
	void placeAgentAtTheMiddle(final Agent agent);
	public Node getNodeAt(final int line, final int column);
	int getHeight();
	int getWidth();
	List<EnvironmentElement> getEnvironmentElements();
	void addEnvironmentElement(EnvironmentElement element, int x, int y);
	EnvironmentElement getEnvironmentElement(String id);

}
