package ex1;

import java.util.Collection;
import java.util.List;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph;
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public weighted_graph copy() {
        weighted_graph copiedGraph = new WGraph_DS();
        for (node_info node :
                graph.getV()) {
            copiedGraph.addNode(node.getKey());
            for (node_info connectedNode: graph.getV(node.getKey())) {
                copiedGraph.connect(connectedNode.getKey(),node.getKey(),graph.getEdge(node.getKey(),connectedNode.getKey()));
            }
        }
        return copiedGraph;
    }

    @Override
    public boolean isConnected() {
        if(this.graph.getV().size()==0) return true;
        surfAllNodesFromStartPoint(this.graph.getV().stream().findFirst().get());
        for (node_info node : this.graph.getV()) {
            if (node.getTag() == -1) {
                resetGraph();
                return false;
            }
        }
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

    /**
     * resets all tags of nodes in  graph
     * is faster then creating a new copy for function use
     */
    private void resetGraph() {
        for (node_info now : graph.getV()) {
            now.setInfo(String.valueOf(now.getKey()));
            now.setTag(-1);
        }
    }

    private void surfAllNodesFromStartPoint(node_info start){
        start.setTag(0);

    }
}
