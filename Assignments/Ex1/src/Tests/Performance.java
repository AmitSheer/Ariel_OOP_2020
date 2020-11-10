package Tests;

import ex1.WGraph_DS;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Performance extends BaseTest {
    @Test
    void maxSizeGraphTimeCheck(){
        int maxNodes = (int)Math.pow(10,6);
        int maxEdges = maxNodes*10;
        Timestamp n1 = Timestamp.from(Instant.now());
        graphCreator(1,maxNodes,maxEdges,1 );
        Timestamp n2 = Timestamp.from(Instant.now());
        System.out.println(n1);
        System.out.println(n2);
    }
    public void graphCreatorForPerformanceTests2(int seed, int size, int edges, int weight){
        graph = new WGraph_DS();
        Random _rnd = new Random(seed);
        Random rndWeight = new Random(weight);
        for (int i = 0; i < size; i++) {
            graph.addNode(i);
            int edge = graph.edgeSize();
            while(graph.edgeSize()==edge&&i>2){
                graph.connect((int)(size*_rnd.nextDouble()),(int)(size*_rnd.nextDouble()), rndWeight.nextDouble());
            }
        }
        while(graph.edgeSize()<edges){
            graph.connect((int)(size*_rnd.nextDouble()),(int)(size*_rnd.nextDouble()), rndWeight.nextDouble());
        }
    }
}
