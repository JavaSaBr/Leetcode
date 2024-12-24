package javasabr.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SequencedSet;
import java.util.Set;

public record Graph<T>(Set<GraphNode<T>> roots) {

  public GraphNode<T> root(T value) {
    return roots
        .stream()
        .filter(graphNode -> graphNode.value().equals(value))
        .findFirst()
        .orElse(null);
  }

  public static <T> Graph<T> empty() {
    return new Graph<>(new HashSet<>());
  }

  @SafeVarargs
  public static <T> Graph<T> with(Connections<T>... connections) {

    Graph<T> graph = empty();
    Map<T, GraphNode<T>> nodes = new HashMap<>();
    Set<GraphNode<T>> roots = new HashSet<>();

    for (Connections<T> connection : connections) {
      var node = nodes.computeIfAbsent(connection.node, GraphNode::valueOf);
      if (connection.root) {
        roots.add(node);
      }
    }

    for (Connections<T> connection : connections) {
      GraphNode<T> graphNode = nodes.get(connection.node);
      SequencedSet<GraphNode<T>> connected = graphNode.connected();
      for (T connectedValue : connection.connections()) {
        connected.add(nodes.get(connectedValue));
      }
    }

    graph
        .roots()
        .addAll(roots);

    return graph;
  }

  public record Connections<T>(boolean root, T node, T... connections) {

    @SafeVarargs
    public Connections {}

    @SafeVarargs
    public static <T> Connections<T> root(T node, T... connections) {
      return new Connections<>(true, node, connections);
    }

    @SafeVarargs
    public static <T> Connections<T> node(T node, T... connections) {
      return new Connections<>(false, node, connections);
    }

    @Override
    public String toString() {
      return '{' + (root ? "root|" : "") + "|" + node + "|" + Arrays.toString(connections) + '}';
    }
  }
}
