package ex0;

import java.util.*;

public class Graph_Algo implements graph_algorithms{

    private graph graph;
    @Override
    public void init(graph g) {
        graph = g;
    }

    /**
     * creates deep copy of init graph
     * @return
     */
    @Override
    public graph copy() {
        graph copiedGraph = new Graph_DS();
        //copy all nodes into new nodes, with new pointers
        for (node_data node : graph.getV()) {
            copiedGraph.addNode(new NodeData(node.getKey(),node.getInfo()));
            for (node_data a : node.getNi()) {
                copiedGraph.connect(a.getKey(),node.getKey());
            }
        }
//        //copy all connections
//        for (node_data sibling : graph.getV()) {
//
//        }
        return copiedGraph;
    }

    /**
     * checks if all nodes in graph are connected
     * @return
     */
    @Override
    public boolean isConnected() {
        if(graph.getV().size()==0) return true;
        node_data node;
        try {
            node = graph.getV().stream().findFirst().get();
            surfAllConnectedNodes(node);
            //go over all nodes to  see if one of them wasn't visited
            for (node_data n : graph.getV()) {
                if(n.getTag()<0){
                    resetGraph();
                    return false;
                }
            }
        }catch (Exception e ) {
            System.out.println("Empty Graph");
        }
        resetGraph();
        return true;
    }

    /**
     * starts going over nodes with accordance to BFS algorithm
     * @param node start point of algo
     * @throws NullPointerException when the start node doesn't exist
     */
    private void surfAllConnectedNodes(node_data node) throws NullPointerException{
        Queue<node_data> nodeDataQueue = new LinkedList<>();
        nodeDataQueue.add(node);
        if (node.getTag() > -1) return;
        node.setTag(0);
        while(!nodeDataQueue.isEmpty()){
            node = nodeDataQueue.remove();
            //updates all of neighbors as visited
            for (node_data n: node.getNi()) {
                if(n.getTag()<0) {
                    n.setTag(node.getTag() + 1);
                    n.setInfo(node.getInfo()+","+n.getInfo());
                    nodeDataQueue.add(n);
                }
            }
        }
    }

    /**
     * resets all tags of nodes in  graph
     * is faster then creating a new copy for function use
     */
    private void resetGraph() {
        for (node_data now : graph.getV()) {
            now.setInfo(String.valueOf(now.getKey()));
            now.setTag(-1);
        }
    }

    /**
     * gets the shortest distance from start node to other node
     * @param src - start node
     * @param dest - end (target) node
     * @return the nodes tag, the amount of connections to go through to get to node
     */
    @Override
    public int shortestPathDist(int src, int dest) {
        try {
            surfAllConnectedNodes(graph.getNode(src));
            int tag = graph.getNode(dest).getTag();
            resetGraph();
            return tag;
        }catch(Exception e){
            return -1;
        }
    }


    /**
     *
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data> pathToDest = new ArrayList<>();
        try{
            surfAllConnectedNodes(graph.getNode(src));
            if(graph.getNode(dest).getTag()==-1) return pathToDest;
            node_data node = graph.getNode(dest);
            for (String s : node.getInfo().split(",")) {
                pathToDest.add(graph.getNode(Integer.parseInt(s)));
            }
            return pathToDest;
        }catch (NullPointerException e){
            return pathToDest;
        }finally {
            resetGraph();
        }
    }
}
