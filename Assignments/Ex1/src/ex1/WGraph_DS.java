package ex1;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class WGraph_DS implements weighted_graph{
    private final HashMap<Integer,node_info> nodes;
    private final HashMap<Integer,Edges> edges;
    private Integer edgeCount;
    private Integer mc;

    public WGraph_DS(){
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.edgeCount=0;
        this.mc = 0;
    }

    @Override
    public node_info getNode(int key) {
        return this.nodes.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        try{
            return ((NodeInfo)this.nodes.get(node1)).hasNi(node2)&& ((NodeInfo)this.nodes.get(node2)).hasNi(node1);
            //return edges.get(node1).containsKey(node2) && edges.get(node2).containsKey(node1);
        }catch (NullPointerException e ){
            return false;
        }
    }

    @Override
    public double getEdge(int node1, int node2) {
        return (this.hasEdge(node1,node2))?((NodeInfo)this.nodes.get(node1)).getNi(node2):-1;
        //return (this.hasEdge(node1, node2)) ? edges.get(node2).get(node1) :  -1;

    }

    @Override
    public void addNode(int key) {
        this.nodes.putIfAbsent(key,new NodeInfo(key));
        //this.edges.putIfAbsent(key, new Edges());
        this.mc++;
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (!hasEdge(node1,node2)&&w>=0&&node1!=node2) {
            try{
                if(!((NodeInfo)this.nodes.get(node1)).hasNi(node2)){
                    this.edgeCount++;
                }
//                if(!this.edges.get(node1).containsKey(node2)){
//                    this.edgeCount++;
//                }
//                this.edges.get(node1).put(node2,w);
//                this.edges.get(node2).put(node1,w);
                ((NodeInfo)this.nodes.get(node1)).addNi(node2,w);
                ((NodeInfo)this.nodes.get(node2)).addNi(node1,w);
                this.mc++;
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
            List<node_info> connectedNodes = new ArrayList<>();
            ((NodeInfo)this.nodes.get(node_id)).getAllNi().keySet().forEach((key)->connectedNodes.add(nodes.get(key)));;
            //edges.get(node_id).keySet().forEach((key)->connectedNodes.add(nodes.get(key)));
            return  connectedNodes;
        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public node_info removeNode(int key) {
        node_info node = nodes.get(key);
        try{
            //Collection<Integer> a  = edges.get(key).keySet();
            Collection<Integer> a  = ((NodeInfo)node).getAllNi().keySet();
            while(a.iterator().hasNext()){
                removeEdge(a.iterator().next(),key);
            }
            //this.edges.remove(key);
            this.mc++;
            nodes.remove(key);
        }catch(Exception ignore){
        }
        return node;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)){
            ((NodeInfo)this.nodes.get(node1)).removeNi(node2);
            ((NodeInfo)this.nodes.get(node2)).removeNi(node1);
//            this.edges.get(node1).remove(node2);
//            this.edges.get(node2).remove(node1);
            this.edgeCount--;
            this.mc++;
        }
    }

    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edgeCount;
    }

    @Override
    public int getMC() {
        return this.mc;
    }

    static class Edges extends HashMap<Integer,Double>{
    }


    private static class NodeInfo implements node_info {
        private static int nodeCounter=0;
        private final Integer key;
        private String info;
        private double tag;
        private HashMap<Integer,Double> ni;

        public NodeInfo(int key){
            this.key = key;
            this.info = String.valueOf(key);
            this.tag = Integer.MAX_VALUE;
            nodeCounter= nodeCounter + key;
            ni = new HashMap<>();
        }

        public NodeInfo(){
            this.key = nodeCounter;
            this.info = String.valueOf(this.key);
            this.tag = Integer.MAX_VALUE;
            nodeCounter++;
            ni = new HashMap<>();
        }

        public NodeInfo(node_info node){
            this.key = node.getKey();
            this.info = String.valueOf(this.key);
            this.tag = Integer.MAX_VALUE;
            nodeCounter= nodeCounter + this.key;
            ni = new HashMap<>();
        }

        public boolean hasNi(int key) {
            return ni.containsKey(key);
        }

        public HashMap<Integer, Double> getAllNi() {
            return ni;
        }

        public double getNi(int key) {
            return ni.get(key);
        }

        public void removeNi(int key) {
            ni.remove(key);
        }

        public void addNi(int key,double dist) {
            ni.put(key, dist);
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
            return "{" +
                    "key:" + key +
                    ", info:'" + info + '\'' +
                    ", tag:" + tag +
                    '}';
        }
    }
}

