package org.ag.common.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ag.common.annotation.FrameworkExclusive;
import org.ag.common.env.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Base implementation of Agent interface.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 *
 */
@ThreadSafe
public abstract class AbstractAgent implements Agent {
    private static final Logger logger = LoggerFactory
            .getLogger(AbstractAgent.class);

    protected final String id;
    protected final AgentType agentType;
    protected final boolean recordNodeHistory;
    @GuardedBy("this")
    protected Node currentNode;
    @GuardedBy("this")
    protected List<Node> nodesVisited = null;

    /**
     * Constructs a new Agent with an id, a type, the node it will start from in the environment, and if it should keep
     * track of the nodes it has been.
     *
     * @param id agent's unique identifier.
     * @param agentType agent's type.
     * @param currentNode the node the agent will start from.
     * @param recordNodeHistory whether the agent should keep track of the nodes it has been or not.
     */
    public AbstractAgent(final String id, final AgentType agentType,
                         final Node currentNode, final boolean recordNodeHistory) {

        this.id = id;
        this.agentType = agentType;
        this.recordNodeHistory = recordNodeHistory;
        currentNode.addAgentStartingHere(this);
    }
    /**
     * Constructs a new Agent with an id, a type and if it should keep track of the nodes it has been.
     *
     * @param id agent's unique identifier.
     * @param agentType agent's type.
     * @param recordNodeHistory whether the agent should keep track of the nodes it has been or not.
     */
    public AbstractAgent(final String id, final AgentType agentType,
                         final boolean recordNodeHistory) {

        this.id = id;
        this.agentType = agentType;
        this.recordNodeHistory = recordNodeHistory;

    }
    /**
     * Constructs a new Agent with an id, a type. Agents created with this constructor will not track the nodes they
     * will visit during a simulation.
     *
     * @param id agent's unique identifier.
     * @param agentType agent's type.
     */
    public AbstractAgent(final String id, final AgentType agentType) {
        this.id = id;
        this.agentType = agentType;
        this.recordNodeHistory = false;
    }

    /**
     * Set the node the agent is currently at. This method might seem a bit strange because it seems to be 'passive',
     * that is, some outside object will set where the agent is in the environment instead of the agent itself. This is
     * necessary because usually agents are placed in the environment at the beginning of the simulation by some factory
     * method. Also, because agents are defined in terms of tasks, the task that the agent chooses to execute has access
     * to its internal state and might determine where the agent goes next in the environment.
     *
     * <p>An important note is that the object that sets changes the agent's current node should call the
     * <i>Node.addAgent()</i> method, not set it directly, this ensures that the the Agent is added to the new node and
     * removed form the old one.</p>
     *
     * @see Node
     * @param node node that is going to be set as current.
     */
    @FrameworkExclusive
    @Override
    public synchronized void setCurrentNode(final Node node) {
        this.currentNode = node;
    }

    /**
     * Return the node that the agent is currently at.

     * @return current node agent is at.
     */
    @Override
    public synchronized Node getCurrentNode() {
        return currentNode;
    }

    /**
     * Returns the agent's unique identifier. Note that the <i>Environment</i> or <i>Agent</i> APIs do not place any
     * enforcement that guarantees that users' identifiers in an particular environment are unique, it's up to the user
     * to make sure that they don't create agents with duplicated IDs.

     * @return agent's unique identifier.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Returns the <i>AgentType</i> that defines the agent.
     *
     * @see AgentType
     * @return agent's type.
     */
    @Override
    public AgentType getAgentType() {
        return agentType;
    }

    /**
     * Adds the given node to the history of the nodes visited by the agent. This is something like a memory that the
     * agent has of the places it has been. It might be useful if the user is interested to know the path taken by a
     * particular agent throughout a simulation.
     *
     * @param node node to be added to the agent's history of nodes visited.
     */
    @Override
    public void addToVisitedHistory(final Node node) {
        synchronized (this) {
            if (nodesVisited == null) {
                nodesVisited = Collections.synchronizedList(new ArrayList<Node>());
            }
        }

        nodesVisited.add(node);
    }

    /**
     * Returns a unmodifiable version of the list of the nodes visited by the agent.

     * @return list of nodes visited by the agent.
     */
    @Override
    public synchronized List<Node> getNodesVisited() {
        if (!this.recordNodeHistory) {
            logger.error("Node {} wasn't asked to record the list of nodes it has been, but the recordHistoryNode " +
                    "has tried to be accessed.", this.getId());

            return new ArrayList<Node>();
        }

        if (nodesVisited != null) {
            return Collections.unmodifiableList(nodesVisited);
        }

        if (this.recordNodeHistory) {
            logger.warn("{} has no node in the visited list, but was asked to record its moving history", this.getId());

            return new ArrayList<Node>();
        }

        return null;
    }

    /**
     * When the agent is created the user should tell the framework if they want it to keep track of the places that the
     * agent has been or not. Clients can use this method to check that.
     *
     * @return true if the agent was asked to keep track of the nodes it has been, false otherwise.
     */
    @Override
    public boolean shouldRecordNodeHistory() {
        return recordNodeHistory;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((agentType == null) ? 0 : agentType.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof AbstractAgent)) {
            return false;
        }

        AbstractAgent other = (AbstractAgent) obj;
        if (agentType == null) {
            if (other.agentType != null)
                return false;
        } else if (!agentType.equals(other.agentType))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        if (!other.canEqual(this)) {
            return false;
        }

        return true;
    }

    public boolean canEqual(final Object obj) {
        return (obj instanceof AbstractAgent);
    }
}
