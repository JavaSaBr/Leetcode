package javasabr.leetcode.search;

import static javasabr.leetcode.WeightGraph.Connection.connection;
import static javasabr.leetcode.WeightGraph.Connections.node;
import static javasabr.leetcode.WeightGraph.Connections.root;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javasabr.leetcode.WeightGraph;
import javasabr.leetcode.WeightGraphNode;
import javasabr.leetcode.WeightGraphNode.WeightEdge;

public class FloydWarshallAlgorithm {

  public static void main(String[] args) {

    /* Let us create the following weighted graph
         10
    (0)------->(3)
    |         /|\
    5 |          |
    |          | 1
    \|/         |
    (1)------->(2)
          3       */

    WeightGraph<Integer> graph1 = WeightGraph.with(
        root(0, connection(1, 5), connection(3, 10)),
        node(1, connection(2, 3)),
        node(2, connection(3, 1)),
        node(3));

    System.out.println(calculateDistances(graph1));;
  }

  public static Map<Integer, Map<Integer, Integer>> calculateDistances(WeightGraph<Integer> graph) {

    var distanceMap = new HashMap<Integer, Map<Integer, Integer>>();
    var visited = new HashSet<WeightGraphNode<Integer>>();

    for (WeightGraphNode<Integer> root : graph.roots()) {
      if (!visited.add(root)) {
        continue;
      }
      traverse(root, visited);
    }

    // initialize init matrix
    for (WeightGraphNode<Integer> node : visited) {
      Map<Integer, Integer> nodeDistanceMap = distanceMap
          .computeIfAbsent(node.value(), _ -> new HashMap<>());

      nodeDistanceMap.put(node.value(), 0);

      for (WeightEdge<Integer> edge : node.edges()) {
        nodeDistanceMap.put(edge.node().value(), edge.weight());
      }
    }

    for (WeightGraphNode<Integer> intermediate : visited) {
      Map<Integer, Integer> iDistances = distanceMap.get(intermediate.value());
      for (WeightGraphNode<Integer> source : visited) {
        Map<Integer, Integer> sDistances = distanceMap.get(source.value());
        for (WeightGraphNode<Integer> destination : visited) {

          Integer sourceToInt = sDistances.get(intermediate.value());
          Integer intToDest = iDistances.get(destination.value());

          if (sourceToInt == null || intToDest == null) {
            continue;
          }

          Integer sourceToDest = sDistances.get(destination.value());

          if (sourceToDest == null || sourceToDest > (sourceToInt + intToDest)) {
            sDistances.put(destination.value(), sourceToInt + intToDest);
          }
        }
      }
    }

    return distanceMap;
  }

  private static void traverse(WeightGraphNode<Integer> node, Set<WeightGraphNode<Integer>> visited) {
    visited.add(node);

    for (WeightEdge<Integer> edge : node.edges()) {
      if (!visited.add(edge.node())) {
        continue;
      }
      traverse(edge.node(), visited);
    }
  }
}
