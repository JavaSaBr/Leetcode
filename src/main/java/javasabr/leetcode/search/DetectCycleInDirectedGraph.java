package javasabr.leetcode.search;

import static javasabr.leetcode.DirectionGraph.Connections.node;
import static javasabr.leetcode.DirectionGraph.Connections.root;

import java.util.ArrayDeque;
import java.util.Deque;
import javasabr.leetcode.DirectionGraph;
import javasabr.leetcode.DirectionGraphNode;

public class DetectCycleInDirectedGraph {

  public static void main(String[] args) {

    DirectionGraph<Integer> graph1 = DirectionGraph.with(
        root(0, 1),
        node(1, 2, 3),
        node(2, 4),
        node(3, 5),
        node(4, 8),
        node(5, 6),
        node(8),
        node(6));

    System.out.println(isCycled(graph1));

    DirectionGraph<Integer> graph2 = DirectionGraph.with(
        root(0, 1, 2),
        node(1, 2),
        node(2, 0, 3),
        node(3, 3));

    System.out.println(isCycled(graph2));
  }

  public static boolean isCycled(DirectionGraph<Integer> graph) {

    var stack = new ArrayDeque<DirectionGraphNode<Integer>>();

    for (var root : graph.roots()) {
      if (isCycled(root, stack)) {
        return true;
      }
    }

    return false;
  }

  protected static boolean isCycled(
      DirectionGraphNode<Integer> node,
      Deque<DirectionGraphNode<Integer>> stack) {

    System.out.println("Found: " + node.value());

    stack.addLast(node);

    for (var next : node.next()) {
      if (stack.contains(next)) {
        return true;
      } else if (isCycled(next, stack)) {
        return true;
      }
    }

    stack.removeLast();
    return false;
  }
}
