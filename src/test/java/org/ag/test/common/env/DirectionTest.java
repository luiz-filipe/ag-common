package org.ag.test.common.env;

import static org.junit.Assert.assertTrue;

import org.ag.common.env.Direction;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectionTest {
    private static final Logger logger = LoggerFactory.getLogger(DirectionTest.class);
    /**
     * The idea of this test is to verify that the tasks chooses a neighbour randomly in a fair way. So it asks the
     * WandererTask to choose a neighbour a thousand times and check the distribution of the chosen nodes
     */
    @Test
    public void randomNeighbourDistribution() {
        final int nInteractions = 2000;
        final int nDirections = 8;
        final int margin = (int) ((nInteractions / nDirections) * 0.15);
        final int expectedMinimum = (nInteractions / nDirections) - margin;
        final int expectedMaximum = (nInteractions / nDirections) + margin;

        int nNorth = 0;
        int nNorthEast = 0;
        int nEast = 0;
        int nSouthEast = 0;
        int nSouth = 0;
        int nSouthWest = 0;
        int nWest = 0;
        int nNorthWest = 0;

        for (int i = 0; i < nInteractions; i++) {
            Direction direction = Direction.getRandomDirection();

            switch (direction) {
                case NORTH:
                    nNorth++;
                    break;

                case NORTH_EAST:
                    nNorthEast++;
                    break;

                case EAST:
                    nEast++;
                    break;

                case SOUTH_EAST:
                    nSouthEast++;
                    break;

                case SOUTH:
                    nSouth++;
                    break;

                case SOUTH_WEST:
                    nSouthWest++;
                    break;

                case WEST:
                    nWest++;
                    break;

                case NORTH_WEST:
                    nNorthWest++;
                    break;
            }

        }

        final float pNorth = (float) nNorth / nInteractions * 100;
        final float pNorthEast = (float) nNorthEast / nInteractions * 100;
        final float pEast = (float) nEast / nInteractions * 100;
        final float pSouthEast = (float) nSouthEast / nInteractions * 100;
        final float pSouth = (float) nSouth / nInteractions * 100;
        final float pSouthWest = (float) nSouthWest / nInteractions * 100;
        final float pWest = (float) nWest / nInteractions * 100;
        final float pNorthWest = (float) nNorthWest / nInteractions * 100;

        logger.info("Random neighbour direction results ----------");
        logger.info("north direction was selected: {} ({}%)", nNorth, pNorth);
        logger.info("Number of times north-east direction was selected: {} ({}%)", nNorthEast, pNorthEast);
        logger.info("Number of times east direction was selected: {} ({}%)", nEast, pEast);
        logger.info("Number of times south-east direction was selected: {} ({}%)", nSouthEast, pSouthEast);
        logger.info("Number of times south direction was selected: {} ({}%)", nSouth, pSouth);
        logger.info("Number of times south-west direction was selected: {} ({}%)", nSouthWest, pSouthWest);
        logger.info("Number of times west node was selected: {} ({}%)", nWest, pWest);
        logger.info("Number of times north-west direciton was selected: {} ({}%)", nNorthWest, pNorthWest);

        assertTrue(nNorth > expectedMinimum);
        assertTrue(nNorth < expectedMaximum);
        assertTrue(nNorthEast > expectedMinimum);
        assertTrue(nNorthEast < expectedMaximum);
        assertTrue(nEast > expectedMinimum);
        assertTrue(nEast < expectedMaximum);
        assertTrue(nSouthEast > expectedMinimum);
        assertTrue(nSouthEast < expectedMaximum);
        assertTrue(nSouth > expectedMinimum);
        assertTrue(nSouth < expectedMaximum);
        assertTrue(nSouthWest > expectedMinimum);
        assertTrue(nSouthWest < expectedMaximum);
        assertTrue(nWest > expectedMinimum);
        assertTrue(nWest < expectedMaximum);
        assertTrue(nNorthWest > expectedMinimum);
        assertTrue(nNorthWest < expectedMaximum);
    }

    @Test
    public void getOppositeDirectionTest() {
        assertTrue(Direction.NORTH.getOpposite().equals(Direction.SOUTH));
        assertTrue(Direction.NORTH_EAST.getOpposite().equals(Direction.SOUTH_WEST));
        assertTrue(Direction.EAST.getOpposite().equals(Direction.WEST));
        assertTrue(Direction.SOUTH_EAST.getOpposite().equals(Direction.NORTH_WEST));
        assertTrue(Direction.SOUTH.getOpposite().equals(Direction.NORTH));
        assertTrue(Direction.SOUTH_WEST.getOpposite().equals(Direction.NORTH_EAST));
        assertTrue(Direction.WEST.getOpposite().equals(Direction.EAST));
        assertTrue(Direction.NORTH_WEST.getOpposite().equals(Direction.SOUTH_EAST));
    }
}
