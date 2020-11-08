package Tests;

import ex1.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTest {
    static WGraph_DS graph;
    static WGraph_Algo algo;
    static String path = "src/Tests/testfiles";
    static File f;
    static  FileWriter writer;

    @BeforeAll
    static void mainSetup() throws IOException {
        f = new File(path+"/writeOver.json");
        writer = new FileWriter(f.getPath());
        f.createNewFile();
        writer.write("blabla");
        writer.close();
        f = new File(path+"/writeOver.txt");
        writer = new FileWriter(f.getPath());
        f.createNewFile();
        writer.write("blabla");
        writer.close();
    }

    @BeforeEach
    void setUp() {
        graph = new WGraph_DS();
        algo = new WGraph_Algo();
    }

    public void graphCreator(int seed, int size, int edges, int weight){
        Random rnd = new Random(seed);
        Random rndWeight = new Random(weight);
        for (int i = 0; i < size; i++) {
            graph.addNode(i);
        }
        while(graph.edgeSize()<edges){
            graph.connect((int)(size*rnd.nextDouble()),(int)(size*rnd.nextDouble()), rndWeight.nextDouble());
        }
    }

    public boolean compareGraph(weighted_graph g1, weighted_graph g2){
        if(g1.getV().size()!=g2.getV().size()) return false;
        for (int i = 0; i < g1.getV().size(); i++) {
            assertEquals(g1.getNode(i).toString(),g2.getNode(i).toString());
            for (node_info node :
                    g1.getV(i)) {
                if(g2.hasEdge(node.getKey(), i)&&g2.getEdge(node.getKey(),i)!=g1.getEdge(i,node.getKey()))
                    return false;
            }
        }
        return true;
    }
}
