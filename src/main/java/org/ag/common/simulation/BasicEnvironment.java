package org.ag.common.simulation;

import org.ag.common.agent.Agent;
import org.ag.common.env.EnvironmentFactory;
import org.ag.common.env.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jcip.annotations.NotThreadSafe;

/**
 * A environment provides encapsulation for a grid of basic nodes and some handy
 * methods to place agents into this grid.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 *
 */
@NotThreadSafe
public class BasicEnvironment implements Environment {
	private static final Logger logger = LoggerFactory
			.getLogger(BasicEnvironment.class);

	private final Node[][] env;
	private final int nLines;
	private final int nColumns;

	public int getNumberOfLines() {
		return nLines;
	}

	public int getNumberOfColumns() {
		return nColumns;
	}

	public BasicEnvironment(final int nLines, final int nColumns) {
		this.nLines = nLines;
		this.nColumns = nColumns;
		this.env = EnvironmentFactory.createBasicNodeGrid(nLines, nColumns);
	}

	public void placeAgentAt(final Agent agent, final int line, final int column) {
		if ((line < 0) || (column < 0)) {
			logger.error("Cannot place agent '{}' into the environment. The " +
					"value for line and column must be equal or grater than 0",
					agent.getId());

			return;
		}

		if (line > nLines - 1) {
			logger.error("Cannot place agent '{}' into the environment. The " +
					"maximum allowed value for line is: {}", agent.getId(), 
					nLines - 1);
			
			return;
		}

		if (column > nColumns - 1) {
			logger.error("Cannot place agent '{}' into the environment. The " +
					"maximum allowed value for column is: {}", agent.getId(), 
					nColumns - 1);
			
			return;
		}
		
		env[line][column].addAgentStartingHere(agent);
	}
	
	public void placeAgentAtTheMiddle(final Agent agent) {
		env[nLines / 2][nColumns / 2].addAgentStartingHere(agent);
	}
	
	public Node getNodeAt(final int line, final int column) {
		if ((line < 0) || (column < 0)) {
			logger.error("Cannot return node. line and column parameters" +
					"must be greater or equal to 0.");

			return null;
		}

		if (line > nLines - 1) {
			logger.error("Cannot return node. The maximum allowed value for" +
					" line is: {}", nLines - 1);
			
			return null;
		}

		if (column > nColumns - 1) {
			logger.error("Cannot return node. The maximum allowed value for " +
					"column is: {}", nColumns - 1);
			
			return null;
		}
		
		return env[line][column];
	}
}
