package org.ag.common.env;

/**
 * Communication stimulus type allows to differentiate between stimulus that are
 * present in a node. This is important when agents update the present stimuli
 * in the nodes they operate upon.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
public interface CommunicationStimulusType {
	/**
	 * A identifier that should be unique.
	 * 
	 * @return String Type's identifier name
	 */
	String getName();
}