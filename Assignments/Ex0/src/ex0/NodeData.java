package ex0;

import java.util.Collection;
import java.util.HashSet;


public class NodeData implements node_data {
    private Integer key;
    //counter to generate appropriate keys
    private static int nodeCounter;
    //neighbors of nodes
    private HashSet<node_data> siblingNodes;
    //neighbors Ids
    private HashSet<Integer> siblingNodesIds;
    private String info;
    private int tag;

    public NodeData(){
        siblingNodes = new HashSet<>();
        siblingNodes = new HashSet<>();
        siblingNodesIds = new HashSet<>();
        key = nodeCounter;
        nodeCounter++;
        this.tag=-1;
    }

    public NodeData(node_data node){
        this.key = node.getKey();
        nodeCounter=nodeCounter+node.getKey();
        this.tag=-1;
    }

    public NodeData(Integer key, String info) {
        this.key = key;
        siblingNodes = new HashSet<>();
        siblingNodesIds = new HashSet<>();
        this.info = info;
        this.tag=-1;
        nodeCounter=nodeCounter+key;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public Collection<node_data> getNi() {
        return siblingNodes;
    }

    @Override
    public boolean hasNi(int key) {
        return siblingNodesIds.contains(key);
    }

    /**
     * adds the node as neighbors
     * then sends current node to be added as neighbors at the other node
     * @param t node to add as neighbors
     */
    @Override
    public void addNi(node_data t) {
        if(siblingNodesIds.contains(t.getKey())) return;
        siblingNodes.add(t);
        siblingNodesIds.add(t.getKey());
        t.addNi(this);
    }

    /**
     * remove node from neighbors list and id list
     * then send current node to be removed from other node
     * @param node
     */
    @Override
    public void removeNode(node_data node) {
        if(siblingNodesIds.contains(node.getKey())) {
            siblingNodes.remove(node);
            siblingNodesIds.remove(node.getKey());
            node.removeNode(this);
        }
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
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }
}
