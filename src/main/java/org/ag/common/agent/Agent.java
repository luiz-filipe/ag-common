package org.ag.common.agent;

import java.util.List;
import java.util.concurrent.Callable;

import org.ag.common.env.Node;

/**
 * The public API for every agent defined in the simulation.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public interface Agent extends Callable<Void> {
    /**
     * Every agent must have an identifier, and this should be used in the
     * hashCode() and equals().
     *
     * @return String a unique identifier.
     */
    String getId();

    /**
     * Returns agent's cast
     *
     * @return Cast agent's cast
     */
    AgentType getAgentType();

    /**
     * The node that the agent is currently sat on. This must be thread safe
     *
     * @param node
     *            Node a node
     */
    void setCurrentNode(final Node node);

    /**
     * Returns the node the agent is sat on. This must be thread safe
     *
     * @return Node
     */
    Node getCurrentNode();

    /**
     * An agent might hold a list of nodes that it has visited, this method
     * allows to a node to be added to this list. Agents that do implement the
     * list should initialise it lazily.
     *
     * @param node
     *            Node to add to the list of nodes that have been visited.
     */
    void addToVisitedHistory(final Node node);

    /**
     * Returns the list of nodes that the agent has visited. Node that this list
     * must be unmodifiable to ensure thread safety.
     *
     * @return List of nodes
     */
    List<Node> getNodesVisited();

    /**
     * Agents should define a flag that is used to know if the history of nodes
     * visited by the agent should be recorded or not. This method returns the
     * status of that flag.
     *
     * @return boolean the status of the flag.
     */
    boolean shouldRecordNodeHistory();
}