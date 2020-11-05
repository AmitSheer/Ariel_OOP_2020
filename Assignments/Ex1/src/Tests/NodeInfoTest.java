package Tests;

import ex1.WGraph_DS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeInfoTest {
    static WGraph_DS.NodeInfo nodeInfo;
    @BeforeEach
    void setUp() {
        nodeInfo = new WGraph_DS.NodeInfo(0);
    }

    @Test
    void getKey() {
        assertEquals(nodeInfo.getKey(), 0);
    }

    @Test
    void getInfo() {
        assertEquals(nodeInfo.getInfo(),"0");
    }

    @Test
    void setInfo() {
        nodeInfo.setInfo("hello");
        assertEquals(nodeInfo.getInfo(),"hello");
    }

    @Test
    void getTag() {
        assertEquals(nodeInfo.getTag(),-1);
    }

    @Test
    void setTag() {
        nodeInfo.setTag(20);
        assertEquals(nodeInfo.getTag(),20);
    }
}