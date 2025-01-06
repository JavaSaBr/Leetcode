package javasabr.leetcode.search;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import javasabr.leetcode.DirectionGraph;
import javasabr.leetcode.DirectionGraphNode;

public class DetectCycleInDirectedGraph {

  public static void main(String[] args) {

    DirectionGraph<Integer> graph1 = DirectionGraph.with(
        DirectionGraph.Connections.root(0, 1),
        DirectionGraph.Connections.node(1, 2, 3),
        DirectionGraph.Connections.node(2, 4),
        DirectionGraph.Connections.node(3, 5),
        DirectionGraph.Connections.node(4, 8),
        DirectionGraph.Connections.node(5, 6),
        DirectionGraph.Connections.node(8),
        DirectionGraph.Connections.node(6));

    System.out.println(isCycled(graph1));

    DirectionGraph<Integer> graph2 = DirectionGraph.with(
        DirectionGraph.Connections.root(0, 1, 2),
        DirectionGraph.Connections.node(1, 2),
        DirectionGraph.Connections.node(2, 0, 3),
        DirectionGraph.Connections.node(3, 3));

    System.out.println(isCycled(graph2));
  }

  public static boolean isCycled(DirectionGraph<Integer> graph) {

    var visited = new HashSet<DirectionGraphNode<Integer>>();
    var stack = new ArrayDeque<DirectionGraphNode<Integer>>();

    for (var root : graph.roots()) {
      if (visited.contains(root)) {
        continue;
      }
      if (isCycled(root, visited, stack)) {
        return true;
      }
    }
    return false;
  }

  protected static boolean isCycled(
      DirectionGraphNode<Integer> node,
      Set<DirectionGraphNode<Integer>> visited,
      Deque<DirectionGraphNode<Integer>> stack) {

    visited.add(node);
    stack.addLast(node);

    for (var next : node.next()) {
      if (stack.contains(next)) {
        return true;
      } else if (visited.contains(next)) {
        continue;
      }
      if (isCycled(next, visited, stack)) {
        return true;
      }
    }

    stack.removeLast();
    return false;
  }
}
