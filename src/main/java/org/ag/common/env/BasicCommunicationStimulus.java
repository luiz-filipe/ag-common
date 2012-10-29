package org.ag.common.env;

import net.jcip.annotations.Immutable;

/**
 * A communication stimulus might represent any interaction between agents and
 * the environment. For example, when ant agents deposit pheromone on the
 * environment, the pheromone is a type of communication stimulus. The
 * ChemicalCommStimulus class is an example of this case. This is just an
 * abstraction implementation that should be used as base.
 * 
 * @see ChemicalCommStimulus
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
@Immutable
public class BasicCommunicationStimulus implements CommunicationStimulus {
	private final CommunicationStimulusType communicationStimulusType;

	public BasicCommunicationStimulus(
			final CommunicationStimulusType communicationStimulusType) {
		this.communicationStimulusType = communicationStimulusType;
	}

	@Override
	public CommunicationStimulusType getType() {
		return this.communicationStimulusType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((communicationStimulusType == null) ? 0
						: communicationStimulusType.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof BasicCommunicationStimulus)) {
			return false;
		}

		BasicCommunicationStimulus other = (BasicCommunicationStimulus) obj;
		if (communicationStimulusType == null) {
			if (other.communicationStimulusType != null)
				return false;
		} else if (!communicationStimulusType
				.equals(other.communicationStimulusType))
			return false;

		if (!other.canEqual(this))
			return false;

		return true;
	}

	/**
	 * This method in necessary when subclasses of a communication stimulus add
	 * state and need to redefine <em>equals</em> and <em>hashCode</em> methods
	 * 
	 * See http://www.artima.com/lejava/articles/equality.html
	 * 
	 * @param other
	 *            Object to test equality
	 * @return True if other object can equals objects of the class
	 */
	public boolean canEqual(final Object obj) {
		return (obj instanceof BasicCommunicationStimulus);
	}
}
