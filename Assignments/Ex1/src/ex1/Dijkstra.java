package ex1;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Dijkstra {
    public void dijkstra(weighted_graph graph, node_info start, Integer nodeKeyToFind) {
        PriorityQueue<node_info> a = new PriorityQueue<>(new CompareToForQueue());
        start.setTag(0);
        HashSet<Integer> visited = new HashSet<>();
        a.add(start);
        while (a.size() > 0) {
            node_info curr = a.remove();
            if (!visited.contains(curr.getKey())) {
                visited.add(curr.getKey());
                if (nodeKeyToFind == curr.getKey()) break;
                for (node_info node : graph.getV(curr.getKey())) {
                    if ((curr.getTag() + graph.getEdge(curr.getKey(), node.getKey())) < node.getTag()) {
                        node.setTag(curr.getTag() + graph.getEdge(curr.getKey(), node.getKey()));
                        node.setInfo(curr.getInfo() + "," + node.getKey());
                        a.add(node);
                    }
                }
            }
        }
    }

    private static class CompareToForQueue implements Comparator<node_info> {
        @Override
        public int compare(node_info o1, node_info o2) {
            if (o1.getTag() == o2.getTag())
                return 0;
            else if (o1.getTag() < o2.getTag())
                return -1;
            return 1;
        }
    }
}
