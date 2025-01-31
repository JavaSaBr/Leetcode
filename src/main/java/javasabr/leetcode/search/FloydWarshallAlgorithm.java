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
      if (!visited.contains(root)) {
        traverse(root, visited);
      }
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

    for (WeightGraphNode<Integer> medium : visited) {
      Map<Integer, Integer> mediumDistanceMap = distanceMap.get(medium.value());
      for (WeightGraphNode<Integer> source : visited) {

        Map<Integer, Integer> sourcedistanceMap = distanceMap.get(source.value());
        Integer sourceToMedium = sourcedistanceMap.get(medium.value());
        if (sourceToMedium == null) {
          continue;
        }

        for (WeightGraphNode<Integer> destination : visited) {

          Integer mediumToDestination = mediumDistanceMap.get(destination.value());
          if (mediumToDestination == null) {
            continue;
          }

          Integer sourceToDestination = sourcedistanceMap.get(destination.value());
          int newDistance = sourceToMedium + mediumToDestination;

          if (sourceToDestination == null || sourceToDestination > newDistance) {
            sourcedistanceMap.put(destination.value(), newDistance);
          }
        }
      }
    }

    return distanceMap;
  }

  private static void traverse(WeightGraphNode<Integer> node, Set<WeightGraphNode<Integer>> visited) {
    visited.add(node);

    for (WeightEdge<Integer> edge : node.edges()) {
      if (!visited.contains(edge.node())) {
        traverse(edge.node(), visited);
      }
    }
  }
}
