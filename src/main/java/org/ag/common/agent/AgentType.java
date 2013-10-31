package org.ag.common.agent;

/**
 * AgentType is a basic data type that hold all the tasks agent of a certain
 * type can perform and extra information like the type's name.
 *
 * AgentTypes are advised to be implemented as singleton, in order to save
 * memory consumption.
 *
 * Classes that implement this interface must be thread-safe and have a public
 * static final field called NAME.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 *
 */
public interface AgentType {
    /**
     * Returns the type's name.

     * @return types's name.
     */
    String getName();
}