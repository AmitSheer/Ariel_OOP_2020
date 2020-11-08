package Tests;

import ex1.*;
import ex1.WGraph_DS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest extends BaseTest{

    @Test
    void addNode() {
        graph.addNode(0);
        node_info node = graph.getNode(0);
        assertEquals("0", node.getInfo());
        assertEquals(0, node.getKey());
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
        }
        for (int i = 0; i < 10; i++) {
            assertEquals(i,graph.getNode(i).getKey(),String.valueOf(i));
        }
    }

    @Test
    void GetAllConnectionToSpecificNode() {
        graphCreator(1, 10, 10, 1);
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
        graphCreator(1, 10, 10, 1);
        graph.removeNode(0);
        assertFalse(graph.hasEdge(0,5));
        assertFalse(graph.hasEdge(0,9));
        assertNull(graph.getNode(0));
        assertEquals(8, graph.edgeSize());
    }

    @Test
    void removeFakeNode() {
        graphCreator(1, 10, 10, 1);
        graph.removeNode(19);
        assertTrue(graph.hasEdge(0,5));
        assertTrue(graph.hasEdge(0,9));
        assertNotNull(graph.getNode(0));
        assertEquals(10, graph.edgeSize());
    }

    @Test
    void removeEdge() {
        graphCreator(1, 10, 10, 1);
        assertTrue(graph.hasEdge(0,5));
        graph.removeEdge(0,5);
        assertFalse(graph.hasEdge(0,5));
        assertEquals(9, graph.edgeSize());
    }

    @Test
    void removeNotExistingEdge() {
        graphCreator(1, 10, 10, 1);
        assertFalse(graph.hasEdge(0,7));
        graph.removeEdge(0,7);
        assertFalse(graph.hasEdge(0,7));
        assertEquals(10, graph.edgeSize());
    }

    @Test
    void removeNotExistingNodeEdge() {
        graphCreator(1, 10, 10, 1);
        assertFalse(graph.hasEdge(0,7));
        graph.removeEdge(0,11);
        assertFalse(graph.hasEdge(0,7));
        assertEquals(10, graph.edgeSize());
    }


    @Test
    void nodeSize() {
        graphCreator(1,10,10,1);
        assertEquals(10,graph.nodeSize());
        graph.removeNode(0);
        assertEquals(9,graph.nodeSize());
    }

    @Test
    void nodeSizeAfterDeletingFakeNode() {
        graphCreator(1,10,10,1);
        assertEquals(10,graph.nodeSize());
        graph.removeNode(20);
        assertEquals(10,graph.nodeSize());
    }

    @Test
    void nodeSizeAfterAddingNode() {
        graphCreator(1,10,10,1);
        assertEquals(10,graph.nodeSize());
        graph.addNode(20);
        assertEquals(11,graph.nodeSize());
    }

    @Test
    void edgeSize() {
        graphCreator(1,10,10,1);
        assertEquals(10,graph.edgeSize());
        graph.removeEdge(0,5);
        assertEquals(9,graph.edgeSize());
    }

    @Test
    void edgeSizeAfterDeleteFakeEdge() {
        graphCreator(1,10,10,1);
        assertEquals(10,graph.edgeSize());
        graph.removeEdge(0,11);
        assertEquals(10,graph.edgeSize());
    }

    @Test
    void getMC() {
        graphCreator(1,10,10,1);
        assertEquals(20,graph.getMC());
        graph.removeEdge(0,5);
        assertEquals(21,graph.getMC());
        graph.connect(0,5,1);
        assertEquals(22,graph.getMC());
        graph.removeNode(0);
        assertEquals(25,graph.getMC());
    }

}