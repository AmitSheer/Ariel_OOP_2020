# BFS algorithm on unidirectional graph
The project contains three classes and three interfaces which they implement. It uses an implementation of BFS
algorithm in order to travers the graph nodes.
## Usage
To work with the project you need to:\
1. create an instance of the graph itself: `Graph_DS graph = new Graph_DS()`
2. create Nodes for the graph: `NodeData node = new NodeData()`, can initialize node with specific key 
3. add nodes to the graph: `graph.addNode(node)`
4. add edges, connections: `graph.addEdge(node.getKey(),node2.getKey()`
5. create an instance of the graph algorithm: `Graph_Algo algo = new Graph_Algo()`
6. initialize the algo with the graph you created: ` algo.init(graph)`
7. then use the methods provided to run and use the graph:\
    * `algo.isConnected()` to check if the graph you created all nodes connect
    * `algo.shortestPath(src,dest)` to find all the nodes between the src node to the dest node
    * `algo.shortestPathDist(src,dest)` to find how many nodes are between the src node to the dest node 


## Classes
* __Graph_Algo__\
    Responsible for running the algorithm on specific graph
* __Graph_DS__\
    Controls the graph. Adding,Removing nodes and creating connections between nodes
* __NodeData__\
    The date holder for a node. Used in node creation, addition of new edges and removing them.
## Interfaces
* __graph_algorithms__
* __graph__
* __node_data__
## General Description of Methods, Divided by Class 
* __Graph_Algo.__
    * __init(graph g)__\
    initializes a new graph object into the class.
    * __copy()__\
    creates a deep copy of the initialized graph and returns it
    * __isConnected()__\
    checks if there is a way to get from every node to any other node in graph
    * __shortestPath(int src, int dest)__\
    returns the shortest path from one node to the other, if it exists, in the form of a list
    containing all of the nodes in the path
    * 	__shortestPathDist(int src, int dest)__\
    finds the shortest path, as in how many edges are between two nodes
* __Graph_DS.__
    *  __addNode(node_data n)__\
    adds a new node to the graph
    *  __connect(int node1,int node2)__\
    create a connection between two nodes, given that the connection doesn't already exist
    *  __edgeSize()__\
    gets how many edges are in the graph
    *  __getMC()__\
    counts how many changes were made to the graph
    *  __getNode(int key)__\
    gets a node from the graph, given that the given key is of an existing node in the graph
    *  __getV()__\
    gets all the nodes in the graph
    *  __getV(int node_id)__\
    returns all the connected nodes to the node with given id
    *  __hasEdge(int node1, int node2)__\
    checks if there is a connection between two nodes
    *  __nodeSize()__\
    returns the number of nodes in graph
    *  __removeEdge(int node1, int node2)__\
    deletes the connection between nodes
    *  __removeNode(int key)__\
    deletes the node with given key, and all of its connections in other node
    *  __toString()__\
    return the graph in string form
* __NodeData.__\
    * __addNi(node_data t)__\
    adds a new connection to the connection list in node 
    * __getInfo()__\
    gets the value of info field in node
    * __getInfo()__\
    sets a new value in info field
    * __getKey()__\
    gets the value of key field in node
    * __getNi()__\
    gets a Collection of all the values in connected nodes
    * __getTag()__\
    gets the value of field tag
    * __setTag(int t)__\
    sets a new value in tag field
    * __hasNi(int key)__\
    check node with given key, is connected to this node
    * __removeNode(node_data node)__\
    deletes node from list of connected nodes, and list of connected nodes ids