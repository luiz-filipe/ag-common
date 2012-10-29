package org.ag.common.agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ag.common.env.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Base implementation of Agent interface. It also implements Runnable in order
 * to force subclasses to be able to be ran in different threads.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
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

	public AbstractAgent(final String id, final AgentType agentType,
			final Node currentNode, final boolean recordNodeHistory) {

		this.id = id;
		this.agentType = agentType;
		this.recordNodeHistory = recordNodeHistory;
		currentNode.addAgentStartingHere(this);
	}

	public AbstractAgent(final String id, final AgentType agentType,
			final boolean recordNodeHistory) {

		this.id = id;
		this.agentType = agentType;
		this.recordNodeHistory = recordNodeHistory;

	}

	public AbstractAgent(final String id, final AgentType agentType) {
		this.id = id;
		this.agentType = agentType;
		this.recordNodeHistory = false;
	}

	/**
	 * This method is not normally called directly form user code. In normal
	 * circumstances the current node is set by the node.addAgent() method.
	 * Which makes sure both sides of the relationship are defined.
	 * 
	 * @see Node
	 * @param Node
	 *            node is going to be set as current.
	 */
	@Override
	public synchronized void setCurrentNode(final Node node) {
		this.currentNode = node;
	}

	@Override
	public synchronized Node getCurrentNode() {
		return currentNode;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public AgentType getAgentType() {
		return agentType;
	}

	@Override
	public void addToVisitedHistory(final Node node) {
		synchronized (this) {
			if (nodesVisited == null) {
				nodesVisited = Collections
						.synchronizedList(new ArrayList<Node>());
			}
		}

		nodesVisited.add(node);
	}

	@Override
	public synchronized List<Node> getNodesVisited() {
		if (!this.recordNodeHistory) {
			logger.error("Node {} wasn't asked to record the list of nodes "
					+ "it has been, but the recordHistoryNode has tried to be"
					+ " accessed.", this.getId());

			return new ArrayList<Node>();
		}

		if (nodesVisited != null) {
			return nodesVisited;
		}

		if (this.recordNodeHistory) {
			logger.warn("{} has no node in the visited list, but was asked "
					+ "to recorcord its moving history", this.getId());

			return new ArrayList<Node>();
		}

		return null;
	}

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
