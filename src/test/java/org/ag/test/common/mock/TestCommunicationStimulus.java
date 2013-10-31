package org.ag.test.common.mock;

import org.ag.common.env.BasicCommunicationStimulus;

/**
 * Communication stimulus to be used in unit tests. A field has been added to the class in order to add state to it and
 * test equality using the <i>canEqual</i> strategy.
 *
 * @author Luiz Filipe Abrahao <me@luizfilipe.com>
 */
public class TestCommunicationStimulus extends BasicCommunicationStimulus {
    public final String addedState = "Added state";

    /**
     * Constructs a communication stimulus of the <i>TestCommunicationStimulusType</i>
     */
    public TestCommunicationStimulus() {
        super(TestCommunicationStimulusType.TYPE);
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((addedState == null) ? 0 : addedState.hashCode());
        return result;
    }

    @Override
    public final boolean equals(Object obj) {
        if (!(obj instanceof TestCommunicationStimulus)) {
            return false;
        }

        TestCommunicationStimulus other = (TestCommunicationStimulus) obj;
        if (addedState == null) {
            if (other.addedState != null)
                return false;
        } else if (!addedState.equals(other.addedState))
            return false;

        if (!other.canEqual(this))
            return false;

        return true;
    }

    @Override
    public boolean canEqual(Object obj) {
        return (obj instanceof TestCommunicationStimulus);
    }
}
