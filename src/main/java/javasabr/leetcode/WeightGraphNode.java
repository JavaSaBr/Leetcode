package javasabr.leetcode;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.SequencedSet;

public record WeightGraphNode<T>(T value, SequencedSet<WeightEdge<T>> edges) {

  public static <T> WeightGraphNode<T> valueOf(T value) {
    return new WeightGraphNode<>(value, new LinkedHashSet<>());
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WeightGraphNode<?> graphNode = (WeightGraphNode<?>) o;
    return Objects.equals(value, graphNode.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  @Override
  public String toString() {
    return "{" + "value=" + value + ", edges=" + edges.size() + '}';
  }

  public record WeightEdge<T>(WeightGraphNode<T> node, int weight) {}
}