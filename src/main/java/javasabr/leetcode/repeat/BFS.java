package javasabr.leetcode.repeat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
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

    var result = new ArrayList<Integer>();
    var queue = new ArrayDeque<GraphNode<Integer>>();
    var visited = new HashSet<GraphNode<Integer>>();

    GraphNode<Integer> start = graph.root(rootValue);

    queue.add(start);
    visited.add(start);

    while (!queue.isEmpty()) {
      GraphNode<Integer> next = queue.removeFirst();
      result.add(next.value());
      for (GraphNode<Integer> connected : next.connected()) {
        if (visited.add(connected)) {
          queue.addLast(connected);
        }
      }
    }

    return result;
  }

  private static List<Integer> traverse(Graph<Integer> graph) {

    var result = new ArrayList<Integer>();
    var queue = new ArrayDeque<GraphNode<Integer>>();
    var visited = new HashSet<GraphNode<Integer>>();

    for (GraphNode<Integer> root : graph.roots()) {
      if (!visited.add(root)) {
        continue;
      }

      queue.add(root);
      visited.add(root);

      while (!queue.isEmpty()) {
        GraphNode<Integer> next = queue.removeFirst();
        result.add(next.value());
        for (GraphNode<Integer> connected : next.connected()) {
          if (visited.add(connected)) {
            queue.addLast(connected);
          }
        }
      }
    }

    return result;
  }
}
