package javasabr.leetcode.repeat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javasabr.leetcode.Graph;
import javasabr.leetcode.GraphNode;

public class DFS {

  public static void main(String[] args) {

    Graph<Integer> onlyConnected = Graph.with(
        Graph.Connections.root(0, 1, 2),
        Graph.Connections.node(1, 0, 2, 3),
        Graph.Connections.node(2, 0, 1, 4),
        Graph.Connections.node(3, 1, 4),
        Graph.Connections.root(4, 3, 2));

    System.out.println(traverse(onlyConnected, 0));
    System.out.println(traverse(onlyConnected, 4));

    Graph<Integer> withDisconnected = Graph.with(
        Graph.Connections.root(0, 1, 2),
        Graph.Connections.node(1, 0, 2, 3),
        Graph.Connections.node(2, 0, 1, 4),
        Graph.Connections.node(3, 1, 4),
        Graph.Connections.root(4, 3, 2),
        Graph.Connections.root(5, 6, 7),
        Graph.Connections.node(6, 5, 7),
        Graph.Connections.node(7, 5));

    System.out.println(traverse(withDisconnected));
  }


  private static List<Integer> traverse(Graph<Integer> graph, int rootValue) {
    var visited = new HashSet<GraphNode<Integer>>();
    var result = new ArrayList<Integer>();
    traverse(graph.root(rootValue), visited, result);
    return result;
  }

  private static List<Integer> traverse(Graph<Integer> graph) {

    var visited = new HashSet<GraphNode<Integer>>();
    var result = new ArrayList<Integer>();

    for (GraphNode<Integer> root : graph.roots()) {
      traverse(root, visited, result);
    }

    return result;
  }

  private static void traverse(GraphNode<Integer> graphNode, Set<GraphNode<Integer>> visited, List<Integer> result) {

    if (!visited.add(graphNode)) {
      return;
    }

    result.add(graphNode.value());

    for (GraphNode<Integer> connected : graphNode.connected()) {
      traverse(connected, visited, result);
    }
  }
}
