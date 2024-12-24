package javasabr.leetcode.search;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javasabr.leetcode.Graph;
import javasabr.leetcode.Graph.Connections;
import javasabr.leetcode.GraphNode;

public class BreadthFirstSearch {

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

    var queue = new ArrayDeque<GraphNode<Integer>>();
    var visited = new HashSet<GraphNode<Integer>>();
    var result = new ArrayList<Integer>();

    GraphNode<Integer> initNode = graph.root(init);

    queue.add(initNode);
    visited.add(initNode);

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

  public static List<Integer> withDisconnected(Graph<Integer> graph) {

    var queue = new ArrayDeque<GraphNode<Integer>>();
    var visited = new HashSet<GraphNode<Integer>>();
    var result = new ArrayList<Integer>();

    for (GraphNode<Integer> node : graph.roots()) {

      if (!visited.add(node)) {
        continue;
      }

      queue.add(node);

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
