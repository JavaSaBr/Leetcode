package javasabr.leetcode.repeat;

import java.util.Arrays;
import java.util.HashMap;

public class BottomUp {

  public static void main(String[] args) {
    int[] array = { 10, 22, 9, 33, 21, 50, 41, 60 };
    System.out.println(bottomUp(array));
    System.out.println(bottomUp2(array));
  }

  private static int bottomUp(int[] array) {

    if (array.length < 2) {
      return 1;
    }

    int[] indexes = new int[array.length];
    int max = 1;

    Arrays.fill(indexes, 1);

    for (int i = 1, length = array.length; i < length; i++) {
      int value = array[i];
      for(int j = 0; j < i; j++) {
        if (value <= array[j]) {
          continue;
        }
        int newIndex = Math.max(indexes[i], indexes[j] + 1);
        indexes[i] = newIndex;
        max = Math.max(max, newIndex);
      }
    }

    return max;
  }

  private static int bottomUp2(int[] array) {

    if (array.length < 2) {
      return 1;
    }

    var indexes = new HashMap<Integer, Integer>();
    int max = 1;

    for (int i = 1, length = array.length; i < length; i++) {
      int value = array[i];
      for(int j = 0; j < i; j++) {
        if (value <= array[j]) {
          continue;
        }
        int current = indexes.getOrDefault(i, 1);
        int prev = indexes.getOrDefault(j, 1);
        int newIndex = Math.max(current, prev + 1);
        if (newIndex > 1) {
          indexes.put(i, newIndex);
        }
        max = Math.max(max, newIndex);
      }
    }

    return max;
  }
}
