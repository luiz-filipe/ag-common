package org.ag.common.simulation;

import org.ag.common.agent.Agent;
import org.ag.common.env.Node;

public interface Environment {
	void placeAgentAt(final Agent agent, final int line, final int column);
	void placeAgentAtTheMiddle(final Agent agent);
	public Node getNodeAt(final int line, final int column);
	int getNumberOfLines();
	int getNumberOfColumns();

}
