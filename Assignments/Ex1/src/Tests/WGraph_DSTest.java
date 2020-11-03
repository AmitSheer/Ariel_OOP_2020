package Tests;

import ex1.WGraph_DS;
import ex1.node_info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    static WGraph_DS graph;
    static ArrayList<node_info> nodes;
    @BeforeEach
    void setUp() {
        graph = new WGraph_DS();
        nodes = new ArrayList<>();
    }

    @Test
    void addNode() {
        graph.addNode(0);
        WGraph_DS.NodeInfo node = new WGraph_DS.NodeInfo(0);
        assertEquals(graph.getNode(0).toString(),node.toString());
    }
    // Connection Tests
    @Test
    void connect() {
        graph.addNode(0);
        graph.addNode(1);
        graph.connect(0,1,1);
        assertTrue(graph.hasEdge(0,1));
    }

    @Test
    void connectWithTheSameID() {
        graph.addNode(0);
        graph.connect(0,0,1);
        assertFalse(graph.hasEdge(0, 0));
    }

    @Test
    void connectWithNegativeWeight() {
        graph.addNode(0);
        graph.addNode(1);
        graph.connect(0,1,-1);
        assertFalse(graph.hasEdge(0, 1));
    }

    @Test
    void connectNodesWithExistingConnection() {
        graph.addNode(0);
        graph.addNode(1);
        graph.connect(0,1,1);
        graph.connect(1,0,1);
        graph.connect(0,1,1);
        assertEquals(1,graph.edgeSize());
    }

    @Test
    void connectWithNonExistingNode() {
        graph.addNode(0);
        graph.connect(0,1,1);
        assertFalse(graph.hasEdge(0,1));
    }


    @Test
    void hasEdge() {
        graph.addNode(0);
        graph.addNode(1);
        graph.connect(0,1,1);
        assertTrue(graph.hasEdge(0,1));
    }

    @Test
    void hasEdgeNonExistingNode() {
        graph.addNode(0);
        assertFalse(graph.hasEdge(0,1));
    }

    @Test
    void getEdge() {
        graph.addNode(0);
        graph.addNode(1);
        graph.connect(0,1,1);
        assertEquals(1, graph.getEdge(0,1));
    }

    @Test
    void getNonExistingEdge() {
        graph.addNode(0);
        graph.addNode(1);
        assertFalse(graph.hasEdge(0,1));
    }

    @Test
    void getEdgeOfNonExistingNode() {
        graph.addNode(0);
        graph.addNode(1);
        graph.connect(0,1,1);
        assertFalse(graph.hasEdge(0,5));
    }

    @Test
    void getV() {
        for (int i = 0; i < 10; i++) {
            graph.addNode(i);
            nodes.add(new WGraph_DS.NodeInfo(i));
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(nodes.get(i).toString(),graph.getNode(i).toString(),String.valueOf(i));
        }
    }

    @Test
    void GetAllConnectionToSpecificNode() {
        Random rnd = new Random(1);
        for (int i = 0; i < 10; i++) {
            graph.addNode(i);
            nodes.add(graph.getNode(i));
        }
        while(graph.edgeSize()<10){
            graph.connect((int)(10*rnd.nextDouble()),(int)(10*rnd.nextDouble()),1);
        }
        assertEquals(2,graph.getV(0).size());
        for (node_info node_info : graph.getV(0)) {
            if(node_info.getKey()!=5&&node_info.getKey()!=9) fail(graph.getV(0).toString());
        }
        assertEquals(3,graph.getV(1).size());
        for (node_info node_info : graph.getV(1)) {
            if(node_info.getKey()!=3&&node_info.getKey()!=7&&node_info.getKey()!=6)
                fail(graph.getV(1).toString());
        }
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }
}