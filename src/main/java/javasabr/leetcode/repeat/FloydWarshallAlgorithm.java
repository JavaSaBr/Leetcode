package javasabr.leetcode.repeat;

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

    var visited = new HashSet<WeightGraphNode<Integer>>();
    var distanceMap = new HashMap<Integer, Map<Integer, Integer>>();

    for (WeightGraphNode<Integer> root : graph.roots()) {
      if (visited.contains(root)) {
        continue;
      }
      traverse(root, visited);
    }

    for (WeightGraphNode<Integer> node : visited) {

      Map<Integer, Integer> distanceMapFromNode = distanceMap
          .computeIfAbsent(node.value(), _ -> new HashMap<>());
      distanceMapFromNode.put(node.value(), 0);

      for (WeightEdge<Integer> edge : node.edges()) {
        distanceMapFromNode.put(edge.node().value(), edge.weight());
      }
    }

    for (WeightGraphNode<Integer> mediumNode : visited) {
      Map<Integer, Integer> mediumDistanceMap = distanceMap.get(mediumNode.value());
      for (WeightGraphNode<Integer> sourceNode : visited) {
        Map<Integer, Integer> sourceDistanceMap = distanceMap.get(sourceNode.value());
        Integer sourceToMedium = sourceDistanceMap.get(mediumNode.value());
        if (sourceToMedium == null) {
          continue;
        }
        for (WeightGraphNode<Integer> targetNode : visited) {
          Integer mediumToTarget = mediumDistanceMap.get(targetNode.value());
          if (mediumToTarget == null) {
            continue;
          }
          int newDistance = sourceToMedium + mediumToTarget;
          Integer oldDistance = sourceDistanceMap.get(targetNode.value());
          if (oldDistance == null || oldDistance > newDistance) {
            sourceDistanceMap.put(targetNode.value(), newDistance);
          }
        }
      }
    }

    return distanceMap;
  }

  private static void traverse(WeightGraphNode<Integer> node, Set<WeightGraphNode<Integer>> visited) {
    visited.add(node);
    for (WeightEdge<Integer> edge : node.edges()) {
      WeightGraphNode<Integer> target = edge.node();
      if (visited.contains(target)) {
        continue;
      }
      traverse(target, visited);
    }
  }
}
