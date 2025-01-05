package javasabr.leetcode.search;

import java.util.HashSet;
import java.util.Set;
import javasabr.leetcode.Graph;
import javasabr.leetcode.GraphNode;

public class BipartiteGraph {

  public static void main(String[] args) {

    Graph<Integer> bigraph = Graph.with(
        Graph.Connections.root(0, 1),
        Graph.Connections.node(1, 0, 2, 3),
        Graph.Connections.node(2, 1, 4),
        Graph.Connections.node(3, 1, 5),
        Graph.Connections.node(4, 2, 8),
        Graph.Connections.node(5, 3, 6),
        Graph.Connections.node(6, 5, 7),
        Graph.Connections.node(7, 6),
        Graph.Connections.node(8, 4, 9),
        Graph.Connections.node(9, 8));

    System.out.println(isBigraph(bigraph));

    Graph<Integer> notbigraph = Graph.with(
        Graph.Connections.root(0, 1),
        Graph.Connections.node(1, 0, 2, 3),
        Graph.Connections.node(2, 1, 3, 4),
        Graph.Connections.node(3, 1, 5),
        Graph.Connections.node(4, 2, 8),
        Graph.Connections.node(5, 3, 6),
        Graph.Connections.node(6, 5, 7),
        Graph.Connections.node(7, 6),
        Graph.Connections.node(8, 4, 9),
        Graph.Connections.node(9, 8));

    System.out.println(isBigraph(notbigraph));
  }

  private static boolean isBigraph(Graph<Integer> graph) {

    var red = new HashSet<GraphNode<Integer>>();
    var blue = new HashSet<GraphNode<Integer>>();
    var visited = new HashSet<GraphNode<Integer>>();

    for (GraphNode<Integer> root : graph.roots()) {
      if (!visited.contains(root)) {
        if (!visit(root, red, blue, visited)) {
          return false;
        }
      }
    }

    return true;
  }

  private static boolean visit(
      GraphNode<Integer> node,
      Set<GraphNode<Integer>> left,
      Set<GraphNode<Integer>> right,
      Set<GraphNode<Integer>> visited) {

    if (right.contains(node)) {
      return false;
    }

    visited.add(node);
    left.add(node);

    for (GraphNode<Integer> connected : node.connected()) {

      if (left.contains(connected)) {
        return false;
      } else if (visited.contains(connected)) {
        continue;
      }

      if (!visit(connected, right, left, visited)) {
        return false;
      }
    }

    return true;
  }
}
