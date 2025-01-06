package javasabr.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.SequencedSet;
import java.util.Set;

public record DirectionGraph<T>(Set<DirectionGraphNode<T>> roots) {

  public DirectionGraphNode<T> root(T value) {
    return roots
        .stream()
        .filter(graphNode -> graphNode.value().equals(value))
        .findFirst()
        .orElse(null);
  }

  public static <T> DirectionGraph<T> empty() {
    return new DirectionGraph<>(new HashSet<>());
  }

  @SafeVarargs
  public static <T> DirectionGraph<T> with(Connections<T>... connections) {

    DirectionGraph<T> graph = empty();
    Map<T, DirectionGraphNode<T>> nodes = new HashMap<>();
    Set<DirectionGraphNode<T>> roots = new HashSet<>();

    for (Connections<T> connection : connections) {
      var node = nodes.computeIfAbsent(connection.node, DirectionGraphNode::valueOf);
      if (connection.root) {
        roots.add(node);
      }
    }

    for (Connections<T> connection : connections) {
      DirectionGraphNode<T> graphNode = nodes.get(connection.node);
      SequencedSet<DirectionGraphNode<T>> next = graphNode.next();
      for (T nextValue : connection.connections()) {
        next.add(nodes.get(nextValue));
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
