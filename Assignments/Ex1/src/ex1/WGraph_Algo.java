package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph;
    private Dijkstra algo;
    public WGraph_Algo(){
        this.algo = new Dijkstra();
    }
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    /**
     * creates deep copy of init graph
     * @return the copied graph, the new reference
     */
    @Override
    public weighted_graph copy() {
        weighted_graph copiedGraph = new WGraph_DS();
        //copy all nodes into new nodes, with new pointers
        for (node_info node : graph.getV()) {
            copiedGraph.addNode(node.getKey());
        }
        Iterator<node_info> nodeIterator = this.graph.getV().iterator();
        while (copiedGraph.edgeSize()!=graph.edgeSize()&&nodeIterator.hasNext()){
            int nodeKey = nodeIterator.next().getKey();
            //will connect all of the nodes as they were connected, the first connection wont
            for (node_info connectedNode: graph.getV(nodeKey)) {
                copiedGraph.connect(connectedNode.getKey(),nodeKey,graph.getEdge(nodeKey,connectedNode.getKey()));
            }
        }
        return copiedGraph;
    }

    /**
     * checks if all nodes in graph are connected
     * @return
     */
    @Override
    public boolean isConnected() {
        if(this.graph.getV().size()==0) return true;
        int numberOfVisited = this.algo.dijkstra(this.graph, this.graph.getV().stream().findFirst().get(), -1);
        resetGraph();
        return this.graph.nodeSize()==numberOfVisited;
    }

    /**
     *
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (this.graph.getV().size() == 0 || this.graph.getNode(src) == null || this.graph.getNode(src) == null)
            return -1;
        this.algo.dijkstra(this.graph, graph.getNode(src), dest);
        if (this.graph.getNode(dest).getTag() == Integer.MAX_VALUE){
            resetGraph();
            return -1;
        }else{
            double value = this.graph.getNode(dest).getTag();
            resetGraph();
            return value;
        }
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> nodePathToDest = new LinkedList<>();
        this.algo.dijkstra(this.graph, graph.getNode(src), dest);
        String [] strPath = this.graph.getNode(dest).getInfo().split(",");
        for (String nodeKey : strPath) {
            nodePathToDest.add(this.graph.getNode(Integer.parseInt(nodeKey)));
        }
        resetGraph();
        return nodePathToDest;
    }

    @Override
    public boolean save(String file) {
        try {
            if(!new File(file).createNewFile()) System.out.println("File with the same name exists, will write over the file");
            FileOutputStream fileWriter = new FileOutputStream(file);
            ObjectOutputStream outputStream  = new ObjectOutputStream(fileWriter);
            outputStream.writeObject(this.graph);
            outputStream.close();
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try {
            FileInputStream reader = new FileInputStream(file);
            ObjectInputStream inputStream = new ObjectInputStream(reader);
            this.graph = (WGraph_DS)inputStream.readObject();
            inputStream.close();
            reader.close();
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * resets all tags of nodes in  graph
     * is faster then creating a new copy for function use
     */
    private void resetGraph() {
        for (node_info now : graph.getV()) {
            now.setInfo(String.valueOf(now.getKey()));
            now.setTag(Integer.MAX_VALUE);
        }
    }
}

