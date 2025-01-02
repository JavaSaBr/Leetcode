package javasabr.leetcode.repeat;

import java.util.Arrays;

public class LongestIncreasingSubsequence {

  public static void main(String[] args) {
    int[] array = { 10, 22, 9, 33, 21, 50, 41, 60 };
    System.out.println(recursion(array));
    System.out.println(bottomUp(array));
  }

  public static int recursion(int[] array) {

    int length = array.length;
    int longest = 1;

    for(int limit = 1; limit < length; limit++) {
      longest = Math.max(recursion(array, limit), longest);
    }

    return longest;
  }

  private static int recursion(int[] array, int limit) {

    if (limit == 0) {
      return 1;
    }

    int longest = 1;
    int value = array[limit];

    for (int i = 0; i < limit; i++) {
      if (array[i] < value) {
        longest = Math.max(longest, recursion(array, i) + 1);
      }
    }

    return longest;
  }

  public static int bottomUp(int[] array) {

    int length = array.length;
    int[] indexes = new int[length + 1];
    Arrays.fill(indexes, 1);

    int longest = 1;

    for (int limit = 1; limit < length; limit++) {
      int value = array[limit];
      for (int i = 0; i < limit; i++) {
        if (array[i] > value) {
          continue;
        }
        indexes[limit] = Math.max(indexes[limit], indexes[i] + 1);
        longest = Math.max(longest, indexes[limit]);
      }
    }

    return longest;
  }
}
