package org.ag.test.common.mock;

import org.ag.common.env.CommunicationStimulusType;

/**
 * Communication stimulus type to be used in tests, name assigned: <i>comm:stimulus:type:test</i>
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
public enum TestCommunicationStimulusType implements CommunicationStimulusType {
    TYPE;

    private static final String name = "comm:stimulus:type:test";

    @Override
    public String getName() {
        return name;
    }

}
