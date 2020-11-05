package Tests;

import ex1.*;
import java.util.ArrayList;
import java.util.Random;

public class BaseTest {
    static WGraph_DS graph;
    static ArrayList<node_info> nodes;

    public void graphCreator(int seed, int size, int edges, double weight){
        Random rnd = new Random(seed);
        for (int i = 0; i < size; i++) {
            graph.addNode(i);
            nodes.add(graph.getNode(i));
        }
        while(graph.edgeSize()<edges){
            graph.connect((int)(size*rnd.nextDouble()),(int)(size*rnd.nextDouble()),1);
        }
    }
}
