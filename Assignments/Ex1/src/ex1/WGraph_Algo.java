package ex1;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph;
    private HashSet<Integer> visited;

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
        for (node_info node : graph.getV()) {
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
        Dijkstra(this.graph.getV().stream().findFirst().get(), -1);
        for (node_info node : this.graph.getV()) {
            if (node.getTag() == Integer.MAX_VALUE) {
                resetGraph();
                return false;
            }
        }
        resetGraph();
        return true;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (this.graph.getV().size() == 0 || this.graph.getNode(src) == null || this.graph.getNode(src) == null)
            return -1;
        Dijkstra(graph.getNode(src), dest);
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
        Dijkstra(this.graph.getNode(src),dest);
        String [] strPath = this.graph.getNode(dest).getInfo().split(",");
        for (String nodeKey : strPath) {
            nodePathToDest.add(this.graph.getNode(Integer.parseInt(nodeKey)));
        }
        resetGraph();
        return nodePathToDest;
    }

    @Override
    public boolean save(String file) {
        //file = pathFixer(file);
        try {
            FileWriter fileWriter = new FileWriter(file);
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonNodeArray = new JSONArray();
            JSONObject jsonEdgesObject = new JSONObject();
            for (node_info node : this.graph.getV()) {
                jsonObject.put("key",node.getKey());
                jsonObject.put("info",node.getKey());
                for (node_info connection: this.graph.getV(node.getKey())) {
                    jsonEdgesObject.put(connection.getInfo(),this.graph.getEdge(node.getKey(),connection.getKey()));
                }
                jsonObject.put("edge",jsonEdgesObject);
                jsonNodeArray.add(jsonObject);
                jsonObject = new JSONObject();
                jsonEdgesObject = new JSONObject();
            }
            fileWriter.write(jsonNodeArray.toString());
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        //file = pathFixer(file);
        try (FileReader reader = new FileReader(file)){
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            JSONArray nodesData = (JSONArray) obj;
            weighted_graph graph = new WGraph_DS();
            JSONObject jo;
            for (Object node :nodesData) {
                jo = (JSONObject)node;
                graph.addNode(((Long)jo.get("key")).intValue());
            }
            for (Object node :nodesData) {
                jo = (JSONObject)node;
                int src = ((Long)jo.get("key")).intValue();
                JSONObject edges = (JSONObject)jo.get("edge");
                for (Object key :edges.keySet()) {
                    graph.connect(src,Integer.parseInt(key.toString()), (Double) edges.get(key));
                }
            }
            this.graph = graph;
        } catch (Exception e) {
            return false;
        }
        return true;
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
//    private String pathFixer(String path){
//        String[] pathArr = path.split("\\.");
//        if (!pathArr[pathArr.length - 1].equals("json")){
//            pathArr[pathArr.length-1] = "json";
//        }
//        StringBuilder fileBuilder = new StringBuilder("\\");
//        for (String s : pathArr) {
//            fileBuilder.append(s);
//        }
//        return fileBuilder.toString();
//    }

    private void Dijkstra(node_info start, Integer nodeKeyToFind){
        PriorityQueue<node_info> a = new PriorityQueue<>(new CompareToForQueue());
        start.setTag(0);
        HashSet<Integer> visited = new HashSet<>();
        a.add(start);
        while(a.size()>0){
            node_info curr = a.remove();
            if(!visited.contains(curr.getKey()))
            {
                visited.add(curr.getKey());
                //if(nodeKeyToFind== curr.getKey()) break;
                for (node_info node :graph.getV(curr.getKey())) {
                    if ((curr.getTag()+ graph.getEdge(curr.getKey(), node.getKey()))<node.getTag()){
                        node.setTag(curr.getTag()+ graph.getEdge(curr.getKey(), node.getKey()));
                        node.setInfo(curr.getInfo()+","+node.getKey());
                        a.add(node);
                    }
                }
            }
        }
    }

    private static class CompareToForQueue implements Comparator<node_info>{
        @Override
        public int compare(node_info o1, node_info o2) {
            if(o1.getTag()==o2.getTag())
                return 0;
            else if(o1.getTag()<o2.getTag())
                return -1;
            return 1;
        }
    }
}


