package javasabr.leetcode.repeat;

import static javasabr.leetcode.Graph.Connections.node;
import static javasabr.leetcode.Graph.Connections.root;

import java.util.Collection;
import java.util.HashSet;
import javasabr.leetcode.Graph;
import javasabr.leetcode.GraphNode;

public class BipartiteGraph {

  public static void main(String[] args) {

    Graph<Integer> bigraph = Graph.with(
        root(0, 1),
        node(1, 0, 2, 3),
        node(2, 1, 4),
        node(3, 1, 5),
        node(4, 2, 8),
        node(5, 3, 6),
        node(6, 5, 7),
        node(7, 6),
        node(8, 4, 9),
        node(9, 8));

    System.out.println(isBigraph(bigraph));

    Graph<Integer> notbigraph = Graph.with(
        root(0, 1),
        node(1, 0, 2, 3),
        node(2, 1, 3, 4),
        node(3, 1, 5),
        node(4, 2, 8),
        node(5, 3, 6),
        node(6, 5, 7),
        node(7, 6),
        node(8, 4, 9),
        node(9, 8));

    System.out.println(isBigraph(notbigraph));
  }

  public static boolean isBigraph(Graph<Integer> graph) {

    var red = new HashSet<GraphNode<Integer>>();
    var blue = new HashSet<GraphNode<Integer>>();
    var visited = new HashSet<GraphNode<Integer>>();

    for (GraphNode<Integer> root : graph.roots()) {
      if (visited.contains(root)) {
        continue;
      }
      if (!isBigraph(root, red, blue, visited)) {
        return false;
      }
    }
    return true;
  }

  private static boolean isBigraph(
      GraphNode<Integer> node,
      Collection<GraphNode<Integer>> left,
      Collection<GraphNode<Integer>> right,
      Collection<GraphNode<Integer>> visited) {

    if (right.contains(node)) {
      return false;
    }

    left.add(node);
    visited.add(node);

    for (GraphNode<Integer> connected : node.connected()) {
      if (left.contains(connected)) {
        return false;
      } if (visited.contains(connected)) {
        continue;
      } else if (!isBigraph(connected, right, left, visited)) {
        return false;
      }
    }

    return true;
  }
}
