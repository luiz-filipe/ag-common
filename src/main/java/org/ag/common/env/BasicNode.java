package org.ag.common.env;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ag.common.agent.Agent;
import org.ag.common.annotation.PseudoThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jcip.annotations.GuardedBy;

/**
 * This class is the basic implementation of the <i>Node</i> interface. It hold references to neighbour nodes and has
 * the utility methods to navigate through them. Its neighbour nodes are represented by the <i>north</i>,
 * <i>northEast</i>, <i>east</i>, <i>southEast</i>, <i>south</i>, <i>southWest</i>, <i>west</i>, <i>northWest</i>.
 *
 * <p>It is essential to keep this class as lightweight as possible, for it is
 * extensively used, a simulation with a reasonable resolution like 300 lines by 100 columns will have 30 000 objects
 * of this class.</p>
 *
 * Note that this class is thread-safe as far as the agents are concerned. The methods <i>getNeighbour</i>,
 * <i>setNeighbour</i> and <i>setNeighbours</i> do expose the neighbour nodes, but they were deliberately left without
 * synchronisation because they must be used only at setup time, that is, the environment does not change after the
 * simulation starts. getNeighbour is extensively used throughout the simulation, so the overhead added by the
 * synchronisation would not pay off.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
@PseudoThreadSafe
public class BasicNode implements Node {
    private static final Logger logger = LoggerFactory.getLogger(BasicNode.class);

    private final String id;
    private Node north = null;
    private Node northEast = null;
    private Node east = null;
    private Node southEast = null;
    private Node south = null;
    private Node southWest = null;
    private Node west = null;
    private Node northWest = null;

    @GuardedBy("this")
    private List<Agent> agents = null;

    @GuardedBy("this")
    private List<CommunicationStimulus> communicationStimuli = null;

    /**
     * Constructs a BasicNode with a unique identifier.

     * @param id node's unique identifier.
     */
    public BasicNode(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public synchronized List<Agent> getAgents() {
        return agents;
    }

    @Override
    public synchronized List<CommunicationStimulus> getCommunicationStimuli() {
        return communicationStimuli;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!(obj instanceof BasicNode)) {
            return false;
        }

        BasicNode other = (BasicNode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public void addCommunicationStimulus(
            final CommunicationStimulus communicationStimulus) {
        synchronized (this) {
            if (communicationStimuli == null) {
                communicationStimuli = Collections.synchronizedList(new ArrayList<CommunicationStimulus>());
            }
        }

        this.communicationStimuli.add(communicationStimulus);
    }

    @Override
    public void addAgent(final Agent agent) {
        /*
        * This needs to be synchronised because the agents list is lazily
        * initialised. This time it was chosen to pay the price the synchronisation
        * adds in order to save memory allocation.
         */
        synchronized (this) {
            if (agents == null) {
                agents = Collections.synchronizedList(new ArrayList<Agent>());

            } else {
                // if the agent is in the node already, just ignore the call.
                if ((agent.getCurrentNode() == this)) {
                    logger.info("Agent {} already in the node {}!", agent.getId(), this.getId());
                    return;
                }
            }
        }

        synchronized (agents) {
            this.agents.add(agent);
            logger.trace("{}: agent {} moved here.", this.getId(), agent.getId());
        }

        // Let's remove the agent from the node's agent list, and after we set
        // the new current node to reflect the agent's new position. This was
        // decided to be done here because a agent cannot be in two places at
        // the same time, so adding a agent to a node, means removing from the
        // other one (agent's current node).
        agent.getCurrentNode().getAgents().remove(agent);

        // I'm not sure if I should leave this inside or outside the
        // synchronisation block above, I'm leaving outside now because I think
        // if I leave inside the method will use the wrong lock and Agent is
        // thread-safe so should be fine.
        agent.setCurrentNode(this);

        // it doesn't need to be in a synchronised block because the recording
        // flag is final and the history list is synchronised
        if (agent.shouldRecordNodeHistory()) {
            agent.addToVisitedHistory(this);
        }
    }

    @Override
    public Node getNeighbour(final Direction direction) {
        switch (direction) {
            case NORTH:
                return this.north;

            case NORTH_EAST:
                return this.northEast;

            case EAST:
                return this.east;

            case SOUTH_EAST:
                return this.southEast;

            case SOUTH:
                return this.south;

            case SOUTH_WEST:
                return this.southWest;

            case WEST:
                return this.west;

            case NORTH_WEST:
                return this.northWest;
        }

        throw new RuntimeException("Direction '" + direction + "' is not valid.");
    }

    @Override
    public void setNeighbour(final Direction direction, final Node node) {
        switch (direction) {
            case NORTH:
                this.north = node;
                break;

            case NORTH_EAST:
                this.northEast = node;
                break;

            case EAST:
                this.east = node;
                break;

            case SOUTH_EAST:
                this.southEast = node;
                break;

            case SOUTH:
                this.south = node;
                break;

            case SOUTH_WEST:
                this.southWest = node;
                break;

            case WEST:
                this.west = node;
                break;

            case NORTH_WEST:
                this.northWest = node;
                break;
        }
    }

    @Override
    public void setNeighbours(final Direction direction, final Node node) {
        switch (direction) {
            case NORTH:
                this.north = node;
                node.setNeighbour(Direction.SOUTH, this);
                break;

            case NORTH_EAST:
                this.northEast = node;
                node.setNeighbour(Direction.SOUTH_WEST, this);
                break;

            case EAST:
                this.east = node;
                node.setNeighbour(Direction.WEST, this);
                break;

            case SOUTH_EAST:
                this.southEast = node;
                node.setNeighbour(Direction.NORTH_WEST, this);
                break;

            case SOUTH:
                this.south = node;
                node.setNeighbour(Direction.NORTH, this);
                break;

            case SOUTH_WEST:
                this.southWest = node;
                node.setNeighbour(Direction.NORTH_EAST, this);
                break;

            case WEST:
                this.west = node;
                node.setNeighbour(Direction.EAST, this);
                break;

            case NORTH_WEST:
                this.northWest = node;
                node.setNeighbour(Direction.SOUTH_EAST, this);
                break;
        }
    }

    @Override
    public String toString() {
        return "BasicNode [id=" + id + "]";
    }

    @Override
    public void addAgentStartingHere(final Agent agent) {
        synchronized (this) {
            if (agents == null) {
                agents = Collections.synchronizedList(new ArrayList<Agent>());
            }

            synchronized (agents) {
                this.agents.add(agent);
                logger.trace("{}: agent {} initialised here.", this.getId(),
                        agent.getId());
            }

            // not synchronised for the same reason described in addAgent method.
            agent.setCurrentNode(this);
        }
    }

    public synchronized CommunicationStimulus getCommunicationStimulus(
            final CommunicationStimulusType communicationStimulusType) {

        if (communicationStimuli == null) {
            return null;
        }

        for (CommunicationStimulus stimulus : communicationStimuli) {
            if (stimulus.getType() == communicationStimulusType) {
                return stimulus;
            }
        }

        return null;
    }

    @Override
    public void disconnectFromNeighbours() {
        north = null;
        northEast = null;
        east = null;
        southEast = null;
        south = null;
        southWest = null;
        west = null;
        northWest = null;
    }
}
