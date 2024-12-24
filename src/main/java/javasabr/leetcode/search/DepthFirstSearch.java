package javasabr.leetcode.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javasabr.leetcode.Graph;
import javasabr.leetcode.Graph.Connections;
import javasabr.leetcode.GraphNode;

public class DepthFirstSearch {

  public static void main(String[] args) {

    Graph<Integer> onlyConnected = Graph.with(
        Connections.root(0, 1, 2),
        Connections.node(1, 0, 2, 3),
        Connections.node(2, 0, 1, 4),
        Connections.node(3, 1, 4),
        Connections.root(4, 3, 2));

    System.out.println(baseline(onlyConnected, 0));
    System.out.println(baseline(onlyConnected, 4));

    Graph<Integer> withDisconnected = Graph.with(
        Connections.root(0, 1, 2),
        Connections.node(1, 0, 2, 3),
        Connections.node(2, 0, 1, 4),
        Connections.node(3, 1, 4),
        Connections.root(4, 3, 2),
        Connections.root(5, 6, 7),
        Connections.node(6, 5, 7),
        Connections.node(7, 5));

    System.out.println(withDisconnected(withDisconnected));
  }

  public static List<Integer> baseline(Graph<Integer> graph, int init) {

    var visited = new HashSet<GraphNode<Integer>>();
    var result = new ArrayList<Integer>();

    baseline(graph.root(init), visited, result);

    return result;
  }

  public static void baseline(GraphNode<Integer> node, Set<GraphNode<Integer>> visited, List<Integer> result) {
    visited.add(node);
    result.add(node.value());

    for (GraphNode<Integer> connected : node.connected()) {
      if (!visited.contains(connected)) {
        baseline(connected, visited, result);
      }
    }
  }

  public static List<Integer> withDisconnected(Graph<Integer> graph) {

    var visited = new HashSet<GraphNode<Integer>>();
    var result = new ArrayList<Integer>();

    for (GraphNode<Integer> root : graph.roots()) {
      if (!visited.contains(root)) {
        baseline(root, visited, result);
      }
    }

    return result;
  }
}
