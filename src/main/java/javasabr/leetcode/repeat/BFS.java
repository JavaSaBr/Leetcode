package javasabr.leetcode.repeat;

import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.List;
import javasabr.leetcode.Graph;
import javasabr.leetcode.GraphNode;

public class BFS {

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

    var visited = new LinkedHashSet<Integer>();
    var queue = new ArrayDeque<GraphNode<Integer>>();

    GraphNode<Integer> init = graph.root(rootValue);

    queue.add(init);

    while (!queue.isEmpty()) {
      GraphNode<Integer> removed = queue.removeFirst();
      if (visited.contains(removed.value())) {
        continue;
      }
      visited.add(removed.value());
      for (GraphNode<Integer> connected : removed.connected()) {
        if (visited.contains(connected.value())) {
          continue;
        }
        queue.addLast(connected);
      }
    }

    return List.copyOf(visited);
  }

  private static List<Integer> traverse(Graph<Integer> graph) {

    var visited = new LinkedHashSet<Integer>();
    var queue = new ArrayDeque<GraphNode<Integer>>();

    for (GraphNode<Integer> root : graph.roots()) {
      if (visited.contains(root.value())) {
        continue;
      }
      queue.add(root);

      while (!queue.isEmpty()) {
        GraphNode<Integer> removed = queue.removeFirst();
        if (visited.contains(removed.value())) {
          continue;
        }
        visited.add(removed.value());
        for (GraphNode<Integer> connected : removed.connected()) {
          if (visited.contains(connected.value())) {
            continue;
          }
          queue.addLast(connected);
        }
      }
    }
    return List.copyOf(visited);
  }
}
