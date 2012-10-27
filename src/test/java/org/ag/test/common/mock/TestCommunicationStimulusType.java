package org.ag.test.common.mock;

import org.ag.common.env.CommunicationStimulusType;

public enum TestCommunicationStimulusType implements CommunicationStimulusType {
	TYPE;

	private static final String name = "comm:stimulus:type:test";
	
	@Override
	public String getName() {
		return name;
	}

}
