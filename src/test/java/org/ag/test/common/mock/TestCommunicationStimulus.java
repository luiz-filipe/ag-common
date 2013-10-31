package org.ag.test.common.mock;

import org.ag.common.env.BasicCommunicationStimulus;

/**
 *
 *
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 */
public class TestCommunicationStimulus extends BasicCommunicationStimulus {
    public final String addedState = "Added state";

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
