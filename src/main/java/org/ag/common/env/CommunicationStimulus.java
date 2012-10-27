package org.ag.common.env;

/**
 * A communication stimulus might represent any interaction between agents and
 * the environment. For example, when ant agents deposit pheromone on the
 * environment, the pheromone is a type of communication stimulus.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
public interface CommunicationStimulus {
	/**
	 * Returns the type of communication stimulus the object has.
	 * 
	 * @return CommunicationStimulusType Type of communication stimulus.
	 */
	CommunicationStimulusType getType();
}
