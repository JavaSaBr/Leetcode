package javasabr.leetcode;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.SequencedSet;

public record GraphNode<T>(T value, SequencedSet<GraphNode<T>> connected) {
  public static <T> GraphNode<T> valueOf(T value) {
    return new GraphNode<>(value, new LinkedHashSet<>());
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GraphNode<?> graphNode = (GraphNode<?>) o;
    return Objects.equals(value, graphNode.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  @Override
  public String toString() {
    return "{" + "value=" + value + ", connected=" + connected.size() + '}';
  }
}
