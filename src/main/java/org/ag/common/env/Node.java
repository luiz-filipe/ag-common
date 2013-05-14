package org.ag.common.env;

import java.util.List;

import org.ag.common.agent.Agent;
import org.ag.common.annotation.FrameworkExclusive;
import org.ag.common.annotation.ThreadSafetyBreaker;

/**
 * This class represents a piece of the environment, one could say it is a
 * infinitesimal representation of the environment. A node as a representation
 * of space is able to accommodate agents.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
public interface Node {
	/**
	 * Each agent has an unique identifier, this is going to be used during the
	 * data analysis.
	 * 
	 * @return String unique identifier 
	 */
	String getId();

	/**
	 * Adds a agent to this node, it takes care of both directions connecting
	 * both node to the agent and the agent to the node.
	 * 
	 * @param agent
	 *            Agent that is going to be added to the node.
	 */
	void addAgent(final Agent agent);

	/**
	 * Returns the neighbour node of the specified direction. In case the node
	 * is part of the boundary in a determined direction it will not have any
	 * neighbour in that direction, so null will be returned instead.
	 * 
	 * IMPORTANT: This method is not thread-safe! This means that you must not
	 * use it in simulations that the environment changes, that is, the nodes'
	 * neighbours change. This method should be used only to transverse the
	 * nodes graph. The reason for not making this method thread-safe is
	 * performance. This method is likely to be called very often during the
	 * simulations, so it wouldn't make sense to make it thread-safe if there
	 * are no plans to use dynamic environments.
	 * 
	 * @param direction
	 *            Direction of the neighbour node related to the current object.
	 * @return Node neighbour node.
	 */
	@ThreadSafetyBreaker
	Node getNeighbour(final Direction direction);

	/**
	 * This method should not be called directly from your code. As the
	 * neighbours node are not exposed explicitly, this method creates the means
	 * to set a neighbour in a particular direction.
	 * 
	 * DO NOT CALL this method in your code, use setNeighbours instead, it makes
	 * sure both of your node objects are liked to each other.
	 * 
	 * IMPORTANT: This method is not thread-safe! This means that you must not
	 * use it in simulations that the environment changes, that is, the nodes'
	 * neighbours change. This method is to be used only during the environment
	 * setup.
	 * 
	 * @param direction
	 *            Direction of the neighbour node related to the current object.
	 * @param node
	 *            Node neighbour node.
	 */
	@FrameworkExclusive
	@ThreadSafetyBreaker
	void setNeighbour(final Direction direction, final Node node);

	/**
	 * Firstly this set the node passed as argument as the neighbour of the
	 * instance node, after that, it does the same for the neighbour node but in
	 * the opposite direction.
	 * 
	 * IMPORTANT: This method is not thread-safe! This means that you must not
	 * use it in simulations that the environment changes, that is, the nodes'
	 * neighbours change. This method should be used only to transverse the
	 * nodes graph. The reason for not making this method thread-safe is
	 * performance. This method is likely to be called very often during the
	 * simulations, so it wouldn't make sense to make it thread-safe if there
	 * are no plans to use dynamic environments.
	 * 
	 * @param direction
	 *            Direction of the neighbour node related to the current object.
	 * @param node
	 *            Node neighbour node.
	 */
	@FrameworkExclusive
	@ThreadSafetyBreaker
	void setNeighbours(final Direction direction, final Node node);

	/**
	 * Returns the list of agents present in the node
	 * 
	 * @return List Agents present in the node
	 */
	List<Agent> getAgents();

	/**
	 * Places an agent in the node. This method should be used only when
	 * allocating agents to node for the first time in the agent's life-cycle.
	 * 
	 * Usualy the method is called right after creating a new agent.
	 * 
	 * @param agent
	 *            Agent to add to the node
	 */
	void addAgentStartingHere(final Agent agent);

	/**
	 * Returns the list of communication stimulus present in the node.
	 * 
	 * @see CommunicationStimulus
	 * 
	 * @return List Communication stimuli present in the node
	 */
	List<CommunicationStimulus> getCommunicationStimuli();

	/**
	 * Add a new communication stimulus to the node. It's recommended to use
	 * lazy initialisation for the stimulus list as only nodes visited by agents
	 * need to have one.
	 * 
	 * @param communicationStimulus
	 *            Communication stimulus to add to node
	 */
	void addCommunicationStimulus(
			final CommunicationStimulus communicationStimulus);

	/**
	 * Returns the communication stimulus of the requested type present in the
	 * node. If there is no communication stimulus of that particular type it
	 * returns null.
	 * 
	 * @param communicationStimulusType
	 *            The type of the communication stimulus
	 * @return CommunicationStimulus Communication stimulus present in the node
	 * 
	 * @see CommunicationStimulusType
	 */
	CommunicationStimulus getCommunicationStimulus(
			final CommunicationStimulusType communicationStimulusType);
	
	/**
	 * This method removes all links from the node to its neighbours. It's used
	 * when a node is replaced by other such as nodes from an environment
	 * element.
	 */
	void disconnectFromNeighbours();
} 