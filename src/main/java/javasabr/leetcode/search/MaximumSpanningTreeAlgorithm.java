package javasabr.leetcode.search;

import static javasabr.leetcode.WeightGraph.Connection.connection;
import static javasabr.leetcode.WeightGraph.Connections.node;
import static javasabr.leetcode.WeightGraph.Connections.root;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import javasabr.leetcode.WeightGraph;
import javasabr.leetcode.WeightGraphNode;
import javasabr.leetcode.WeightGraphNode.WeightEdge;

public class MaximumSpanningTreeAlgorithm {

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

    WeightGraphNode<Integer> biggestRoot = null;
    int biggestEdge = Integer.MIN_VALUE;

    for (WeightGraphNode<Integer> root : graph.roots()) {
      for (WeightEdge<Integer> edge : root.edges()) {
        if (edge.weight() > biggestEdge) {
          biggestRoot = root;
          biggestEdge = edge.weight();
        }
      }
    }

    var treeNodes = new LinkedHashMap<Integer, WeightGraphNode<Integer>>();
    var visited = new HashSet<WeightGraphNode<Integer>>();
    var useInMST = new HashSet<WeightGraphNode<Integer>>();
    useInMST.add(biggestRoot);

    buildMSTV1(biggestRoot, treeNodes, useInMST, visited);

    WeightGraphNode<Integer> treeRoot = treeNodes
        .values()
        .stream()
        .findFirst()
        .orElse(null);

    WeightGraph<Integer> result = WeightGraph.empty();
    result.roots().add(treeRoot);

    return result;
  }

  private static BiggestEdge nextBiggestWeightV1(
      WeightGraphNode<Integer> node,
      Set<WeightGraphNode<Integer>> useInMST,
      Set<WeightGraphNode<Integer>> visited,
      int biggestWeight) {

    visited.add(node);

    WeightEdge<Integer> foundEdge = null;
    WeightGraphNode<Integer> parentNode = null;

    // detect the smallest edge from this node
    for (WeightEdge<Integer> edge : node.edges()) {
      if (!useInMST.contains(edge.node())) {
        if (edge.weight() > biggestWeight) {
          foundEdge = edge;
          parentNode = node;
          biggestWeight = foundEdge.weight();
        }
      }
    }

    // check the possible smallest edge deeper
    for (WeightEdge<Integer> edge : node.edges()) {
      if (visited.contains(edge.node()) || !useInMST.contains(edge.node())) {
        continue;
      }

      var deepEdge = nextBiggestWeightV1(edge.node(), useInMST, visited, biggestWeight);

      if (deepEdge != null) {
        parentNode = deepEdge.parent();
        foundEdge = deepEdge.edge();
        biggestWeight = foundEdge.weight();
      }
    }

    return parentNode == null ? null : new BiggestEdge(parentNode, foundEdge);
  }

  private static void buildMSTV1(
      WeightGraphNode<Integer> node,
      Map<Integer, WeightGraphNode<Integer>> treeNodes,
      Set<WeightGraphNode<Integer>> useInMST,
      Set<WeightGraphNode<Integer>> visited) {

    while (true) {

      visited.clear();

      var nextBiggest = nextBiggestWeightV1(node, useInMST, visited, Integer.MIN_VALUE);
      if (nextBiggest == null) {
        break;
      }

      WeightEdge<Integer> edge = nextBiggest.edge();
      WeightGraphNode<Integer> parent = nextBiggest.parent();
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

    WeightGraphNode<Integer> biggestRoot = null;
    int biggestEdge = Integer.MIN_VALUE;

    for (WeightGraphNode<Integer> root : graph.roots()) {
      for (WeightEdge<Integer> edge : root.edges()) {
        if (edge.weight() > biggestEdge) {
          biggestRoot = root;
          biggestEdge = edge.weight();
        }
      }
    }

    var treeNodes = new LinkedHashMap<Integer, WeightGraphNode<Integer>>();
    treeNodes.computeIfAbsent(biggestRoot.value(), WeightGraphNode::valueOf);

    var useInMST = new HashSet<WeightGraphNode<Integer>>();
    var biggestEdges = new PriorityQueue<>(Comparator
        .<BiggestEdge>comparingInt(element -> element.edge.weight())
        .reversed());

    buildMSTV2(biggestRoot, null, treeNodes, useInMST, biggestEdges);

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
      BiggestEdge parentEdge,
      Map<Integer, WeightGraphNode<Integer>> treeNodes,
      Set<WeightGraphNode<Integer>> useInMST,
      Queue<BiggestEdge> biggestEdges) {

    useInMST.add(node);

    if (parentEdge != null) {
      biggestEdges.removeIf(smallest -> smallest
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

    BiggestEdge biggestEdge;

    for (WeightEdge<Integer> nodeEdge : node.edges()) {
      if (useInMST.contains(nodeEdge.node())) {
        continue;
      }
      biggestEdge = new BiggestEdge(node, nodeEdge);
      biggestEdges.add(biggestEdge);
    }

    while (!biggestEdges.isEmpty()) {
      BiggestEdge biggest = biggestEdges.poll();
      buildMSTV2(biggest.edge().node(), biggest, treeNodes, useInMST, biggestEdges);
    }
  }

  private record BiggestEdge(
      WeightGraphNode<Integer> parent,
      WeightEdge<Integer> edge) implements Comparable<BiggestEdge> {

    @Override
    public int compareTo(BiggestEdge another) {
      return edge.weight() - another.edge.weight();
    }
  }
}
