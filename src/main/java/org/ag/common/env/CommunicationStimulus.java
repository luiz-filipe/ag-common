package org.ag.common.env;

/**
 * Agents can shape the environment they belong to in various ways, communication stimuli are one. They are deposited
 * by the agents onto the environment. Other agents can read these stimuli and use them to draw any conclusions and take
 * decisions based on the state of the environment.
 *
 * <p>For example, ants deposit pheromone on the environment to mark trails that lead to source of foods. The other
 * ants can read the pheromone and reinforce the trail if they are lead to a good source of food.</p>
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
public interface CommunicationStimulus {
    /**
     * Returns the type of the communication stimulus.
     *
     * @return type of communication stimulus.
     */
    CommunicationStimulusType getType();
}
