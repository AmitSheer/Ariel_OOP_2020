package ex1;

import java.io.*;
import java.sql.Date;
import java.time.Instant;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



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
        resetGraph();
        int numberOfVisited = this.algo.dijkstra(this.graph, this.graph.getV().stream().findFirst().get(), -1);
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
        if (this.graph.getV().size() == 0 || this.graph.getNode(src) == null || this.graph.getNode(src) == null) {
            return -1;
        }
        resetGraph();
        this.algo.dijkstra(this.graph, graph.getNode(src), dest);
        if (this.graph.getNode(dest).getTag() == Integer.MAX_VALUE){
            return -1;
        }else{
            return this.graph.getNode(dest).getTag();
        }
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> nodePathToDest = new LinkedList<>();
        resetGraph();
        this.algo.dijkstra(this.graph, graph.getNode(src), dest);
        String [] strPath = this.graph.getNode(dest).getInfo().split(",");
        for (String nodeKey : strPath) {
            nodePathToDest.add(this.graph.getNode(Integer.parseInt(nodeKey)));
        }
        return nodePathToDest;
    }

    @Override
    public boolean save(String file) {
        try {
            if(!new File(file).createNewFile()) System.out.println("File with the same name exists, will write over the file");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new JsonGraphParser().parseGraph(this.graph).toJSONString());
            fileWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load(String file) {
        try (FileReader reader = new FileReader(file)){
            weighted_graph tempGraph = new JsonGraphParser().parseJsonToGraph(reader);
            this.init(tempGraph);
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

    /**
     * converts the graph to and from JSON format
     */
    private static class JsonGraphParser {
        /**
         * receives a graph and changes it to JSON FORMAT
         * @param graph to format
         * @return the formatted Json Array
         */
        public JSONArray parseGraph(weighted_graph graph) {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonNodeArray = new JSONArray();
            JSONObject jsonEdgesObject = new JSONObject();
            //
            for (node_info node : graph.getV()) {
                jsonObject.put("key", node.getKey());
                jsonObject.put("info", node.getKey());
                for (node_info connection : graph.getV(node.getKey())) {
                    jsonEdgesObject.put(connection.getInfo(), graph.getEdge(node.getKey(), connection.getKey()));
                }
                jsonObject.put("edge", jsonEdgesObject);
                jsonNodeArray.add(jsonObject);
                jsonObject = new JSONObject();
                jsonEdgesObject = new JSONObject();
            }
            return jsonNodeArray;
        }

        /**
         * receives a reader and reads from file in JSON format
         * then changes it to graph
         * @param reader normal file reader from FileReader
         * @return the initiated graph
         * @throws IOException if file is missing
         * @throws ParseException if unauthorized tokens are detected in file
         */
        public weighted_graph parseJsonToGraph(FileReader reader) throws IOException, ParseException {
            weighted_graph g = new WGraph_DS();
            JSONParser jsonParser = new JSONParser();
            try{
                Object obj = jsonParser.parse(reader);
                JSONArray nodesData = (JSONArray) obj;
                weighted_graph graph = new WGraph_DS();
                JSONObject jo;
                //loads all nodes
                for (Object node : nodesData) {
                    jo = (JSONObject) node;
                    g.addNode(((Long) jo.get("key")).intValue());
                }
                //loads all nodes data
                for (Object node : nodesData) {
                    jo = (JSONObject) node;
                    int src = ((Long) jo.get("key")).intValue();
                    JSONObject edges = (JSONObject) jo.get("edge");
                    for (Object key : edges.keySet()) {
                        g.connect(src, Integer.parseInt(key.toString()), (Double) edges.get(key));
                    }
                }
                return g;
            }catch(Exception e){
                throw e;
            }
        }
    }
}

