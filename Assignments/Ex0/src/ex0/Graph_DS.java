package ex0;

import java.util.*;

public class Graph_DS implements graph{
    //Holds the nodes in order to have access to the nodes in O(1)
    private HashMap<Integer,node_data> nodes;
    //counts the number of edges
    private Integer edgeSize;
    //holds the values of nodes in order to be able to get all of the nodes in O(1)
    private HashSet<node_data> v;
    //counts the numbers of changes in graph
    private int numberOfChanges;
    //V,E

    /**
     * init
     */
    public Graph_DS(){
        nodes = new HashMap<>();
        v= new HashSet<>();
        edgeSize=0;
        numberOfChanges=0;
    }

    /**
     * returns nodes data with specific key
     * @param key - the node_id
     * @return
     */
    @Override
    public node_data getNode(int key) {
        return nodes.get(key);
    }

    /**
     * checks if there is an edge between two nodes
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        return nodes.get(node1).hasNi(node2);
    }

    /**
     * adds a new node to the graph
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if(!nodes.containsKey(n.getKey())){
            nodes.putIfAbsent(n.getKey(), n);
            v.add(n);
            numberOfChanges++;
        }
    }

    /**
     * creates new connection between nodes
     * @param node1
     * @param node2
     */
    @Override
    public void connect(int node1, int node2) {
        if(nodes.containsKey(node1)&& nodes.containsKey(node2)&&!nodes.get(node1).hasNi(node2)&&!nodes.get(node2).hasNi(node1)&&node1!=node2){
            nodes.get(node1).addNi(nodes.get(node2));
            edgeSize++;
            numberOfChanges++;
        }
    }

    /**
     *
     * @return all of the nodes in O(1)
     */
    @Override
    public Collection<node_data> getV() {
        return v;
    }

    /**
     * gets all of the neighbors nodes
     * @param node_id id of node
     * @return
     */
    @Override
    public Collection<node_data> getV(int node_id) {
        if(nodes.containsKey(node_id)){
            return nodes.get(node_id).getNi();
        }
        return null;
    }

    /**
     * deletes specific node from graph
     * and removes all of the edges associated with it
     * @param key of node to remove
     * @return the removed node
     */
    @Override
    public node_data removeNode(int key) {
        node_data node = nodes.get(key);
        if(node == null) return null;
        //remove all connected edges
        while(nodes.get(key).getNi().size()>0){
            int node_key = nodes.get(key).getNi().stream().findFirst().get().getKey();
            removeEdge(node_key,key);
        }
        //remove node from value list, and nodes
        v.remove(node);
        nodes.remove(key);
        numberOfChanges++;
        return node;
    }

    /**
     * delete an edge from node
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(nodes.get(node1).hasNi(node2)){
            nodes.get(node1).removeNode(nodes.get(node2));
            edgeSize--;
            numberOfChanges++;
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
