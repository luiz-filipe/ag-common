package org.ag.test.common.env;

import static org.junit.Assert.assertTrue;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.ag.common.agent.Agent;
import org.ag.common.env.BasicNode;
import org.ag.common.env.Direction;
import org.ag.common.env.Node;
import org.ag.test.common.mock.TestAgent;
import org.ag.test.common.mock.TestAgentType;
import org.ag.test.common.mock.TestCommunicationStimulus;
import org.ag.test.common.mock.TestCommunicationStimulusType;
import org.junit.Test;

public class BasicNodeTest {
	@Test
	public void equalsContract() {
		EqualsVerifier.forClass(BasicNode.class).verify();
	}
	
	@Test
	public void communicationStimulusTest() {
		final Node node = new BasicNode("node");
		
		node.addCommunicationStimulus(new TestCommunicationStimulus());
		
		assertTrue(node.getCommunicationStimuli().size() == 1);
		assertTrue(node.getCommunicationStimuli().get(0).getType().equals(
				TestCommunicationStimulusType.TYPE));
	}
	
	@Test
	public void addAgentTest() {
		final Node node = new BasicNode("node");
		final Node stagging = new BasicNode("stagging node");
		
		final Agent a = new TestAgent("a", TestAgentType.TYPE, stagging, false);
		
		assertTrue(stagging.getAgents().size() == 1);
		assertTrue(stagging.getAgents().get(0).getId().equals("a"));
		
		node.addAgent(a);
		
		assertTrue(stagging.getAgents().size() == 0);
		assertTrue(node.getAgents().size() == 1);
		assertTrue(node.getAgents().get(0).getId().equals("a"));
	}

	@Test
	public void neighboursLinkTest() {
		final Node c = new BasicNode("centre");
		final Node n = new BasicNode("north");
		final Node ne = new BasicNode("north-east");
		final Node e = new BasicNode("east");
		final Node se = new BasicNode("south-east");
		final Node s = new BasicNode("south");
		final Node sw = new BasicNode("south-west");
		final Node w = new BasicNode("west");
		final Node nw = new BasicNode("north-west");
		
		c.setNeighbours(Direction.NORTH, n);
		c.setNeighbours(Direction.NORTH_EAST, ne);
		c.setNeighbours(Direction.EAST, e);
		c.setNeighbours(Direction.SOUTH_EAST, se);
		c.setNeighbours(Direction.SOUTH, s);
		c.setNeighbours(Direction.SOUTH_WEST, sw);
		c.setNeighbours(Direction.WEST, w);
		c.setNeighbours(Direction.NORTH_WEST, nw);
		
		assertTrue(c.getNeighbour(Direction.NORTH).equals(n));
		assertTrue(c.getNeighbour(Direction.NORTH_EAST).equals(ne));
		assertTrue(c.getNeighbour(Direction.EAST).equals(e));
		assertTrue(c.getNeighbour(Direction.SOUTH_EAST).equals(se));
		assertTrue(c.getNeighbour(Direction.SOUTH).equals(s));
		assertTrue(c.getNeighbour(Direction.SOUTH_WEST).equals(sw));
		assertTrue(c.getNeighbour(Direction.WEST).equals(w));
		assertTrue(c.getNeighbour(Direction.NORTH_WEST).equals(nw));
	}
}
