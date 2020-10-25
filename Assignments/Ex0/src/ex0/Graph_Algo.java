package ex0;

import org.junit.jupiter.api.parallel.Execution;

import java.util.*;

public class Graph_Algo implements graph_algorithms{

    private graph graph;
    @Override
    public void init(graph g) {
        graph = g;
    }

    @Override
    public graph copy() {
        graph copiedGraph = new Graph_DS();
        for (node_data node : graph.getV()) {
            copiedGraph.addNode(new NodeData(node.getKey(),node.getInfo()));
            node_data currentNode = copiedGraph.getNode(node.getKey());
            for (node_data sibling : node.getNi()) {
                copiedGraph.addNode(new NodeData(sibling.getKey(),sibling.getInfo()));
                currentNode.addNi(copiedGraph.getNode(sibling.getKey()));
                copiedGraph.connect(node.getKey(),sibling.getKey());
            }
         }
        return copiedGraph;
    }

    @Override
    public boolean isConnected() {
        if(graph.getV().size()==0) return true;
        node_data node = graph.getV().stream().findFirst().get();
        Queue<node_data> nodeDataQueue = new LinkedList<>();
        nodeDataQueue.add(node);
        ArrayList<Integer> nodeIdList = new ArrayList<>();
        while(!nodeDataQueue.isEmpty()){
            node = nodeDataQueue.remove();
            nodeIdList.add(node.getKey());
            for (node_data n: node.getNi()) {
                if(!nodeIdList.contains(n.getKey())) {
                    nodeDataQueue.add(n);
                }
            }
        }
        return nodeIdList.size() == graph.nodeSize();
        //graph graphCopy = copy();
        //try {
        //    node = graphCopy.getV().stream().findFirst().get();
        //    surfAllConnectedNodes(node);
        //    for (node_data n: graphCopy.getV()) {
        //        if (n.getTag()==-1) return false;
        //    }
        //    return true;
        //}catch (Exception e ) {
        //    System.out.println("Empty Graph");
        //    return true;
        //}
    }

    private void surfAllConnectedNodes(node_data node) throws NullPointerException{
        Queue<node_data> nodeDataQueue = new LinkedList<>();
        nodeDataQueue.add(node);
        if (node.getTag() > -1) return;
        node.setTag(0);
        while(!nodeDataQueue.isEmpty()){
            node = nodeDataQueue.remove();
            for (node_data n: node.getNi()) {
                if(n.getTag()<0) {
                    n.setTag(node.getTag() + 1);
                    nodeDataQueue.add(n);
                }
            }
        }
    }

    @Override
    public int shortestPathDist(int src, int dest) {
        node_data node = graph.getV().stream().findFirst().get();
        Queue<node_data> nodeDataQueue = new LinkedList<>();
        nodeDataQueue.add(node);
        ArrayList<Integer> nodeIdList = new ArrayList<>();
        while(!nodeDataQueue.isEmpty()){
            node = nodeDataQueue.remove();
            nodeIdList.add(node.getKey());
            for (node_data n: node.getNi()) {
                if(!nodeIdList.contains(n.getKey())) {
                    nodeDataQueue.add(n);
                }
            }
        }
        graph copiedGraph = copy();
        try {
            surfAllConnectedNodes(copiedGraph.getNode(src));
            return copiedGraph.getNode(dest).getTag();
        }catch(Exception e){
            return -1;
        }
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        graph copiedGraph = copy();
        List<node_data> pathToDest = new ArrayList<>();
        try{
            surfAllConnectedNodes(copiedGraph.getNode(src));
            pathToDest.add(copiedGraph.getNode(dest));
            if(pathToDest.get(0).getTag()==-1) return pathToDest;
            for (int i = 0; i < copiedGraph.getNode(dest).getTag(); i++) {
                node_data temp = pathToDest.get(0);
                for (node_data node : pathToDest.get(i).getNi()) {
                    if(temp.getTag()>node.getTag()) temp = node;
                }
                pathToDest.add(temp);
            }
            return pathToDest;
        }catch(Exception e){
            return null;
        }
    }
}
