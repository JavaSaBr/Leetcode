package javasabr.leetcode;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.SequencedSet;

public record DirectionGraphNode<T>(T value, SequencedSet<DirectionGraphNode<T>> next) {
  public static <T> DirectionGraphNode<T> valueOf(T value) {
    return new DirectionGraphNode<>(value, new LinkedHashSet<>());
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DirectionGraphNode<?> graphNode = (DirectionGraphNode<?>) o;
    return Objects.equals(value, graphNode.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

  @Override
  public String toString() {
    return "{" + "value=" + value + ", edges=" + next.size() + '}';
  }
}