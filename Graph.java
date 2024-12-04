/******************************************************************
 *
 *   Mohammed Khursiwala / 002
 *
 *   Note, additional comments provided throughout this source code
 *   is for educational purposes
 *
 ********************************************************************/

import java.util.*;


/**
 *  Graph traversal exercise
 *
 *  The Graph class is a representing an oversimplified Directed Graph of vertices
 *  (nodes) and edges. The graph is stored in an adjacency list
 */

public class Graph {
  int numVertices;                  // vertices in graph
  LinkedList<Integer>[] adjListArr; // Adjacency list
  List<Integer> vertexValues;       // vertex values

  // Constructor 
  public Graph(int numV) {
    numVertices = numV;
    adjListArr = new LinkedList[numVertices];
    vertexValues = new ArrayList<>(numVertices);

    for (int i = 0; i < numVertices; i++) {
      adjListArr[i] = new LinkedList<>();
      vertexValues.add(0);
    }
  }

  /*
   * method setValue
   * 
   * Sets a vertex's (node's) value.
   */ 
  
  public void setValue(int vertexIndex, int value) {
    if (vertexIndex >= 0 && vertexIndex < numVertices) {
      vertexValues.set(vertexIndex, value);
    } else {
      throw new IllegalArgumentException(
             "Invalid vertex index: " + vertexIndex);
    }
  }


  public void addEdge(int src, int dest) {
    adjListArr[src].add(dest);
  }

  /*
   * method printGraph
   * 
   * Prints the graph as an adjacency matrix
   */ 
  
  public void printGraph() {
    System.out.println(
         "\nAdjacency Matrix Representation:\n");
    int[][] matrix = new int[numVertices][numVertices];

    for (int i = 0; i < numVertices; i++) {
      for (Integer dest : adjListArr[i]) {
        matrix[i][dest] = 1;
      }
    }

    System.out.print("  ");
    for (int i = 0; i < numVertices; i++) {
      System.out.print(i + " ");
    }
    System.out.println();

    for (int i = 0; i < numVertices; i++) {
      System.out.print(i + " ");
      for (int j = 0; j < numVertices; j++) {
        if (matrix[i][j] == 1) {
          System.out.print("| ");
        } else {
          System.out.print(". ");
        }
      }
      System.out.println();
    }
  }

  public Map<Integer, List<Integer>> buildGraph(){
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (int i = 0; i < numVertices; i++) {
      int srcNode = vertexValues.get(i);
      // Get pre-existing neighbors of src node, if none return empty array to
      // add avaiable neighbors to
      List<Integer> currNeighbors = graph.getOrDefault(srcNode, new ArrayList<>());
      List<Integer> actualNeighbors = adjListArr[i];
      for (int j = 0; j < actualNeighbors.size(); j++) {
        int neighborIndex = actualNeighbors.get(j);
        int neighbor = vertexValues.get(neighborIndex);
        currNeighbors.add(neighbor);
      }
      // Add map the node to its list of neighbors
      graph.put(srcNode, currNeighbors);
    }
    return graph;
  }


  /**
   * method findRoot
   *
   * This method returns the value of the root vertex, where root is defined in
   * this case as a node that has no incoming edges. If no root vertex is found
   * and/or more than one root vertex, then return -1.
   * 
   */
  
  public int findRoot() {

    /**
     * Multiple nodes with indegree 0 -> return -1
     * No nodes with indegree 0 -> return -1
     * One node with indegree 0 -> return value of node
     */
    Map<Integer, List<Integer>> graph = buildGraph();
    Map<Integer, Integer> indegreeMap = new HashMap<>();

    Set<Integer> visited = new HashSet<>();
    for(int i = 0; i < numVertices; i++){
      int currNode = vertexValues.get(i);
      // Make sure all nodes have an indegree (including isolated nodes)
      indegreeMap.put(currNode, indegreeMap.getOrDefault(currNode,0));
      Queue<Integer> q = new LinkedList<>();
      q.add(currNode);
      while (!q.isEmpty()){
        // start the bfs
        int node = q.poll();
        if(visited.contains(node)){
          continue;
        }
        // we add the neighbors
        List<Integer> neighbors = graph.get(node);

        for(int j = 0; j < neighbors.size(); j++){
          int neighbor = neighbors.get(j);
          int neighborIndigree = indegreeMap.getOrDefault(neighbor, 0);
          neighborIndigree++;
          indegreeMap.put(neighbor, neighborIndigree);
        }
      }
    }
    int numOfZeroIndegree = 0;
    int root = -1;
    for(int node: indegreeMap.keySet()){
      if(indegreeMap.get(node) == 0){
        root = node;
        numOfZeroIndegree++;
      }
    }

    if(numOfZeroIndegree == 0 || numOfZeroIndegree > 1){
      return -1;
    }
    return root;
  }
}