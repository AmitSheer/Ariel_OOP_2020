package ex0;

import java.util.*;

public class Graph_DS implements graph{
    private HashMap<Integer,node_data> nodes;
    private Hashtable<Integer, HashSet<Integer>> edges;
    private Integer edgeSize;
    private HashSet<node_data> v;
    private int numberOfChanges;
    //V,E


    public Graph_DS(){
        nodes = new HashMap<>();
        edges = new Hashtable<>();
        v= new HashSet<>();
        edgeSize=0;
        numberOfChanges=0;
    }

    @Override
    public node_data getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        return nodes.get(node1).hasNi(node2);
    }

    @Override
    public void addNode(node_data n) {
        if(!nodes.containsKey(n.getKey())){
            nodes.putIfAbsent(n.getKey(), n);
            v.add(n);
            numberOfChanges++;
        }
    }

    @Override
    public void connect(int node1, int node2) {
        if(nodes.containsKey(node1)&& nodes.containsKey(node2)&&!nodes.get(node1).hasNi(node2)&&!nodes.get(node2).hasNi(node1)&&node1!=node2){
            nodes.get(node1).addNi(nodes.get(node2));
            nodes.get(node2).addNi(nodes.get(node1));
            edgeSize++;
            numberOfChanges++;
        }
    }

    @Override
    public Collection<node_data> getV() {
        return v;
    }

    @Override
    public Collection<node_data> getV(int node_id) {
        if(nodes.containsKey(node_id)){
            return nodes.get(node_id).getNi();
        }
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        node_data node = nodes.get(key);
        if(node == null) return null;
        v.remove(node);
        while(nodes.get(key).getNi().size()>0){
            int node_key = nodes.get(key).getNi().stream().findFirst().get().getKey();
            removeEdge(node_key,key);
        }
        nodes.remove(key);
        numberOfChanges++;
        return node;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        try{
        if(nodes.get(node1).hasNi(node2)||nodes.get(node2).hasNi(node1)){
            nodes.get(node1).removeNode(nodes.get(node2));
            nodes.get(node2).removeNode(nodes.get(node1));
            edgeSize--;
            numberOfChanges++;
        }
        }catch (NullPointerException e){

        }
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgeSize;
    }

    @Override
    public int getMC() {
        return numberOfChanges;
    }

    @Override
    public String toString() {
        return "Graph_DS{" +
                "nodes=" + nodes +
                ", edgeSize=" + edgeSize +
                ", v=" + v +
                '}';
    }
}
