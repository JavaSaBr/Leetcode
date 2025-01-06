package javasabr.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SequencedSet;
import java.util.Set;

public record WeightGraph<T>(Set<WeightGraphNode<T>> roots) {

  public WeightGraphNode<T> root(T value) {
    return roots
        .stream()
        .filter(graphNode -> graphNode.value().equals(value))
        .findFirst()
        .orElse(null);
  }

  public static <T> WeightGraph<T> empty() {
    return new WeightGraph<>(new HashSet<>());
  }

  @SafeVarargs
  public static <T> WeightGraph<T> with(Connections<T>... connections) {

    WeightGraph<T> graph = empty();
    Map<T, WeightGraphNode<T>> nodes = new HashMap<>();
    Set<WeightGraphNode<T>> roots = new HashSet<>();

    for (Connections<T> connection : connections) {
      var node = nodes.computeIfAbsent(connection.node, WeightGraphNode::valueOf);
      if (connection.root) {
        roots.add(node);
      }
    }

    for (Connections<T> connection : connections) {
      WeightGraphNode<T> graphNode = nodes.get(connection.node);
      SequencedSet<WeightGraphNode.WeightEdge<T>> edges = graphNode.edges();
      for (var nextConnection : connection.connections()) {
        WeightGraphNode<T> node = nodes.get(nextConnection.value());
        edges.add(new WeightGraphNode.WeightEdge<>(node, nextConnection.weight()));
      }
    }

    graph
        .roots()
        .addAll(roots);

    return graph;
  }

  public record Connections<T>(boolean root, T node, Connection<T>... connections) {

    @SafeVarargs
    public Connections {}

    @SafeVarargs
    public static <T> Connections<T> root(T node, Connection<T>... connections) {
      return new Connections<>(true, node, connections);
    }

    @SafeVarargs
    public static <T> Connections<T> node(T node, Connection<T>... connections) {
      return new Connections<>(false, node, connections);
    }

    @Override
    public String toString() {
      return '{' + (root ? "root|" : "") + "|" + node + "|" + Arrays.toString(connections) + '}';
    }
  }

  public void print() {

    var visited = new HashSet<WeightGraphNode<T>>();

    System.out.println("Edge        Weight");

    for (var root : roots()) {
      if (!visited.contains(root)) {
        print(null, root, 0, visited);
      }
    }
  }

  private void print(
      WeightGraphNode<T> parent,
      WeightGraphNode<T> node,
      int weight,
      Set<WeightGraphNode<T>> visited) {

    visited.add(node);

    if (parent != null) {
      String edge = parent.value() + " - " + node.value();
      int space = 12 - edge.length();
      System.out.println(edge + " ".repeat(space) + weight);
    }

    for (var edge : node.edges()) {
      if (!visited.contains(edge.node())) {
        print(node, edge.node(), edge.weight(), visited);
      }
    }
  }

  public record Connection<T>(T value, int weight) {
    public static <T> Connection<T> connection(T value, int weight) {
      return new Connection<>(value, weight);
    }
  }
}
