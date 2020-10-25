package ex0;

import java.util.Collection;
import java.util.HashSet;

public class NodeData implements node_data {
    private Integer key;
    private HashSet<node_data> siblingNodes;
    private HashSet<Integer> siblingNodesIds;
    private String info;
    private int tag;

    public NodeData(){
        siblingNodes = new HashSet<>();
        this.tag=-1;
    }

    public NodeData(node_data node){
        this.key = node.getKey();
        this.tag=-1;
    }

    public NodeData(Integer key, String info) {
        this.key = key;
        siblingNodes = new HashSet<>();
        siblingNodesIds = new HashSet<>();
        this.info = info;
        this.tag=-1;
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

    @Override
    public void addNi(node_data t) {
        if(!siblingNodesIds.contains(t.getKey())) {
            siblingNodes.add(t);
            siblingNodesIds.add(t.getKey());
        }
    }

    @Override
    public void removeNode(node_data node) {
        if(siblingNodesIds.contains(node.getKey())) {
            siblingNodes.remove(node);
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

   //@Override
   //public String toString() {
   //    return "NodeData{" +
   //            "key=" + key.toString() +
   //            ", siblingNodes=" + siblingNodes.toString() +
   //            ", siblingNodesIds=" + siblingNodesIds.toString() +
   //            ", info='" + info + '\'' +
   //            ", tag=" + tag +
   //            '}';
   //}
}
