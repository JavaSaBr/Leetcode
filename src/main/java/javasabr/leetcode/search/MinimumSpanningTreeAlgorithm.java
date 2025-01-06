package javasabr.leetcode.search;

import static javasabr.leetcode.WeightGraph.Connection.connection;
import static javasabr.leetcode.WeightGraph.Connections.node;
import static javasabr.leetcode.WeightGraph.Connections.root;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import javasabr.leetcode.WeightGraph;
import javasabr.leetcode.WeightGraphNode;
import javasabr.leetcode.WeightGraphNode.WeightEdge;

public class MinimumSpanningTreeAlgorithm {

  public static void main(String[] args) {

    WeightGraph<Integer> graph1 = WeightGraph.with(
        root(0, connection(1, 4), connection(7, 8)),
        node(1, connection(0, 4), connection(7, 11), connection(2, 8)),
        node(2, connection(1, 8), connection(8, 2), connection(5, 4), connection(3, 7)),
        node(3, connection(2, 7), connection(5, 14), connection(4, 9)),
        root(4, connection(3, 9), connection(5, 10)),
        node(5, connection(6, 2), connection(2, 4), connection(3, 14), connection(4, 10)),
        node(6, connection(5, 2), connection(8, 6), connection(7, 1)),
        node(7, connection(0, 8), connection(1, 11), connection(8, 7), connection(6, 1)),
        node(8, connection(7, 7), connection(2, 2), connection(6, 6)));

    buildMSTV1(graph1).print();
    buildMSTV2(graph1).print();
  }

  public static WeightGraph<Integer> buildMSTV1(WeightGraph<Integer> graph) {

    WeightGraphNode<Integer> smallestRoot = null;
    int smallestEdge = Integer.MAX_VALUE;

    for (WeightGraphNode<Integer> root : graph.roots()) {
      for (WeightEdge<Integer> edge : root.edges()) {
        if (edge.weight() < smallestEdge) {
          smallestRoot = root;
          smallestEdge = edge.weight();
        }
      }
    }

    var treeNodes = new LinkedHashMap<Integer, WeightGraphNode<Integer>>();
    var visited = new HashSet<WeightGraphNode<Integer>>();
    var useInMST = new HashSet<WeightGraphNode<Integer>>();
    useInMST.add(smallestRoot);

    buildMSTV1(smallestRoot, treeNodes, useInMST, visited);

    WeightGraphNode<Integer> treeRoot = treeNodes
        .values()
        .stream()
        .findFirst()
        .orElse(null);

    WeightGraph<Integer> result = WeightGraph.empty();
    result.roots().add(treeRoot);

    return result;
  }

  private static SmallestEdge nextSmallestWeightV1(
      WeightGraphNode<Integer> node,
      Set<WeightGraphNode<Integer>> useInMST,
      Set<WeightGraphNode<Integer>> visited,
      int smallestWeight) {

    visited.add(node);

    WeightEdge<Integer> foundEdge = null;
    WeightGraphNode<Integer> parentNode = null;

    // detect the smallest edge from this node
    for (WeightEdge<Integer> edge : node.edges()) {
      if (!useInMST.contains(edge.node())) {
        if (edge.weight() < smallestWeight) {
          foundEdge = edge;
          parentNode = node;
          smallestWeight = foundEdge.weight();
        }
      }
    }

    // check the possible smallest edge deeper
    for (WeightEdge<Integer> edge : node.edges()) {
      if (visited.contains(edge.node()) || !useInMST.contains(edge.node())) {
        continue;
      }

      var deepEdge = nextSmallestWeightV1(edge.node(), useInMST, visited, smallestWeight);

      if (deepEdge != null) {
        parentNode = deepEdge.parent();
        foundEdge = deepEdge.edge();
        smallestWeight = foundEdge.weight();
      }
    }

    return parentNode == null ? null : new SmallestEdge(parentNode, foundEdge);
  }

  private static void buildMSTV1(
      WeightGraphNode<Integer> node,
      Map<Integer, WeightGraphNode<Integer>> treeNodes,
      Set<WeightGraphNode<Integer>> useInMST,
      Set<WeightGraphNode<Integer>> visited) {

    while (true) {

      visited.clear();

      var nextSmallest = nextSmallestWeightV1(node, useInMST, visited, Integer.MAX_VALUE);
      if (nextSmallest == null) {
        break;
      }

      WeightEdge<Integer> edge = nextSmallest.edge();
      WeightGraphNode<Integer> parent = nextSmallest.parent();
      WeightGraphNode<Integer> target = edge.node();

      useInMST.add(parent);
      useInMST.add(target);

      WeightGraphNode<Integer> treeNode = treeNodes
          .computeIfAbsent(parent.value(), WeightGraphNode::valueOf);

      WeightGraphNode<Integer> childNode = treeNodes
          .computeIfAbsent(target.value(), WeightGraphNode::valueOf);

      treeNode
          .edges()
          .add(new WeightEdge<>(childNode, edge.weight()));
    }
  }

  public static WeightGraph<Integer> buildMSTV2(WeightGraph<Integer> graph) {

    WeightGraphNode<Integer> smallestRoot = null;
    int smallestEdge = Integer.MAX_VALUE;

    for (WeightGraphNode<Integer> root : graph.roots()) {
      for (WeightEdge<Integer> edge : root.edges()) {
        if (edge.weight() < smallestEdge) {
          smallestRoot = root;
          smallestEdge = edge.weight();
        }
      }
    }

    var treeNodes = new LinkedHashMap<Integer, WeightGraphNode<Integer>>();
    treeNodes.computeIfAbsent(smallestRoot.value(), WeightGraphNode::valueOf);

    var useInMST = new HashSet<WeightGraphNode<Integer>>();
    var smallestEdges = new PriorityQueue<SmallestEdge>();

    buildMSTV2(smallestRoot, null, treeNodes, useInMST, smallestEdges);

    WeightGraphNode<Integer> treeRoot = treeNodes
        .values()
        .stream()
        .findFirst()
        .orElse(null);

    WeightGraph<Integer> result = WeightGraph.empty();
    result.roots().add(treeRoot);

    return result;
  }

  private static void buildMSTV2(
      WeightGraphNode<Integer> node,
      SmallestEdge parentEdge,
      Map<Integer, WeightGraphNode<Integer>> treeNodes,
      Set<WeightGraphNode<Integer>> useInMST,
      Queue<SmallestEdge> smallestEdges) {

    useInMST.add(node);

    if (parentEdge != null) {
      smallestEdges.removeIf(smallest -> smallest
          .edge()
          .node()
          .equals(node));

      WeightEdge<Integer> edge = parentEdge.edge();
      WeightGraphNode<Integer> parent = parentEdge.parent();
      WeightGraphNode<Integer> target = edge.node();

      useInMST.add(parent);
      useInMST.add(target);

      WeightGraphNode<Integer> treeNode = treeNodes.computeIfAbsent(parent.value(), WeightGraphNode::valueOf);
      WeightGraphNode<Integer> childNode = treeNodes.computeIfAbsent(target.value(), WeightGraphNode::valueOf);

      treeNode
          .edges()
          .add(new WeightEdge<>(childNode, edge.weight()));
    }

    SmallestEdge smallestEdge;

    for (WeightEdge<Integer> nodeEdge : node.edges()) {
      if (useInMST.contains(nodeEdge.node())) {
        continue;
      }
      smallestEdge = new SmallestEdge(node, nodeEdge);
      smallestEdges.add(smallestEdge);
    }

    while (!smallestEdges.isEmpty()) {
      SmallestEdge smallest = smallestEdges.poll();
      buildMSTV2(smallest.edge().node(), smallest, treeNodes, useInMST, smallestEdges);
    }
  }

  private record SmallestEdge(
      WeightGraphNode<Integer> parent,
      WeightEdge<Integer> edge) implements Comparable<SmallestEdge> {

    @Override
    public int compareTo(SmallestEdge another) {
      return edge.weight() - another.edge.weight();
    }
  }
}
