package org.ag.common.env;

/**
 * Communication stimulus types allow to differentiate between different kind of stimulus that are present in a node.
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
public interface CommunicationStimulusType {
    /**
     * Returns the unique identifier for the stimulus type.
     *
     * @return type's name.
     */
    String getName();
}