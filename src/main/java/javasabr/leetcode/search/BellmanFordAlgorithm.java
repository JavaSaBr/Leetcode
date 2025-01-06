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

public class BellmanFordAlgorithm {

  public static void main(String[] args) {

    WeightGraph<Integer> graph1 = WeightGraph.with(
        root(0, connection(1, 5)),
        node(1, connection(2, 1), connection(3, 2)),
        node(2, connection(4, 1)),
        node(3),
        node(4, connection(3, -1)));

    System.out.println(calculateDistances(graph1, 0));

    WeightGraph<Integer> graph2 = WeightGraph.with(
        root(0, connection(1, 4)),
        node(1, connection(2, -6)),
        node(2, connection(3, 5)),
        node(3, connection(1, -2)));

    System.out.println(calculateDistances(graph2, 0));
  }

  public static Map<Integer, Integer> calculateDistances(WeightGraph<Integer> graph, int source) {

    var distanceMap = new HashMap<Integer, Integer>();
    distanceMap.put(source, 0);

    var visited = new HashSet<WeightGraphNode<Integer>>();

    for (WeightGraphNode<Integer> root : graph.roots()) {
      if (!visited.add(root)) {
        continue;
      }
      calculateDistances(root, distanceMap, visited, false);
    }

    int totalIterations = visited.size() - 1;

    for (int i = 1; i < totalIterations; i++) {
      visited.clear();
      for (WeightGraphNode<Integer> root : graph.roots()) {
        if (!visited.add(root)) {
          continue;
        }
        calculateDistances(root, distanceMap, visited, i == totalIterations - 1);
      }
    }

    return distanceMap;
  }

  private static void calculateDistances(
      WeightGraphNode<Integer> node,
      Map<Integer, Integer> distanceMap,
      Set<WeightGraphNode<Integer>> visited,
      boolean last) {

    visited.add(node);

    Integer distanceToThis = distanceMap.get(node.value());

    for (WeightEdge<Integer> edge : node.edges()) {

      if (distanceToThis != null) {

        int weight = edge.weight();
        WeightGraphNode<Integer> target = edge.node();
        Integer distanceToTarget = distanceMap.get(target.value());
        int newDistanceToTarget = distanceToThis + weight;

        if (distanceToTarget == null) {
          distanceMap.put(target.value(), newDistanceToTarget);
        } else if (distanceToTarget > newDistanceToTarget) {
          if (last) {
            throw new IllegalArgumentException("Negative cycle");
          }
          distanceMap.put(target.value(), newDistanceToTarget);
        }
      }

      if (!visited.add(edge.node())) {
        continue;
      }

      calculateDistances(edge.node(), distanceMap, visited, last);
    }
  }
}
