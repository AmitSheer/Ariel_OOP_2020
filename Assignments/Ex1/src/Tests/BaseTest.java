package Tests;

import ex1.WGraph_DS;
import java.util.ArrayList;
import java.util.Random;

public class BaseTest {
    static WGraph_DS graph;
    static ArrayList<WGraph_DS.NodeInfo> nodes;

    public void graphCreator(int seed, int size, int edges, double weight){
        Random rnd = new Random(seed);
        for (int i = 0; i < size; i++) {
            WGraph_DS.NodeInfo node = new WGraph_DS.NodeInfo();
            graph.addNode(i);
            nodes.add(node);
        }
        while(graph.edgeSize()<edges){
            graph.connect((int)(size*rnd.nextDouble()),(int)(size*rnd.nextDouble()),1);
        }
    }
}
