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

    var visited = new HashSet<WeightGraphNode<Integer>>();
    var distanceMap = new HashMap<Integer, Integer>();
    distanceMap.put(source, 0);

    WeightGraphNode<Integer> initNode = graph.root(source);

    calculateDistance(initNode, visited, distanceMap, false);

    int totalNodes = visited.size();

    for (int i = 1, last = totalNodes - 1; i < totalNodes; i++) {
      visited.clear();
      calculateDistance(initNode, visited, distanceMap, i == last);
    }

    return distanceMap;
  }

  private static void calculateDistance(
      WeightGraphNode<Integer> node,
      Set<WeightGraphNode<Integer>> visited,
      Map<Integer, Integer> distanceMap,
      boolean last) {

    visited.add(node);

    Integer distanceToNode = distanceMap.get(node.value());

    for (WeightGraphNode.WeightEdge<Integer> edge : node.edges()) {

      WeightGraphNode<Integer> target = edge.node();
      Integer distanceToTarget = distanceMap.get(target.value());

      if (distanceToNode != null) {
        int newDistanceToTarget = distanceToNode + edge.weight();
        if (distanceToTarget == null) {
          distanceMap.put(target.value(), newDistanceToTarget);
        } else if (newDistanceToTarget < distanceToTarget) {
          if (last) {
            throw new IllegalArgumentException("Negative cycle");
          }
          distanceMap.put(target.value(), newDistanceToTarget);
        }
      }

      if (visited.contains(target)) {
        continue;
      }

      calculateDistance(target, visited, distanceMap, last);
    }
  }
}
