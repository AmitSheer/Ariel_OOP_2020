package ex1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonGraphParser {
    public JSONArray parseGraph(weighted_graph graph) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonNodeArray = new JSONArray();
        JSONObject jsonEdgesObject = new JSONObject();
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

    public weighted_graph parseJsonToGraph(FileReader reader) throws IOException, ParseException {
        weighted_graph g = new WGraph_DS();
        JSONParser jsonParser = new JSONParser();
        try{
            Object obj = jsonParser.parse(reader);
            JSONArray nodesData = (JSONArray) obj;
            weighted_graph graph = new WGraph_DS();
            JSONObject jo;
            for (Object node : nodesData) {
                jo = (JSONObject) node;
                g.addNode(((Long) jo.get("key")).intValue());
            }
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
            return null;
        }
    }
}
