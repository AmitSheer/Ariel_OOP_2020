package Tests;


import ex1.weighted_graph;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest extends BaseTest {

    @Test
    void copy() {
        graphCreator(1,10,10,1);
        algo.init(graph);
        weighted_graph copied = algo.copy();
        assertTrue(compareGraph(copied,graph));
    }

    @Test
    void isConnected() {
        graphCreator(1,10,10,1);
        algo.init(graph);
        assertTrue(algo.isConnected());
    }

    @Test
    void shortestPathDist() {
    }

    @Test
    void shortestPath() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}