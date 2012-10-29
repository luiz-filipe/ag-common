package org.ag.common.env;

/**
 * The EnvironmentFactory class is an utility class that holds the methods to
 * generate environments to be used for the simulation.
 * 
 * @author Luiz Abrahao <luiz@luizabrahao.com>
 * 
 */
public class EnvironmentFactory {
	private EnvironmentFactory() {
	}

	/**
	 * Initialises an environment based on BasicNode objects. This environment
	 * has rectangular shape and each node are assigned an identifier following
	 * the pattern: "n+lineNumber,colunmNumber", e.g. "n3,2" corresponds to the
	 * node at the third line and second column.
	 * 
	 * @param nLines
	 *            number of lines the grid will contain
	 * @param nColunms
	 *            number of column the grid will contain.
	 * @return Node[][] A two dimensional array of interconnected BasicNode
	 *         objects.
	 */
	public static Node[][] createBasicNodeGrid(final int nLines,
			final int nColunms) {
		Node[][] nodes = new Node[nLines][nColunms];

		for (int l = 0; l < nLines; l++) {
			for (int c = 0; c < nColunms; c++) {
				nodes[l][c] = new BasicNode("n" + l + "," + c);

				if (c != 0) {
					nodes[l][c].setNeighbours(Direction.WEST, nodes[l][c - 1]);
				}

				if (l != 0) {
					nodes[l][c].setNeighbours(Direction.NORTH, nodes[l - 1][c]);
				}

				if ((l != 0) && (c != 0)) {
					nodes[l][c].setNeighbours(Direction.NORTH_WEST,
							nodes[l - 1][c - 1]);
				}

				if ((l != 0) && (c != nColunms - 1)) {
					nodes[l][c].setNeighbours(Direction.NORTH_EAST,
							nodes[l - 1][c + 1]);
				}
			}
		}

		return nodes;
	}
}
