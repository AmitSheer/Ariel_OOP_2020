package ex1;

import java.util.Collection;
import java.util.HashMap;

public class WGraph_DS implements weighted_graph{
    private final HashMap<Integer,node_info> nodes;
    private final HashMap<Integer,Edges> edges;
    private Integer edgeCount;

    public WGraph_DS(){
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        edgeCount=0;
    }

    @Override
    public node_info getNode(int key) {
        return this.nodes.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        try{
            return edges.get(node1).containsKey(node2) && edges.get(node2).containsKey(node1);
        }catch (NullPointerException e ){
            return false;
        }
    }

    @Override
    public double getEdge(int node1, int node2) {
        return (this.hasEdge(node1, node2)) ? edges.get(node2).get(node1) :  -1;

    }

    @Override
    public void addNode(int key) {
        this.nodes.putIfAbsent(key,new NodeInfo(key));
        this.edges.putIfAbsent(key, new Edges());
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (!hasEdge(node1,node2)&&w>=0&&node1!=node2) {
            try{
                if(edges.get(node1).containsKey(node2)){
                    edges.get(node2).put(node1,w);
                    edges.get(node1).put(node2,w);
                }else{
                    edges.get(node1).put(node2,w);
                    edges.get(node2).put(node1,w);
                    edges.get(node1).addConnection(nodes.get(node2));
                    edges.get(node2).addConnection(nodes.get(node1));
                    edgeCount++;
                }
            }catch(NullPointerException ignored){

            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        return this.nodes.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        try {
            return edges.get(node_id).getConnections();
        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public node_info removeNode(int key) {
        return null;
    }

    @Override
    public void removeEdge(int node1, int node2) {

    }

    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgeCount;
    }

    @Override
    public int getMC() {
        return 0;
    }

    static class Edges extends HashMap<Integer,Double>{
        private final HashMap<Integer,node_info> allConnections = new HashMap<>();
        public void addConnection(node_info nodeInfo){
            allConnections.putIfAbsent(nodeInfo.getKey(),nodeInfo);
        }
        public Collection<node_info> getConnections(){
            return allConnections.values();
        }

    }

    public static class NodeInfo implements node_info{
        private static int nodeCounter=0;
        private final Integer key;
        private String info;
        private double tag;

        public NodeInfo(int key){
            this.key = key;
            this.info = String.valueOf(key);
            this.tag = -1;
            nodeCounter= nodeCounter + key;
        }

        public NodeInfo(){
            this.key = nodeCounter;
            this.info = String.valueOf(this.key);
            this.tag = -1;
            nodeCounter++;
        }

        public NodeInfo(node_info node){
            this.key = node.getKey();
            this.info = String.valueOf(this.key);
            this.tag = -1;
            nodeCounter= nodeCounter + this.key;
        }

        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        @Override
        public String toString() {
            return "NodeInfo{" +
                    "key=" + key +
                    ", info='" + info + '\'' +
                    ", tag=" + tag +
                    '}';
        }
    }
}

